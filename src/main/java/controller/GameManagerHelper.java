package controller;

import model.BulletModel;
import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.enemies.TrigorathModel;
import model.game.GameModel;
import movement.Point;

import java.util.ArrayList;

public class GameManagerHelper {
    public static <T> void removeFrom(ArrayList<T> list1, ArrayList<T> list2) {
        for (int i= 0; i < list2.size(); i++) {
            list1.remove(list2.get(i));
        }
    }
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
        Enemy enemy;
        if (x == 0) {
            enemy = new SquarantineModel(point, hp, velocity);
        }
        else {
            enemy = new TrigorathModel(point, hp, velocity);
        }
        return enemy;
    }
    public static boolean checkFrameCollisionWithBullet(BulletModel bullet) {
        GameModel gameModel = GameManager.getINSTANCE().getGameModel();
        if (bullet.getX2() <= 0) {
            changeWidth(gameModel, bullet, -10);
            return true;
        }
        else if (bullet.getX2() >= gameModel.getWidth()) {
            changeWidth(gameModel, bullet, 10);
            return true;
        }
        else if (bullet.getY2() <= 0) {
            changeHeight(gameModel, bullet, -10);
            return true;
        }
        else if (bullet.getY2() >= gameModel.getHeight()) {
            changeHeight(gameModel, bullet, 10);
            return true;
        }
        return false;
    }
    private static void changeWidth(GameModel gameModel, BulletModel bullet, int x) {
        gameModel.setWidth(gameModel.getWidth()+10);
        gameModel.setX(gameModel.getX()+ x + bullet.getDirection().getDx()*20);
        gameModel.setY(gameModel.getY() + bullet.getDirection().getDy()*20);
        if (GameManager.getINSTANCE().checkPosition()) {
            gameModel.setWidth(gameModel.getWidth()-10);
        }
    }
    private static void changeHeight(GameModel gameModel, BulletModel bullet, int y) {
        gameModel.setHeight(gameModel.getHeight()+10);
        gameModel.setX(gameModel.getX() + bullet.getDirection().getDx()*20);
        gameModel.setY(gameModel.getY() + y + bullet.getDirection().getDy()*20);
        if (GameManager.getINSTANCE().checkPosition()) {
            gameModel.setHeight(gameModel.getHeight()-10);
        }
    }

}
