package controller.game_manager;

import controller.game_manager.GameManager;
import controller.save.Configs;
import model.game.BulletModel;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.game.enemies.SquarantineModel;
import model.game.enemies.TrigorathModel;
import model.game.enemies.mini_boss.Barricados;
import model.game.enemies.mini_boss.black_orb.BlackOrb;
import model.game.enemies.normal.Necropick;
import model.game.enemies.normal.Omenoct;
import model.game.enemies.normal.Wyrm;
import model.game.enemies.normal.archmire.Archmire;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;

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
        point = new Point(point.getX()+epsilon.getInitialFrame().getX(), point.getY()+epsilon.getInitialFrame().getY());
        if (enemy == 0){
            return new TrigorathModel(point, hp, velocity, epsilon.getInitialFrame(), epsilon.getGameManager(), epsilon);
        }
        else if (enemy == 1){
            return new SquarantineModel(point, hp, velocity, epsilon.getInitialFrame(), epsilon.getGameManager(), epsilon);
        }
        else if (enemy == 2){
            return new Wyrm(new Point((point.getX()- epsilon.getInitialFrame().getX())/ epsilon.getInitialFrame().getWidth()*
                    (Configs.FRAME_SIZE.width-200)+100, (point.getY()- epsilon.getInitialFrame().getY())/ epsilon.
                    getInitialFrame().getHeight()*(Configs.FRAME_SIZE.height-200)+100), velocity, hp, epsilon.getGameManager(), epsilon);
        }
        else if (enemy == 3){
            return new Omenoct(point, velocity, hp, epsilon.getInitialFrame(), epsilon.getGameManager(), epsilon);
        }
        else if (enemy == 4){
            return new Necropick(point, velocity, hp, epsilon.getInitialFrame(), epsilon.getGameManager(), epsilon);
        }
        else if (enemy == 5){
            return new Archmire(point, velocity, hp, epsilon.getInitialFrame(), epsilon.getGameManager(), epsilon);
        }
        else if (enemy == 6){
            int x = 100 + (int)(Math.random()*(Configs.FRAME_SIZE.width-500));
            int y = 100 + (int)(Math.random()*(Configs.FRAME_SIZE.height-300));
            int isRigid = (int) (Math.random()*2);
            if (isRigid == 0) {
                return new Barricados(new Point(x, y), velocity, false, epsilon.getGameManager(), epsilon);
            }
            return new Barricados(new Point(x, y), velocity, true, epsilon.getGameManager(), epsilon);
        }
        int x = 100 + (int)(Math.random()*(Configs.FRAME_SIZE.width-400));
        int y = 100 + (int)(Math.random()*(Configs.FRAME_SIZE.height-350));
        return new BlackOrb(new Point(x,y),velocity, epsilon.getGameManager(), epsilon);
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
