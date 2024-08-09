package controller.game_manager;

import application.MyApplication;
import controller.save.Configs;
import model.game.BulletModel;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.game.enemies.mini_boss.Barricados;
import model.game.enemies.mini_boss.black_orb.BlackOrb;
import model.game.enemies.normal.Necropick;
import model.game.enemies.normal.Omenoct;
import model.game.enemies.normal.Wyrm;
import model.game.enemies.normal.archmire.Archmire;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GameManagerHelper {
    public static Point getRandomPosition(double width, double height) {
        double x = 0;
        double y = 0;
        int random1 = (int)(Math.random()*4);
        int random2 = (int)(Math.random()*6);
            if (random1 == 0) {
                y = -10;
                x = random2 * ( width / 6) + 10;
            } else if (random1 == 1) {
                x = (int) width;
                y = random2 * ( height/ 6) + 10;
            } else if (random1 == 2) {
                y = (int)height;
                x = random2 * ( width / 6) + 10;
            }
            else {
                x = -10;
                y = random2 * ( height/ 6) + 10;
            }
            return new Point(x,y);
    }
    public static Enemy getNewEnemy(Point point, int hp, double velocity, int enemy, EpsilonModel epsilon) {
        Class clazz = null;
        point = new Point(point.getX()+epsilon.getInitialFrame().getX(), point.getY()+epsilon.getInitialFrame().getY());
        if (enemy == 0){
            clazz = MyApplication.enemiesClass.get(4);
        }
        else if (enemy == 1){
            clazz = MyApplication.enemiesClass.get(9);
        }
        else if (enemy == 2){
            clazz = MyApplication.enemiesClass.get(3);
        }
        else if (enemy == 3){
            if (epsilon.getGameManager().isOnline()){
                clazz = MyApplication.enemiesClass.get(4);
            }
            else {
                clazz = MyApplication.enemiesClass.get(5);
            }
        }
        else if (enemy == 4){
            if (epsilon.getGameManager().isOnline()){
                clazz = MyApplication.enemiesClass.get(9);
            }
            else {
                clazz = MyApplication.enemiesClass.get(8);
            }
        }
        else if (enemy == 5){
            clazz = MyApplication.enemiesClass.get(0);
        }
        else if (enemy == 6){
            clazz = MyApplication.enemiesClass.get(10);
        }
        else {
            clazz = MyApplication.enemiesClass.get(2);
        }
        Enemy enemy1 = null;
        try {
            Constructor<? extends Enemy> constructor = clazz.getConstructor(Point.class, int.class, double.class, GameManager.class, EpsilonModel.class);
            enemy1 = constructor.newInstance(point, hp, velocity, epsilon.getGameManager(), epsilon);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return enemy1;
    }
    public static boolean checkFrameCollisionWithBullet(BulletModel bullet) {
        MyFrame myFrame = bullet.getFrame();
        if (myFrame != null) {
            if (bullet.getX2() <= myFrame.getX()) {
                myFrame.changeWidth(bullet, -10);
                return true;
            } else if (bullet.getX2() >= myFrame.getX() + myFrame.getWidth()) {
                myFrame.changeWidth(bullet, 10);
                return true;
            } else if (bullet.getY2() <= myFrame.getY()) {
                myFrame.changeHeight(bullet, -10);
                return true;
            } else if (bullet.getY2() >= myFrame.getHeight() + myFrame.getY()) {
                myFrame.changeHeight(bullet, 10);
                return true;
            }
        }
        return false;
    }

}
