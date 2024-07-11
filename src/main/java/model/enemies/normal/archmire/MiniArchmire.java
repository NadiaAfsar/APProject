package model.enemies.normal.archmire;

import controller.Controller;
import controller.GameManager;
import model.Collective;
import movement.Point;

import java.awt.*;

public class MiniArchmire extends Archmire{
    public MiniArchmire(Point center, double velocity, int hp) {
        super(center, velocity, hp);
        this.HP = hp;
    }
    public void addCollective() {
        int[] x = new int[]{-10, 10};
        int[] y = new int[]{-10, 10};
        for (int i = 0; i < 2; i++) {
            Collective collective = new Collective((int)center.getX()+x[i], (int)center.getY()+y[i], Color.RED, 3);
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collective);
            Controller.addCollectiveView(collective);
        }
    }
}
