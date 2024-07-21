package model.enemies.normal.archmire;

import controller.Controller;
import controller.GameManager;
import controller.audio.AudioController;
import model.Collectible;
import model.frame.Frame;
import model.interfaces.movement.Point;

public class MiniArchmire extends Archmire{
    public MiniArchmire(Point center, double velocity, int hp, Frame frame) {
        super(center, velocity, hp, frame);
        this.HP = hp;
    }
    protected void setArchmire() {
        width = GameManager.configs.MINI_ARCHMIRE_WIDTH;
        height = GameManager.configs.MINI_ARCHMIRE_HEIGHT;
        addVertexes();
        GameManager.getINSTANCE().getGameModel().getEnemies().add(this);
        Controller.addArchmireView(this);
        start();
    }
    public void addCollective() {
        int[] x = new int[]{-10, 10};
        int[] y = new int[]{-10, 10};
        for (int i = 0; i < 2; i++) {
            Collectible collectible = new Collectible((int)center.getX()+x[i], (int)center.getY()+y[i],3);
            GameManager.getINSTANCE().getGameModel().getCollectibles().add(collectible);
            Controller.addCollectibleView(collectible);
        }
    }
    protected void die() {
        removeAll();
        addCollective();
        GameManager.getINSTANCE().getDiedEnemies().add(this);
        Controller.removeArchmireView(this);
        AudioController.addEnemyDyingSound();
        interrupt();
    }
}
