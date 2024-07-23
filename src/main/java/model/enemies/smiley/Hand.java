package model.enemies.smiley;

import controller.Controller;
import controller.GameManager;
import model.BulletModel;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Point;

public abstract class Hand extends Enemy {
    private boolean susceptible;
    private long lastShotTime;
    public Hand(Point center, double velocity) {
        super(center, velocity);
        width = GameManager.configs.HAND_WIDTH;
        height = GameManager.configs.HAND_HEIGHT;
        frame = new Frame(width+40, height+40, center.getX()-width/2-20, center.getY()-height/2-20,
                false, false, width+40, height+40);
        HP = 100;
        addVertexes();
        frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        GameManager.getINSTANCE().getGameModel().getFrames().add(frame);
        GameManager.getINSTANCE().getGameModel().getEnemies().add(this);
    }
    public void setCenter(Point center){
        this.center = center;
        frame.setX(center.getX()-width/2-20);
        frame.setY(center.getY()-height/2-20);
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
                    height/2, 3, false, frame);
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
