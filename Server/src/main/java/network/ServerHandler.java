package network;

import model.Server;
import network.TCP.TCPServer;
import network.UDP.UDPServer;

public class ServerHandler {
    private Server server;
    private TCPServer tcpServer;
    private UDPServer udpServer;
    private static ServerHandler instance;
    private ServerHandler(){
        server = new Server();
        tcpServer = new TCPServer(8090);
        udpServer = new UDPServer();
        tcpServer.start();
        udpServer.run();
    }

    public static ServerHandler getInstance() {
        if (instance == null){
            instance = new ServerHandler();
        }
        return instance;
    }

    public Server getServer() {
        return server;
    }

    public TCPServer getTcpServer() {
        return tcpServer;
    }

    public UDPServer getUdpServer() {
        return udpServer;
    }
}
