package network;

import model.Client;
import network.TCP.RequestHandler;
import network.UDP.UDPServer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener extends Thread{
    private final Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private InetSocketAddress socketAddress;
    private Client client;
    private final Object lock;
    private Logger logger;
    private UDPServer udpServer;
    private static int number;

    public ServerListener(Socket socket) throws IOException {
        number++;
        this.socket = socket;
        lock = new Object();
        logger = Logger.getLogger(ServerListener.class);
        setStreams();
        udpServer = new UDPServer(number);
    }


    public void run(){
        while (!socket.isClosed()){
                String message = getMessage();
                synchronized (lock) {
                    RequestHandler.checkRequest(message, this);
                }
        }
    }
    private void setStreams() throws IOException {
        scanner = new Scanner(socket.getInputStream());
        printWriter = new PrintWriter(socket.getOutputStream());
    }
    public void sendMessage(String message) {
            printWriter.println(message);
            printWriter.flush();

    }
    public String getMessage() {
            return scanner.nextLine();
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        udpServer.run();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Object getLock() {
        return lock;
    }

    public Logger getLogger() {
        return logger;
    }

    public UDPServer getUdpServer() {
        return udpServer;
    }
}
