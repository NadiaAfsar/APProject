package model.enemies.smiley;

import controller.Controller;
import controller.GameManager;
import controller.save.Configs;
import model.BulletModel;
import model.Calculations;
import model.EpsilonModel;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Movable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Smiley extends Enemy implements Movable {
    private int phase;
    private ArrayList<Hand> hands;
    private boolean squeezing;
    private boolean susceptible;
    private boolean projectile;
    private ArrayList<SmileyAoEAttack> smileyAoEAttacks;
    private Fist fist;
    private boolean powerPunch;
    private boolean quake;
    private boolean rapidFire;
    private long rapidFireActivated;
    private boolean slap;
    public Smiley(Point center, double velocity) {
        super(center, velocity);
        logger = Logger.getLogger(Smiley.class.getName());
        phase = 1;
        width = 2 * GameManager.configs.SMILEY_RADIUS;
        height = 2 * GameManager.configs.SMILEY_RADIUS;
        frame = new Frame(width+50, height+50, center.getX()-width/2-25, center.getY()-height/2-25,
                false, false);
        HP = 300;
        smileyAoEAttacks = new ArrayList<>();
        addVertexes();
        addHands();
        frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        GameManager.getINSTANCE().getGameModel().getFrames().add(frame);
    }

    @Override
    protected void addVertexes() {
        position = new RotatablePoint(center.getX(), center.getY(), 1.25*Math.PI, width*Math.sqrt(2));
    }
    private void addHands(){
        double x = frame.getX()+frame.getWidth()+GameManager.configs.HAND_WIDTH/2+10;
        double y = frame.getY()+frame.getHeight()/2;
        RightHand rightHand = new RightHand(new Point(x, y),velocityPower);
        x = frame.getX()-GameManager.configs.HAND_WIDTH/2-10;
        LeftHand leftHand = new LeftHand(new Point(x, y), velocityPower);
        hands = new ArrayList<Hand>(){{add(rightHand);add(leftHand);}};
    }

    @Override
    public void addCollective() {

    }

    @Override
    public Direction getDirection() {
        Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
        Direction direction = new Direction(center, epsilonCenter);
        if (projectile) {
            Direction direction1 = new Direction();
            direction1.setDx(direction.getDy()*3);
            direction1.setDy(-direction.getDx()*3);
            return direction1;
        }
        return direction;
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
        for (int i = 0; i < hands.size(); i++) {
            Hand hand = hands.get(i);
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
        projectile = true;
        susceptible = false;
        setHandsSusceptible(true);
    }
    private void vomit() {
        susceptible = true;
        setHandsSusceptible(false);
        Frame frame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
        for (int i = 0; i < 3; i++){
            int x = (int)(Math.random()*frame.getWidth()+frame.getX());
            int y = (int)(Math.random()*frame.getHeight()+frame.getY());
            smileyAoEAttacks.add(new SmileyAoEAttack(this, new Point(x, y)));
        }
    }

    public ArrayList<SmileyAoEAttack> getAoEAttacks() {
        return smileyAoEAttacks;
    }
    private void powerPunch(){
        susceptible = true;
        setHandsSusceptible(false);
        double[] location = getNearestLocation();
        fist.setDirection(location[0], location[1]);
        fist.setPowerPunch(true);
        powerPunch = true;
    }
    private double[] getNearestLocation(){
        Frame frame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
        double d1 = Calculations.getDistance(fist.getX(), fist.getY(), frame.getX(), frame.getY()+frame.getHeight()/2);
        double d2 = Calculations.getDistance(fist.getX(), fist.getY(), frame.getX()+frame.getWidth()/2, frame.getY());
        double d3 = Calculations.getDistance(fist.getX(), fist.getY(), frame.getX()+frame.getWidth(), frame.getY()+frame.getHeight()/2);
        double d4 = Calculations.getDistance(fist.getX(), fist.getY(), frame.getX()+frame.getWidth()/2, frame.getY()+frame.getHeight());
        double min = Math.min(d1, Math.min(d2, Math.min(d3, d4)));
        if (min == d1){
            return new double[]{0, 0.5};
        }
        else if (min == d2){
            return new double[]{0.5, 0};
        }
        else if (min == d3){
            return new double[]{1, 0.5};
        }
        return new double[]{0.5, 1};
    }
    private void quake() {
        fist.setQuake(true);
        quake = true;
    }

    public void setQuake(boolean quake) {
        this.quake = quake;
    }

    public void setPowerPunch(boolean powerPunch) {
        this.powerPunch = powerPunch;
    }
    private void rapidFire(){
        rapidFire = true;
        rapidFireActivated = System.currentTimeMillis();
        susceptible = true;
        setHandsSusceptible(false);
    }
    private void shoot(){
        double x = Math.random()* Configs.FRAME_SIZE.width;
        double y = Math.random()*Configs.FRAME_SIZE.height;
        BulletModel bulletModel = new BulletModel(center, new Point(x, y),width/2,3,false, frame);
        GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
        if (System.currentTimeMillis()-rapidFireActivated >= 30000){
            rapidFire = false;
        }
    }
    private void setHandsSusceptible(boolean b){
        for (int i = 0; i < hands.size(); i++){
            hands.get(i).setSusceptible(b);
        }
    }
    private void slap() {
        slap = true;
        fist.activateSlap();
    }

    public void setSlap(boolean slap) {
        this.slap = slap;
    }
}
