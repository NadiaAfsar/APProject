package network.UDP;

import application.MyApplication;

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
    private InetSocketAddress serverAddress;
    private Object lock;

    public Sender(DatagramSocket datagramSocket) {
        this.lock = new Object();
        this.datagramSocket = datagramSocket;
        serverAddress = new InetSocketAddress("localHost", MyApplication.configs.SERVER_PORT);
    }
    private void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress);
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendString(String string) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
            printWriter.println(string);
            printWriter.flush();
            byte[] data = byteArrayOutputStream.toByteArray();
            sendData(data);
    }
    public void sendFile(File file) {
        synchronized (lock) {
            sendString(file.getName());
            ArrayList<byte[]> data = FileHandler.extractData(file);
            sendString(data.size() + "");
            sendString(data.get(data.size() - 1).length + "");
            for (int i = 0; i < data.size(); i++) {
                int finalI = i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                                sendData(data.get(finalI));
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
