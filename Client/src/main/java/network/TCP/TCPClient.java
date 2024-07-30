package network.TCP;

import controller.Controller;
import network.ClientHandler;

import java.io.IOException;
import java.net.Socket;

public class TCPClient extends Thread{
    private String serverIPAddress;
    private Integer serverPort;
    private Socket clientSocket;
    private ClientListener listener;
    private static int number;
    private ClientHandler clientHandler;
    private boolean connected;

    public TCPClient(String serverIPAddress, int serverPort, ClientHandler clientHandler) {
        this.serverIPAddress=serverIPAddress;
        this.serverPort=serverPort;
        this.clientHandler = clientHandler;
    }
    public void initSocket(){
        try {
            clientSocket = new Socket(serverIPAddress, serverPort);
            number++;
            listener = new ClientListener(clientSocket);
            connected = true;
        } catch (IOException e) {
            if(Controller.connectionError()){
                clientHandler.initialize();
            }
        }
    }

    public static int getNumber() {
        return number;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public boolean isConnected() {
        return connected;
    }

    public ClientListener getListener() {
        return listener;
    }
}
