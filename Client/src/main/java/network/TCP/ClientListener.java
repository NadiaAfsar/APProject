package network.TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener {
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;

    public ClientListener(Socket socket) {
        this.socket = socket;
        setStreams();
    }
    private void setStreams() {
        try {
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String message) {
            printWriter.println(message);
            printWriter.flush();
    }
    public String getMessage() {
            return scanner.nextLine();
    }
}
