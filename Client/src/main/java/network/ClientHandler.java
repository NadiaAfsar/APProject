package network;

import application.MyApplication;
import controller.GameManager;
import model.Request;
import model.Requests;
import model.Squad;
import network.TCP.TCPClient;
import network.UDP.UDPClient;
import model.Client;
import org.apache.log4j.Logger;
import view.menu.GameFrame;

import java.io.File;

public class ClientHandler extends Thread{
    private Client client;
    private TCPClient tcpClient;
    private UDPClient udpClient;
    private Logger logger;
    public ClientHandler(){
        client = new Client();
        logger = Logger.getLogger(ClientHandler.class.getName());
    }
    public void run(){
        while (true){
            sendName(client.getUsername());
            for (int i = 0; i < client.getReceivedRequests().size(); i++){
                Request request = client.getReceivedRequests().get(i);
                if (GameFrame.receiveRequest(request.getSender(), request.getRequestName())){
                    tcpClient.getListener().sendMessage(Requests.ACCEPTED.toString());
                    tcpClient.getListener().sendMessage(request.getID());
                }
                else {
                    tcpClient.getListener().sendMessage(Requests.DECLINED.toString());
                    tcpClient.getListener().sendMessage(request.getID());
                }
            }
        }
    }
    public void initialize(){
        tcpClient = new TCPClient(MyApplication.configs.SERVER_IP_ADDRESS, MyApplication.configs.SERVER_PORT, this);
        tcpClient.initSocket();
        logger.debug("TCP set");
        if (tcpClient.isConnected()) {
            udpClient = new UDPClient(TCPClient.getNumber()+8090, this);
            udpClient.initialize();
            tcpClient.getListener().sendMessage(udpClient.getPort()+"");
            logger.debug("connected");
            new GameManager(this, true).initialize();
        }
    }
    public void sendName(String name){
        tcpClient.getListener().sendMessage(Requests.NAME.toString());
        tcpClient.getListener().sendMessage(name);
        tcpClient.getListener().sendMessage(udpClient.getPort()+"");
        setClient();
    }
    private void setClient(){
        File file = udpClient.getReceiver().getFile();
        client = MyApplication.readerWriter.getObject(Client.class, file);
    }

    public TCPClient getTcpClient() {
        return tcpClient;
    }

    public UDPClient getUdpClient() {
        return udpClient;
    }
    public void sendJoinRequest(String name){
        tcpClient.getListener().sendMessage(Requests.JOIN.toString());
        tcpClient.getListener().sendMessage(name);
    }

    public Client getClient() {
        return client;
    }
    public boolean createNewSquad(String name){
        tcpClient.getListener().sendMessage(Requests.CREATE_SQUAD.toString());
        tcpClient.getListener().sendMessage(name);
        String response = tcpClient.getListener().getMessage();
        if (response.equals(Requests.ACCEPTED.toString())) {
            File squadFile = udpClient.getReceiver().getFile();
            Squad squad = MyApplication.readerWriter.getObject(Squad.class, squadFile);
            client.setSquad(squad);
            return true;
        }
        return false;
    }
    public void deleteMember(String name){
        tcpClient.getListener().sendMessage(Requests.DELETE_MEMBER.toString());
        tcpClient.getListener().sendMessage(client.getSquad().getName());
        tcpClient.getListener().sendMessage(name);
    }
    public void updateSquad(){
        tcpClient.getListener().sendMessage(Requests.SQUAD.toString());
        tcpClient.getListener().sendMessage(client.getSquad().getName());
        System.out.println("request sent");
        File squadFile = udpClient.getReceiver().getFile();
        System.out.println("file recieved");
        client.setSquad(MyApplication.readerWriter.getObject(Squad.class, squadFile));
    }
    public Squad getCompetitor(){
        tcpClient.getListener().sendMessage(Requests.SQUAD.toString());
        tcpClient.getListener().sendMessage(client.getSquad().getCompetitorSquad());
        File file = udpClient.getReceiver().getFile();
        return MyApplication.readerWriter.getObject(Squad.class, file);
    }
    public void sendBattleRequest(String name, int battle){
        tcpClient.getListener().sendMessage(Requests.BATTLE_REQUEST.toString());
        tcpClient.getListener().sendMessage(client.getUsername());
        tcpClient.getListener().sendMessage(name);
        tcpClient.getListener().sendMessage(battle+"");
    }
    public Client getClient(String name){
        tcpClient.getListener().sendMessage(Requests.CLIENT.toString());
        tcpClient.getListener().sendMessage(name);
        File clientFile = udpClient.getReceiver().getFile();

    }

}
