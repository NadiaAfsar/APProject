package network.UDP;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPServer implements Runnable{
    private Receiver receiver;
    private Sender sender;
    private Integer port;
    private DatagramSocket datagramSocket;
    private Object lock;
    public UDPServer(int number) {
        port = 1000+number;
        lock = new Object();
    }

    @Override
    public void run() {
        try {
            datagramSocket = new DatagramSocket(port);
            sender = new Sender(datagramSocket);
            receiver = new Receiver(datagramSocket);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Sender getSender() {
        return sender;
    }
}
