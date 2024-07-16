package model.enemies.normal.archmire;

import controller.Controller;
import controller.GameManager;
import controller.audio.AudioController;
import model.Collective;
import model.frame.Frame;
import model.interfaces.movement.Point;

import java.awt.*;

public class MiniArchmire extends Archmire{
    public MiniArchmire(Point center, double velocity, int hp, Frame frame) {
        super(center, velocity, hp, frame);
        width = GameManager.configs.MINI_ARCHMIRE_WIDTH;
        height = GameManager.configs.MINI_ARCHMIRE_HEIGHT;
        this.HP = hp;
    }
    public void addCollective() {
        int[] x = new int[]{-10, 10};
        int[] y = new int[]{-10, 10};
        for (int i = 0; i < 2; i++) {
            Collective collective = new Collective((int)center.getX()+x[i], (int)center.getY()+y[i], Color.RED,
                    3, frame);
            GameManager.getINSTANCE().getGameModel().getCollectives().add(collective);
            Controller.addCollectiveView(collective);
        }
    }
    protected void die() {
        addCollective();
        GameManager.getINSTANCE().getDiedEnemies().add(this);
        Controller.removeArchmireView(this);
        AudioController.addEnemyDyingSound();
    }
}
