package network.UDP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Sender extends Thread{
    private DatagramSocket datagramSocket;
    private Object lock;

    public Sender(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
        this.lock = new Object();
    }
    private void sendData(byte[] data, InetSocketAddress inetSocketAddress) {
        DatagramPacket packet = new DatagramPacket(data, data.length, inetSocketAddress);
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendString(String string, InetSocketAddress inetSocketAddress) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        printWriter.println(string);
        printWriter.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        sendData(data, inetSocketAddress);
    }
    public void sendFile(File file, InetSocketAddress inetSocketAddress) {
        synchronized (lock) {
            sendString(file.getName(), inetSocketAddress);
            ArrayList<byte[]> data = FileHandler.extractData(file);
            sendString(data.size()+"", inetSocketAddress);
            sendString(data.get(data.size() - 1).length + "", inetSocketAddress);
            for (int i = 0; i < data.size(); i++) {
                int finalI = i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                                sendData(data.get(finalI), inetSocketAddress);
                        }
                    }).start();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
