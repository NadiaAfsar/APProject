package model.enemies.smiley;

import controller.Controller;
import controller.GameManager;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Point;

public abstract class Hand extends Enemy {
    private boolean susceptible;
    public Hand(Point center, double velocity) {
        super(center, velocity);
        width = GameManager.configs.HAND_WIDTH;
        height = GameManager.configs.HAND_HEIGHT;
        frame = new Frame(width+20, height+20, center.getX()-width/2-10, center.getY()-height/2-10,
                false, false);
        HP = 100;
        addVertexes();
        frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        GameManager.getINSTANCE().getGameModel().getFrames().add(frame);
    }
    public void setCenter(Point center){
        this.center = center;
        frame.setX(center.getX()-width/2-10);
        frame.setY(center.getY()-height/2-10);
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
}
