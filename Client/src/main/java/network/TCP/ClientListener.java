package network.TCP;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener {
    private Socket socket;
    private Scanner socketScanner;
    private PrintWriter socketPrintWriter;

    public ClientListener(Socket socket) {
        this.socket = socket;
        setStreams();
    }
    private void setStreams() {
        try {
            socketScanner = new Scanner(socket.getInputStream());
            socketPrintWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String message) {
            socketPrintWriter.println(message);
            socketPrintWriter.flush();
    }
    public String getMessage() {
            return socketScanner.nextLine();
    }
}
