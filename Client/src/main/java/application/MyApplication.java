package application;

import controller.save.Configs;
import controller.save.ReaderWriter;
import model.game.enemies.Enemy;
import model.game.enemies.SquarantineModel;
import model.game.enemies.TrigorathModel;
import model.game.enemies.mini_boss.Barricados;
import model.game.enemies.mini_boss.black_orb.BlackOrb;
import model.game.enemies.normal.Necropick;
import model.game.enemies.normal.Omenoct;
import model.game.enemies.normal.Wyrm;
import model.game.enemies.normal.archmire.Archmire;
import model.game.enemies.smiley.Fist;
import model.game.enemies.smiley.Hand;
import model.game.enemies.smiley.Smiley;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import view.ConnectionFrame;

import java.util.ArrayList;


public class MyApplication implements Runnable{
    public static Configs configs;
    public static ReaderWriter readerWriter;
    public static ArrayList<Class> enemiesClass;
    @Override
    public void run() {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage("my.package.prefix"));
        enemiesClass = new ArrayList<>(reflections.getSubTypesOf(Enemy.class));
        readerWriter = new ReaderWriter();
        configs = readerWriter.getConfigs();
        new ConnectionFrame();

    }
}
