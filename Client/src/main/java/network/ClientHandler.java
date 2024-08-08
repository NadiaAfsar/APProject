package network;

import application.MyApplication;
import controller.ApplicationManager;
import controller.Controller;
import controller.game_manager.GameManager;
import controller.game_manager.GameManagerHelper;
import controller.game_manager.Monomachia;
import controller.save.Configs;
import model.*;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.interfaces.movement.Point;
import network.TCP.TCPClient;
import network.UDP.UDPClient;
import org.apache.log4j.Logger;
import view.menu.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private Client client;
    private TCPClient tcpClient;
    private UDPClient udpClient;
    private Logger logger;
    private ApplicationManager applicationManager;
    private GameManager gameManager;
    private static int number;
    private final static Object lock = new Object();
    private ArrayList<Integer> newEnemies;
    private ArrayList<Point> newBullets;
    public ClientHandler(){
        number++;
        client = new Client();
        logger = Logger.getLogger(ClientHandler.class.getName()+number);
    }
    public void run(){
        while (true){
                if (client.getStatus().equals(Status.ONLINE)) {
                    synchronized (lock) {
                        updateClient();
                        getRunningGame();
                        if (client.getStatus().equals(Status.BUSY)){
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } else if (gameManager != null) {
                    if (gameManager.getGameModel() != null) {
                        sendUpdate();
                        getUpdates();
                    }
                }
            try {
                sleep((long) Configs.FRAME_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void shootBullet(int x, int y){
            synchronized (lock){
                newBullets.add(new Point(x,y));
            }
    }
    private void sendUpdate(){
        tcpClient.getListener().sendMessage(Requests.SEND_UPDATE.toString());
        EpsilonModel epsilonModel = gameManager.getGameModel().getEpsilons().get(gameManager.getGameModel().getEpsilonNumber());
        Entity epsilon = new Entity((int)epsilonModel.getCenter().getX(),(int) epsilonModel.getCenter().getY(), 0);
        File epsilonFile = MyApplication.readerWriter.convertToFile(epsilon, "epsilon"+epsilon.getID());
        udpClient.getSender().sendFile(epsilonFile);
        epsilonFile.delete();
        tcpClient.getListener().sendMessage(gameManager.getGameModel().getMyEpsilon().getXP()+"");
        tcpClient.getListener().sendMessage(gameManager.getGameModel().getMyEpsilon().getHP()+"");
        tcpClient.getListener().sendMessage(newBullets.size()+"");
        for (int i = 0; i < newBullets.size(); i++){
            Entity bullet = new Entity((int)newBullets.get(i).getX(), (int)newBullets.get(i).getY(), 0);
            File bulletFile = MyApplication.readerWriter.convertToFile(bullet, bullet.getID());
            udpClient.getSender().sendFile(bulletFile);
            bulletFile.delete();
        }
        newBullets = new ArrayList<>();
        tcpClient.getListener().sendMessage(newEnemies.size()+"");
        for (int i = 0; i < newEnemies.size(); i++) {
            tcpClient.getListener().sendMessage(newEnemies.get(i) + "");
        }
        newEnemies = new ArrayList<>();
    }
    private void getUpdates(){
        tcpClient.getListener().sendMessage(Requests.RECEIVE_UPDATE.toString());
        int epsilons = Integer.parseInt(tcpClient.getListener().getMessage());
        for (int i = 0; i < epsilons; i++){
            if (i != gameManager.getGameModel().getEpsilonNumber()){
                File epsilonFile = udpClient.getReceiver().getFile();
                Entity entity = MyApplication.readerWriter.getObject(Entity.class, epsilonFile);
                gameManager.getGameModel().getEpsilons().get(i).setCenter(entity.getX(), entity.getY());
                epsilonFile.delete();
                int xp = Integer.parseInt(tcpClient.getListener().getMessage());
                int hp = Integer.parseInt(tcpClient.getListener().getMessage());
                gameManager.setCompetitorHP(hp);
                gameManager.setCompetitorXP(xp);
                int bullets = Integer.parseInt(tcpClient.getListener().getMessage());
                for (int j = 0; j < bullets; j++){
                    File bulletFile = udpClient.getReceiver().getFile();
                    Entity bullet = MyApplication.readerWriter.getObject(Entity.class, bulletFile);
                    gameManager.getGameModel().getEpsilons().get(i).shootBullet(bullet.getX(), bullet.getY());
                    bulletFile.delete();
                }
            }
            String enemies = tcpClient.getListener().getMessage();
            int newEnemies = Integer.parseInt(enemies);
            for (int j = 0; j < newEnemies; j++){
                File enemyFile = udpClient.getReceiver().getFile();
                Entity enemy = MyApplication.readerWriter.getObject(Entity.class, enemyFile);
                Enemy enemy1 = GameManagerHelper.getNewEnemy(new Point(enemy.getX(), enemy.getY()), gameManager.getGameModel().getEnemyHP(),
                        gameManager.getGameModel().getEnemyVelocity(), enemy.getEnemyType(), gameManager.getGameModel().getEpsilons().get(i));
                enemyFile.delete();
                gameManager.getGameModel().getEnemies().add(enemy1);
            }
        }
    }
    public void addEnemy(int enemies){
        synchronized (lock) {
            newEnemies.add(enemies);
        }
    }
    private void getRunningGame(){
        tcpClient.getListener().sendMessage(Requests.RUNNING_GAME.toString());
        String game = tcpClient.getListener().getMessage();
        if (game.equals(Requests.MONOMACHIA.toString())){
            newBullets = new ArrayList<>();
            newEnemies = new ArrayList<>();
            gameManager = new Monomachia(applicationManager);
            int playerNumber = Integer.parseInt(tcpClient.getListener().getMessage());
            client.setStatus(Status.BUSY);
            Timer timer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Controller.startGame(gameManager, playerNumber);
                }
            });
            timer.setRepeats(false);
            timer.start();
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
            udpClient = new UDPClient(TCPClient.getNumber()+8090, this, TCPClient.getNumber());
            udpClient.initialize();
            tcpClient.getListener().sendMessage(udpClient.getPort()+"");
            applicationManager = new ApplicationManager(this, true);
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
        Client client1 = MyApplication.readerWriter.getObject(Client.class, clientFile);
        clientFile.delete();
        return client1;
    }

    public Object getLock() {
        return lock;
    }
}
