package model.enemies.smiley;

import controller.Controller;
import controller.GameManager;
import controller.save.Configs;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;

public class Fist extends Enemy implements Movable {
    private double xDirection;
    private double yDirection;
    private boolean powerPunch;
    private boolean quake;
    private long quakeActivated;
    private boolean slap;
    private Smiley smiley;
    public Fist(Point center, double velocity, Smiley smiley) {
        super(center, velocity);
        width = GameManager.configs.FIST_WIDTH;
        height = GameManager.configs.FIST_HEIGHT;
        frame = new Frame(width+30, height+30, center.getX()-width/2-15, center.getY()-height/2-15,
                false, false);
        velocityPower = 5;
        this.smiley = smiley;
        frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        GameManager.getINSTANCE().getGameModel().getFrames().add(frame);
    }

    @Override
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{Math.PI+0.06, Math.PI+0.34, Math.PI+0.49, Math.PI+0.62, Math.PI+0.85, Math.PI+1.18,
        Math.PI+1.49, 1.5*Math.PI, 2*Math.PI-1.39, 2*Math.PI-1.2, 2*Math.PI-1.01, 2*Math.PI-0.74, 2*Math.PI-0.46,
                2*Math.PI-0.02, 0.22, 0.33, 0.95 , 1.23, Math.PI-1.52, Math.PI-1.4, Math.PI-1.53, Math.PI-1.2, Math.PI-0.78,
        Math.PI-0.6, Math.PI-0.4};
        double[] radius = new double[]{50*width/100, 49.97*width/100, 46.5*width/100, 40.8*width/100, 41.1*width/100,
        34.5*width/100, 39.11*width/100, 42*width/100, 40.6*width/100, 38.6*width/100, 44.9*width/100, 47.4*width/100, 46.9*width/100,
        43*width/100, 50.21*width/100, 51.8*width/100, 50.2*width/100, 42,8*width/100, 40*width/100, 32.2*width/100,
        28*width/100, 26.5*width/100, 39.5*width/100, 39.4*width/100, 50.5*width/100};
        for (int i = 0; i < 25; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, radius[i]);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 1.32*Math.PI, 50.77*width/55);
    }

    @Override
    public void addCollective() {

    }

    @Override
    public Direction getDirection() {
        if (powerPunch) {
            Frame epsilonFrame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
            double x = epsilonFrame.getX()+epsilonFrame.getWidth()*xDirection-this.frame.getWidth()/2;
            double y = epsilonFrame.getY()+epsilonFrame.getHeight()*yDirection-this.frame.getHeight()/2;
            return new Direction(center, new Point(x,y));
        }
        else if (quake){
            return new Direction(center, new Point(center.getX(), Configs.FRAME_SIZE.height-frame.getHeight()/2-30));
        }
        else if (slap){
            return new Direction(center,GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
        }
        return new Direction(new Point(0, 0), new Point(0, 0));
    }

    public void setDirection(double x, double y) {
        xDirection = x;
        yDirection = y;
    }

    @Override
    public void specialMove() {
        if (powerPunch){
            Frame epsilonFrame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
            double x = epsilonFrame.getX()+epsilonFrame.getWidth()*xDirection-this.frame.getWidth()/2;
            double y = epsilonFrame.getY()+epsilonFrame.getHeight()*yDirection-this.frame.getHeight()/2;
            if (Math.abs(center.getX()-x) <= 10 && Math.abs(center.getY()-y) <= 10){
                powerPunch = false;
                if (xDirection == 0 || xDirection == 1){
                    epsilonFrame.setX((xDirection*(-2)+1)*30);
                    epsilonFrame.setWidth(epsilonFrame.getWidth()-30);
                }
                else {
                    epsilonFrame.setY((yDirection*(-2)+1)*30);
                    epsilonFrame.setHeight(epsilonFrame.getHeight()-30);
                }
                smiley.setPowerPunch(false);
            }
        }
        if (quake){
            if (center.getY() >= Configs.FRAME_SIZE.height-frame.getHeight()/2-30) {
                GameManager.getINSTANCE().setQuake(true);
                long currentTime = System.currentTimeMillis();
                if (quakeActivated == 0 || currentTime-quakeActivated < 2000){
                    quakeActivated = currentTime;
                }
                else {
                    quake = false;
                    smiley.setQuake(false);
                    quakeActivated = 0;
                }
            }
        }
    }

    public void setPowerPunch(boolean powerPunch) {
        this.powerPunch = powerPunch;
    }

    public void setQuake(boolean quake) {
        this.quake = quake;
    }
    public void activateSlap() {
        slap = true;
        damage = 5;
    }
    public void slapped(){
        damage = 0;
        slap = false;
        smiley.setSlap(false);
    }
}
