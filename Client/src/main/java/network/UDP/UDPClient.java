package network.UDP;

import network.ClientHandler;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPClient {
    private Integer port;
    private DatagramSocket datagramSocket;
    private ClientHandler clientHandler;
    private Sender sender;
    private Receiver receiver;
    public UDPClient(Integer port, ClientHandler clientHandler) {
        this.port = port;
        this.clientHandler = clientHandler;
    }

    public void initialize(){
        try {
            datagramSocket = new DatagramSocket(port);
            receiver = new Receiver(datagramSocket);
            sender = new Sender(datagramSocket);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Integer getPort() {
        return port;
    }

    public Sender getSender() {
        return sender;
    }
}
