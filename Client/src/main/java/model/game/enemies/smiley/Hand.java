package model.game.enemies.smiley;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
import model.game.BulletModel;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;

public abstract class Hand extends Enemy {
    private boolean susceptible;
    private long lastShotTime;
    public Hand(Point center, int hp, double velocity, GameManager gameManager, EpsilonModel epsilon) {
        super(center, hp, velocity, gameManager, epsilon);
        width = MyApplication.configs.HAND_WIDTH;
        height = MyApplication.configs.HAND_HEIGHT;
        myFrame = new MyFrame(width+80, height+80, center.getX()-width/2-40, center.getY()-height/2-40,
                false, false, width+80, height+80, gameManager);
        HP = 100;
        addVertexes();
        myFrame.getEnemies().add(this);
        Controller.addEnemyView(this, gameManager);
        gameManager.getGameModel().getFrames().add(myFrame);
        gameManager.getGameModel().getEnemies().add(this);
    }
    public void setCenter(Point center){
        this.center = center;
        myFrame.setX(center.getX()-width/2-40);
        myFrame.setY(center.getY()-height/2-40);
        moveVertexes();
    }
    @Override
    public void addCollective() {

    }

    public boolean isSusceptible() {
        return susceptible;
    }

    public void setSusceptible(boolean susceptible) {
        this.susceptible = susceptible;
    }
    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 2000) {
            BulletModel bulletModel = new BulletModel(center, gameManager.getGameModel().getEpsilons().get(0).getCenter(),
                    height/2, 3, false, myFrame, gameManager);
            gameManager.getGameModel().getEnemiesBullets().add(bulletModel);
            lastShotTime = currentTime;
        }
    }
    public void decreaseHP(int x){
        if (susceptible){
            super.decreaseHP(x);
        }
    }
}
