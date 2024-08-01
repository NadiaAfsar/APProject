package network;

import application.MyApplication;
import model.Requests;
import model.Squad;
import network.TCP.TCPClient;
import network.UDP.UDPClient;
import model.Client;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class ClientHandler {
    private Client client;
    private TCPClient tcpClient;
    private UDPClient udpClient;
    public ClientHandler(){
        client = new Client();
    }
    public void initialize(){
        tcpClient = new TCPClient(MyApplication.configs.SERVER_IP_ADDRESS, MyApplication.configs.SERVER_PORT, this);
        tcpClient.initSocket();
        if (tcpClient.isConnected()) {
            udpClient = new UDPClient(TCPClient.getNumber()+8090, this);
            udpClient.initialize();
            tcpClient.getListener().sendMessage(udpClient.getPort()+"");
        }
    }
    public void sendName(String name){
        tcpClient.getListener().sendMessage(Requests.NAME);
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
        tcpClient.getListener().sendMessage(Requests.JOIN);
        tcpClient.getListener().sendMessage(name);
    }

    public Client getClient() {
        return client;
    }
    public void createNewSquad(String name){
        tcpClient.getListener().sendMessage(Requests.CREATE_SQUAD);
        client.setSquad(new Squad(client, name));
        File squadFile = MyApplication.readerWriter.convertToFile(client.getSquad());
        udpClient.getSender().sendFile(squadFile);
    }
    public void deleteMember(String name){
        tcpClient.getListener().sendMessage(Requests.DELETE_MEMBER);
        tcpClient.getListener().sendMessage(client.getSquad().getName());
        tcpClient.getListener().sendMessage(name);
    }
    public void updateSquad(){
        tcpClient.getListener().sendMessage(Requests.MEMBERS);
        tcpClient.getListener().sendMessage(client.getSquad().getName());
        File squadFile = udpClient.getReceiver().getFile();
        client.setSquad(MyApplication.readerWriter.getObject(Squad.class, squadFile));
    }

}
