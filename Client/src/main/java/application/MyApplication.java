package application;

import controller.GameManager;
import controller.save.Configs;
import controller.save.ReaderWriter;
import network.ClientHandler;

public class MyApplication implements Runnable{
    public static Configs configs;
    public static ReaderWriter readerWriter;
    @Override
    public void run() {
        readerWriter = new ReaderWriter();
        configs = readerWriter.getConfigs();
        ClientHandler clientHandler = new ClientHandler();
        new GameManager(clientHandler, true).initialize();
    }
}
