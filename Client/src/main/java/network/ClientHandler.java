package network;

import application.MyApplication;
import controller.ApplicationManager;
import controller.Controller;
import controller.game_manager.GameManager;
import controller.save.Configs;
import model.*;
import network.TCP.TCPClient;
import network.UDP.UDPClient;
import org.apache.log4j.Logger;
import view.menu.GameFrame;

import java.io.File;

public class ClientHandler extends Thread{
    private Client client;
    private TCPClient tcpClient;
    private UDPClient udpClient;
    private Logger logger;
    private GameManager gameManager;
    private final static Object lock = new Object();
    public ClientHandler(){
        client = new Client();
        logger = Logger.getLogger(ClientHandler.class.getName());
    }
    public void run(){
        while (true){
                if (client.getStatus().equals(Status.ONLINE)) {
                    synchronized (lock) {
                        updateClient();
                    }
                } else if (client.getStatus().equals(Status.BUSY)) {


                }
            try {
                sleep((long) Configs.FRAME_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateClient(){
            client = getClient(client.getUsername());
            for (int i = 0; i < client.getReceivedRequests().size(); i++) {
                Request request = client.getReceivedRequests().get(i);
                if (GameFrame.receiveRequest(request.getSender(), request.getRequestName())) {
                    tcpClient.getListener().sendMessage(Requests.ACCEPTED.toString());
                    tcpClient.getListener().sendMessage(request.getID());
                } else {
                    tcpClient.getListener().sendMessage(Requests.DECLINED.toString());
                    tcpClient.getListener().sendMessage(request.getID());
                }
                received(request.getID());
            }
            for (int i = 0; i < client.getSentRequests().size(); i++) {
                Request request = client.getSentRequests().get(i);
                if (request.isAccepted()) {
                    GameFrame.showMessage(request.getReceiver() + " accepted your " + request.getRequestName() + " request.");
                    sent(request.getID());
                } else if (request.isDeclined()) {
                    GameFrame.showMessage(request.getReceiver() + " declined your " + request.getRequestName() + " request.");
                    sent(request.getID());
                }
            }
    }
    private void received(String ID){
        synchronized (lock) {
            tcpClient.getListener().sendMessage(Requests.RECEIVED.toString());
            tcpClient.getListener().sendMessage(ID);
        }
    }
    private void sent(String ID){
        synchronized (lock) {
            tcpClient.getListener().sendMessage(Requests.SENT.toString());
            tcpClient.getListener().sendMessage(ID);
        }
    }
    public void initialize(){
        tcpClient = new TCPClient(MyApplication.configs.SERVER_IP_ADDRESS, MyApplication.configs.SERVER_PORT, this);
        tcpClient.initSocket();
        logger.debug("TCP set");
        if (tcpClient.isConnected()) {
            udpClient = new UDPClient(TCPClient.getNumber()+8090, this);
            System.out.println(udpClient.getPort());
            udpClient.initialize();
            tcpClient.getListener().sendMessage(udpClient.getPort()+"");
            logger.debug("connected");
            ApplicationManager applicationManager = new ApplicationManager(this, true);
            Controller.runGame(applicationManager);
        }
    }
    public void sendName(String name){
        synchronized (lock) {
            tcpClient.getListener().sendMessage(Requests.NAME.toString());
            tcpClient.getListener().sendMessage(name);
            tcpClient.getListener().sendMessage(udpClient.getPort() + "");
            setClient();
        }
    }
    private void setClient(){
        File file = udpClient.getReceiver().getFile();
        client = MyApplication.readerWriter.getObject(Client.class, file);
        file.delete();
        start();
    }

    public TCPClient getTcpClient() {
        return tcpClient;
    }

    public UDPClient getUdpClient() {
        return udpClient;
    }
    public void sendJoinRequest(String name){
        synchronized (lock) {
            tcpClient.getListener().sendMessage(Requests.JOIN.toString());
            tcpClient.getListener().sendMessage(name);
        }
    }

    public Client getClient() {
        return client;
    }
    public boolean createNewSquad(String name){
        synchronized (lock) {
            tcpClient.getListener().sendMessage(Requests.CREATE_SQUAD.toString());
            tcpClient.getListener().sendMessage(name);
            String response = tcpClient.getListener().getMessage();
            if (response.equals(Requests.ACCEPTED.toString())) {
                logger.debug("accepted");
                tcpClient.getListener().sendMessage(client.getUsername());
                File squadFile = udpClient.getReceiver().getFile();
                Squad squad = MyApplication.readerWriter.getObject(Squad.class, squadFile);
                client.setSquad(squad);
                squadFile.delete();
                return true;
            }
            return false;
        }
    }
    public void deleteMember(String name){
        synchronized (lock) {
            tcpClient.getListener().sendMessage(Requests.DELETE_MEMBER.toString());
            tcpClient.getListener().sendMessage(client.getSquad().getName());
            tcpClient.getListener().sendMessage(name);
        }
    }
    public Squad getCompetitor(){
        synchronized (lock) {
            tcpClient.getListener().sendMessage(Requests.SQUAD.toString());
            tcpClient.getListener().sendMessage(client.getSquad().getCompetitorSquad());
            File file = udpClient.getReceiver().getFile();
            Squad squad = MyApplication.readerWriter.getObject(Squad.class, file);
            file.delete();
            return squad;
        }
    }
    public void sendBattleRequest(String name, int battle){
        synchronized (lock) {
            tcpClient.getListener().sendMessage(Requests.BATTLE_REQUEST.toString());
            tcpClient.getListener().sendMessage(client.getUsername());
            tcpClient.getListener().sendMessage(name);
            tcpClient.getListener().sendMessage(battle + "");
        }
    }
    public Client getClient(String name){
        tcpClient.getListener().sendMessage(Requests.CLIENT.toString());
        tcpClient.getListener().sendMessage(name);
        File clientFile = udpClient.getReceiver().getFile();
        logger.debug(clientFile.getName());
        Client client1 = MyApplication.readerWriter.getObject(Client.class, clientFile);
        clientFile.delete();
        return client1;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public Object getLock() {
        return lock;
    }
}
