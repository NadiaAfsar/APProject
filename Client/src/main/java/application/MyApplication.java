package application;

import controller.save.Configs;
import controller.save.ReaderWriter;
import view.ConnectionFrame;

public class MyApplication implements Runnable{
    public static Configs configs;
    public static ReaderWriter readerWriter;
    @Override
    public void run() {
        readerWriter = new ReaderWriter();
        configs = readerWriter.getConfigs();
        new ConnectionFrame();

    }
}
