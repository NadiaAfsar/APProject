package controller;

import model.BulletModel;
import model.enemies.SquarantineModel;
import model.enemies.mini_boss.Barricados;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.normal.Omenoct;
import model.enemies.normal.Wyrm;
import model.enemies.smiley.Smiley;
import model.frame.Frame;
import model.enemies.Enemy;
import model.interfaces.movement.Point;

import java.util.ArrayList;

public class GameManagerHelper {
//    public static <T> void removeFrom(ArrayList<T> list1, ArrayList<T> list2) {
//        for (int i= 0; i < list2.size(); i++) {
//            list1.remove(list2.get(i));
//        }
//    }
    public static Point getRandomPosition(boolean[][] addedEnemies, double width, double height) {
        double x = 0;
        double y = 0;
        int random1 = (int)(Math.random()*4);
        int random2 = (int)(Math.random()*6);
        if (!addedEnemies[random1][random2]) {
            addedEnemies[random1][random2] = true;
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
        return getRandomPosition(addedEnemies, width, height);
    }
    public static Enemy getNewEnemy(Point point, int hp, double velocity) {
        int x = (int)(Math.random()*2);
        //Enemy enemy = new Wyrm(new Point(440,300),velocity, hp);
        //Enemy enemy = new SquarantineModel(point, hp, velocity, GameManager.getINSTANCE().getGameModel().getEpsilon().getFrame());
        //Enemy enemy = new Omenoct(point, velocity, hp, GameManager.getINSTANCE().getGameModel().getEpsilon().getFrame());
        //Enemy enemy = new TrigorathModel(point, hp, velocity, GameManager.getINSTANCE().getGameModel().getEpsilon().getFrame());
        //Enemy enemy = new Necropick(point, velocity, hp, GameManager.getINSTANCE().getGameModel().getEpsilon().getFrame());
        //Enemy enemy = new Archmire(point,velocity,hp,GameManager.getINSTANCE().getGameModel().getEpsilon().getFrame());
        //Enemy enemy = new Barricados(new Point(1000, 300), velocity, true);
        //Enemy enemy = new BlackOrb(new Point(600, 300),velocity);
        Enemy enemy = new Smiley(new Point(700,100),velocity);
        GameManager.getINSTANCE().setSmiley((Smiley) enemy);
        return enemy;
    }
    public static boolean checkFrameCollisionWithBullet(BulletModel bullet) {
        Frame frame = bullet.getFrame();
        if (frame != null) {
            if (bullet.getX2() <= frame.getX()) {
                frame.changeWidth(bullet, -10);
                return true;
            } else if (bullet.getX2() >= frame.getX() + frame.getWidth()) {
                frame.changeWidth(bullet, 10);
                return true;
            } else if (bullet.getY2() <= frame.getY()) {
                frame.changeHeight(bullet, -10);
                return true;
            } else if (bullet.getY2() >= frame.getHeight() + frame.getY()) {
                frame.changeHeight(bullet, 10);
                return true;
            }
        }
        return false;
    }

}
