package application;

import controller.ClassFinder;
import controller.save.Configs;
import controller.save.ReaderWriter;
import model.game.enemies.Enemy;
import view.ConnectionFrame;

import java.util.ArrayList;


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
