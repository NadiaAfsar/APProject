package network;

import application.MyApplication;
import network.TCP.TCPClient;
import network.UDP.UDPClient;
import model.Client;

import java.io.File;

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
        }
    }
    public void sendName(String name){
        tcpClient.getListener().sendMessage("name");
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
        tcpClient.getListener().sendMessage("join");
        tcpClient.getListener().sendMessage(name);
    }

    public Client getClient() {
        return client;
    }
}
