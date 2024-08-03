package model.game.enemies.normal.archmire;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
import controller.audio.AudioController;
import model.game.Collectible;
import model.game.EpsilonModel;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;

public class MiniArchmire extends Archmire{
    public MiniArchmire(Point center, double velocity, int hp, MyFrame myFrame, GameManager gameManager, EpsilonModel epsilon) {
        super(center, velocity, hp, myFrame, gameManager, epsilon);
        this.HP = hp;
    }
    protected void setArchmire() {
        width = MyApplication.configs.MINI_ARCHMIRE_WIDTH;
        height = MyApplication.configs.MINI_ARCHMIRE_HEIGHT;
        addVertexes();
        gameManager.getGameModel().getEnemies().add(this);
        Controller.addArchmireView(this, gameManager);
        start();
    }
    public void addCollective() {
        int[] x = new int[]{-10, 10};
        int[] y = new int[]{-10, 10};
        for (int i = 0; i < 2; i++) {
            Collectible collectible = new Collectible((int)center.getX()+x[i], (int)center.getY()+y[i],3);
            gameManager.getGameModel().getCollectibles().add(collectible);
            Controller.addCollectibleView(collectible, gameManager);
        }
    }
    protected void die() {
        removeAll();
        addCollective();
        gameManager.getGameModel().getDiedEnemies().add(this);
        Controller.removeArchmireView(this, gameManager);
        AudioController.addEnemyDyingSound();
        gameManager.getGameModel().getCurrentWave().newEnemyDied();
        died = true;
    }
}
