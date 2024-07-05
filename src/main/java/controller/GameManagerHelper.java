package controller;

import model.enemies.Enemy;
import model.enemies.SquarantineModel;
import model.enemies.TrigorathModel;
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

}
