package model.enemies.smiley;

import controller.Controller;
import controller.GameManager;
import model.EpsilonModel;
import model.Interference;
import model.interfaces.movement.Point;

import java.util.UUID;

public class SmileyAoEAttack {
    private Point center;
    private double radius;
    private int clarity;
    private Smiley smiley;
    private long lastCheck;
    private String ID;
    public SmileyAoEAttack(Smiley smiley, Point center){
        ID = UUID.randomUUID().toString();
        this.smiley = smiley;
        radius = GameManager.configs.EPSILON_RADIUS+15;
        this.center = center;
        clarity = 6;
        Controller.addSmileyAoE(this);
    }
    public boolean update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCheck >= 1000) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (Interference.circleIsInCircle(epsilon.getCenter(), epsilon.getRadius(), center, radius)) {
                epsilon.decreaseHP(2);
            }
            clarity--;
            if (clarity == 0) {
                smiley.getAoEAttacks().remove(this);
                Controller.removeAoEAttackView(ID);
                return true;
            }
            lastCheck = currentTime;
        }
        return false;
    }
    public double getX(){
        return center.getX()-radius;
    }
    public double getY(){
        return center.getY()-radius;
    }
    public double getWidth() {
        return 2*radius;
    }
    public double getHeight() {
        return 2*radius;
    }

    public String getID() {
        return ID;
    }

    public int getClarity() {
        return clarity;
    }
}
