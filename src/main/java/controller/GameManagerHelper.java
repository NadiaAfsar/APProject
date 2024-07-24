package controller;

import controller.save.Configs;
import model.BulletModel;
import model.enemies.SquarantineModel;
import model.enemies.TrigorathModel;
import model.enemies.mini_boss.Barricados;
import model.enemies.mini_boss.black_orb.BlackOrb;
import model.enemies.normal.Necropick;
import model.enemies.normal.Omenoct;
import model.enemies.normal.Wyrm;
import model.enemies.normal.archmire.Archmire;
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
    public static Enemy getNewEnemy(Point point, int hp, double velocity, int enemy) {
        Frame frame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
        if (enemy == 0){
            return new TrigorathModel(point, hp, velocity, frame);
        }
        else if (enemy == 1){
            return new SquarantineModel(point, hp, velocity, frame);
        }
        else if (enemy == 2){
            return new Wyrm(new Point((point.getX()- frame.getX())/ frame.getWidth()* (Configs.FRAME_SIZE.width-200)+100, (point.getY()- frame.getY())/
                    frame.getHeight()*(Configs.FRAME_SIZE.height-200)+100), velocity, hp);
        }
        else if (enemy == 3){
            return new Omenoct(point, velocity, hp, frame);
        }
        else if (enemy == 4){
            return new Necropick(point, velocity, hp, frame);
        }
        else if (enemy == 5){
            return new Archmire(point, velocity, hp, frame);
        }
        else if (enemy == 6){
            int x = 100 + (int)(Math.random()*(Configs.FRAME_SIZE.width-500));
            int y = 100 + (int)(Math.random()*(Configs.FRAME_SIZE.height-300));
            int isRigid = (int) (Math.random()*2);
            if (isRigid == 0) {
                return new Barricados(new Point(x, y), velocity, false);
            }
            return new Barricados(new Point(x, y), velocity, true);
        }
        int x = 100 + (int)(Math.random()*(Configs.FRAME_SIZE.width-400));
        int y = 100 + (int)(Math.random()*(Configs.FRAME_SIZE.height-350));
        return new BlackOrb(new Point(x,y),velocity);
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
