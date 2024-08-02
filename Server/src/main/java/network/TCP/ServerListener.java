package network.TCP;

import model.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        setStreams();
    }
    public void run(){
        while (!socket.isClosed()){
            String message = getMessage();
            RequestHandler.checkRequest(message, this);
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
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
