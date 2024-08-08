package network.UDP;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class Receiver extends Thread{
    private DatagramSocket datagramSocket;
    private Object lock;
    private File file;
    private Logger logger;
    public Receiver(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
        logger = Logger.getLogger(Receiver.class.getName());
        lock = new Object();
    }
    private byte[] receiveData(int size) {
        byte[] data = new byte[size];
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        try {
            datagramSocket.receive(datagramPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return datagramPacket.getData();
    }
    public void receiveFile() {
        synchronized (lock) {
            String name = getString();
            String packetsString = getString();
            int packets = Integer.parseInt(packetsString);
            String lastPacketString = getString();
            int lastPacket = Integer.parseInt(lastPacketString);
            ArrayList<byte[]> dataArray = new ArrayList<>();
            for (int i = 0; i < packets; i++) {
                int finalI = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] data = null;
                        if (finalI == packets - 1) {
                            data = receiveData(lastPacket);
                        } else {
                            data = receiveData(65507);
                        }
                        dataArray.add(data);
                        if (dataArray.size() == packets) {
                            file = FileHandler.getFile(name, "src/main/resources/data/files", dataArray);
                        }
                    }
                }).start();
            }
        }
    }
    public File getFile(){
        file = null;
        receiveFile();
        while (file == null){
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }
    public String getString() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receiveData(1024));
        Scanner scanner = new Scanner(byteArrayInputStream);
        return scanner.nextLine();
    }
}
