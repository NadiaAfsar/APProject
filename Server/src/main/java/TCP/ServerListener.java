package TCP;

import model.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener extends Thread{
    private final Socket socket;
    private Scanner socketScanner;
    private PrintWriter socketPrintWriter;
    private Client client;

    public ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        setStreams();
    }
    public void run(){
        while (!socket.isClosed()){
            String message = getMessage();
        }
    }
    private void setStreams() throws IOException {
        socketScanner = new Scanner(socket.getInputStream());
        socketPrintWriter = new PrintWriter(socket.getOutputStream());
    }
    public void sendMessage(String message) {
            socketPrintWriter.println(message);
            socketPrintWriter.flush();
    }
    public String getMessage() {
            return socketScanner.nextLine();
    }
}
