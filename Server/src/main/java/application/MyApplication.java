package application;

import controller.save.Configs;
import controller.save.ReaderWriter;
import network.ServerHandler;

public class MyApplication implements Runnable{
    public static ReaderWriter readerWriter;
    @Override
    public void run() {
        readerWriter = new ReaderWriter();
        ServerHandler.getInstance().initiate();
    }
}
