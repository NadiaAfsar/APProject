package model.enemies.smiley;

import controller.GameManager;
import model.EpsilonModel;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Smiley extends Enemy implements Movable {
    private int phase;
    private ArrayList<Enemy> hands;
    private boolean squeezing;
    private boolean susceptible;
    public Smiley(Point center, double velocity) {
        super(center, velocity);
        logger = Logger.getLogger(Smiley.class.getName());
        phase = 1;
        width = 2 * GameManager.configs.SMILEY_RADIUS;
        height = 2 * GameManager.configs.SMILEY_RADIUS;
        frame = new Frame(width+50, height+50, center.getX()-width/2-25, center.getY()-height/2-25,
                false, false);
        HP = 300;
    }

    @Override
    protected void addVertexes() {

    }

    @Override
    public void addCollective() {

    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void specialMove() {

    }
    public void run() {
        while (true){

        }
    }
    private void firstFazeAttack() {
    }
    private void squeeze() {
        squeezing = true;
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        for (int i = 0; i < 2; i++) {
            Hand hand = (Hand) hands.get(i);
            double x = epsilon.getFrame().getX()-hand.getFrame().getWidth()+epsilon.getFrame().getWidth()*i;
            double y = epsilon.getFrame().getY()+20;
            hand.setCenter(new Point(x, y));
            hand.getFrame().setRigid(true);
            hand.setSusceptible(false);
        }
        frame.setX(epsilon.getFrame().getX()+(epsilon.getFrame().getWidth()-frame.getWidth())/2);
        frame.setY(epsilon.getFrame().getY()-frame.getHeight());
        susceptible = true;

    }
    private void projectile() {

    }

}
