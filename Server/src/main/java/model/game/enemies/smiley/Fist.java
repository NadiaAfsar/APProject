package model.game.enemies.smiley;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
import controller.save.Configs;
import model.game.EpsilonModel;
import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Fist extends Enemy implements Movable {
    private double xDirection;
    private double yDirection;
    private boolean powerPunch;
    private boolean quake;
    private long quakeActivated;
    private boolean slap;
    private Smiley smiley;
    private EpsilonModel punchingEpsilon;
    private EpsilonModel slappingEpsilon;
    public Fist(Point center, double velocity, Smiley smiley, GameManager gameManager, EpsilonModel epsilon) {
        super(center, velocity, gameManager, epsilon);
        logger = Logger.getLogger(Fist.class.getName());
        width = MyApplication.configs.FIST_WIDTH;
        height = MyApplication.configs.FIST_HEIGHT;
        myFrame = new MyFrame(width+30, height+30, center.getX()-width/2-15, center.getY()-height/2-15,
                false, false, width+30, height+30, gameManager);
        velocityPower*= 30;
        addVertexes();
        this.smiley = smiley;
        myFrame.getEnemies().add(this);
        Controller.addEnemyView(this, gameManager);
        gameManager.getGameModel().getFrames().add(myFrame);
        gameManager.getGameModel().getEnemies().add(this);
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
            MyFrame epsilonMyFrame = punchingEpsilon.getInitialMyFrame();
            double x = epsilonMyFrame.getX()+ epsilonMyFrame.getWidth()*xDirection-this.myFrame.getWidth()/2;
            double y = epsilonMyFrame.getY()+ epsilonMyFrame.getHeight()*yDirection-this.myFrame.getHeight()/2;
            return new Direction(center, new Point(x,y));
        }
        else if (quake && quakeActivated == 0){
            logger.debug(quakeActivated);
            return new Direction(center, new Point(center.getX(), Configs.FRAME_SIZE.height- myFrame.getHeight()/2-30));
        }
        else if (slap){
            return new Direction(center,slappingEpsilon.getCenter());
        }
        Direction direction = new Direction();
        direction.setDy(0);
        direction.setDy(0);
        return direction;
    }

    public void setDirection(double x, double y) {
        xDirection = x;
        yDirection = y;
    }

    @Override
    public void specialMove() {
        if (powerPunch){
            checkPowerPunch();
        }
        if (quake){
            checkQuake();
        }
    }
    private void checkPowerPunch() {
        MyFrame epsilonMyFrame = punchingEpsilon.getInitialMyFrame();
        double x = epsilonMyFrame.getX()+ epsilonMyFrame.getWidth()*xDirection-this.myFrame.getWidth()/2;
        double y = epsilonMyFrame.getY()+ epsilonMyFrame.getHeight()*yDirection-this.myFrame.getHeight()/2;
        if (Math.abs(center.getX()-x) <= 10 && Math.abs(center.getY()-y) <= 10){
            powerPunch = false;
            if (xDirection == 0 || xDirection == 1){
                epsilonMyFrame.setX(epsilonMyFrame.getX()+(xDirection*(-2)+1)*30);
                epsilonMyFrame.setWidth(epsilonMyFrame.getWidth()-30);
            }
            else {
                epsilonMyFrame.setY(epsilonMyFrame.getY()+(yDirection*(-2)+1)*30);
                epsilonMyFrame.setHeight(epsilonMyFrame.getHeight()-30);
            }
            smiley.setPowerPunch(false);
        }
    }
    private void checkQuake(){
        long currentTime = System.currentTimeMillis();
        if (center.getY() >= Configs.FRAME_SIZE.height- myFrame.getHeight()/2-30) {
            logger.debug(center.getY());
            gameManager.getGameModel().setQuake(true);
            quakeActivated = currentTime;
            setCenter(new Point(center.getX(), center.getY()-100));
            logger.debug("quake activated");
        }
        else if (currentTime - quakeActivated >= 8000 && quakeActivated != 0){
            quake = false;
            quakeActivated = 0;
            smiley.setQuake(false);
            gameManager.getGameModel().setQuake(false);
            logger.debug("quake ended");
        }
    }

    public void setPowerPunch(boolean powerPunch) {
        this.powerPunch = powerPunch;
        if (powerPunch){
            int random = (int)Math.round(Math.random()*(smiley.getEpsilons().size()-1));
            punchingEpsilon = smiley.getEpsilons().get(random);
        }
    }

    public void setQuake(boolean quake) {
        this.quake = quake;
    }
    public void activateSlap() {
        int random = (int)Math.round(Math.random()*(smiley.getEpsilons().size()-1));
        slappingEpsilon = smiley.getEpsilons().get(random);
        slap = true;
        damage = 5;
    }
    public void slapped(){
        damage = 0;
        slap = false;
        smiley.setSlap(false);
    }
    public void setCenter(Point center){
        this.center = center;
        myFrame.setX(center.getX()-width/2-15);
        myFrame.setY(center.getY()-height/2-15);
    }
}
