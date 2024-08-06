package network.TCP;

import controller.game_manager.GameManager;
import model.Client;
import org.apache.log4j.Logger;
import view.game.FrameView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerListener extends Thread{
    private final Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private InetSocketAddress socketAddress;
    private Client client;
    private final Object lock;
    private final Object framesLock;
    private Logger logger;
    private ArrayList<FrameView> framesToSend;
    private GameManager gameManager;

    public ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        lock = new Object();
        framesLock = new Object();
        logger = Logger.getLogger(ServerListener.class);
        setStreams();
    }

    public ArrayList<FrameView> getFramesToSend() {
        return framesToSend;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setFramesToSend(ArrayList<FrameView> framesToSend) {
        this.framesToSend = framesToSend;
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

    public Object getFramesLock() {
        return framesLock;
    }

    public Logger getLogger() {
        return logger;
    }
}
