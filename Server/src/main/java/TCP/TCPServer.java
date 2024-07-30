package TCP;

import model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {
    private ServerSocket serverSocket;
    private Integer serverPort;

    public TCPServer(Integer serverPort) {
        this.serverPort = serverPort;
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ServerListener listener = new ServerListener(socket);
                listener.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
