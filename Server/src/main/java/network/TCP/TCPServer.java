package network.TCP;

import network.ServerListener;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {
    private ServerSocket serverSocket;
    private Integer serverPort;
    private Logger logger;

    public TCPServer(Integer serverPort) {
        this.serverPort = serverPort;
        logger = Logger.getLogger(TCPServer.class.getName());
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
                logger.debug("connected");
                ServerListener listener = new ServerListener(socket);
                Integer port = Integer.valueOf(listener.getMessage());
                listener.setUdpServer(port-8090);
                listener.setSocketAddress(new InetSocketAddress("localHost", port));
                listener.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
