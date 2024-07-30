package model.game.enemies.smiley;

import controller.Controller;
import controller.GameManager;
import model.game.BulletModel;
import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;

public abstract class Hand extends Enemy {
    private boolean susceptible;
    private long lastShotTime;
    public Hand(Point center, double velocity) {
        super(center, velocity);
        width = GameManager.configs.HAND_WIDTH;
        height = GameManager.configs.HAND_HEIGHT;
        myFrame = new MyFrame(width+80, height+80, center.getX()-width/2-40, center.getY()-height/2-40,
                false, false, width+80, height+80);
        HP = 100;
        addVertexes();
        myFrame.getEnemies().add(this);
        Controller.addEnemyView(this);
        GameManager.getINSTANCE().getGameModel().getFrames().add(myFrame);
        GameManager.getINSTANCE().getGameModel().getEnemies().add(this);
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
            BulletModel bulletModel = new BulletModel(center, GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter(),
                    height/2, 3, false, myFrame);
            GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
            lastShotTime = currentTime;
        }
    }
    public void decreaseHP(int x){
        if (susceptible){
            super.decreaseHP(x);
        }
    }
}
