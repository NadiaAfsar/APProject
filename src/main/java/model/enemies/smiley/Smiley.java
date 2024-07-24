package model.enemies.smiley;

import controller.Controller;
import controller.GameManager;
import controller.audio.AudioController;
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
    private long lastAttack;
    private long lasShot;
    private boolean bulletShot;
    private boolean toDisappear;
    public Smiley(Point center, double velocity) {
        super(center, velocity);
        logger = Logger.getLogger(Smiley.class.getName());
        phase = 1;
        width = 2 * GameManager.configs.SMILEY_RADIUS;
        height = 2 * GameManager.configs.SMILEY_RADIUS;
        frame = new Frame(width+80, height+80, center.getX()-width/2-40, center.getY()-height/2-40,
                false, false, width+80, height+80);
        HP = 300;
        velocityPower /= 2;
        susceptible = true;
        smileyAoEAttacks = new ArrayList<>();
        addVertexes();
        addHands();
        frame.getEnemies().add(this);
        Controller.addEnemyView(this);
        GameManager.getINSTANCE().getGameModel().getFrames().add(frame);
        GameManager.getINSTANCE().getGameModel().setSmiley(this);
        start();
    }

    @Override
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        position = new RotatablePoint(center.getX(), center.getY(), 1.25*Math.PI, width*Math.sqrt(2));
    }
    private void addHands(){
        double x = frame.getX()+frame.getWidth()+GameManager.configs.HAND_WIDTH/2+20;
        double y = frame.getY()+frame.getHeight()/2;
        RightHand rightHand = new RightHand(new Point(x, y),velocityPower);
        x = frame.getX()-GameManager.configs.HAND_WIDTH/2-20;
        LeftHand leftHand = new LeftHand(new Point(x, y), velocityPower);
        hands = new ArrayList<Hand>(){{add(rightHand);add(leftHand);}};
    }

    @Override
    public void addCollective() {

    }

    @Override
    public Direction getDirection() {
        if (squeezing){
            Direction direction = new Direction();
            direction.setDx(0);
            direction.setDy(0);
            return direction;
        }
        Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
        Direction direction = new Direction(center, epsilonCenter);
        if (projectile) {
            Direction direction1 = new Direction();
            direction1.setDx(direction.getDy()*10);
            direction1.setDy(-direction.getDx()*10);
            return direction1;
        }
        return direction;
    }

    @Override
    public void specialMove() {
        if (!squeezing){
            for (int i = 0; i < hands.size(); i++){
                Hand hand = hands.get(i);
                int j = -1;
                if (hand instanceof RightHand){
                    j = 1;
                }
                double x = frame.getX()+frame.getWidth()*((j+1)/2)+(GameManager.configs.HAND_WIDTH/2+20)*j;
                double y = frame.getY()+frame.getHeight()/2;
                hand.setCenter(new Point(x, y));
            }
            if (projectile){
                checkProjectile();
            }
            if (powerPunch || quake || slap){
                fist.move();
            }
            if (rapidFire){
                shoot();
            }
        }
        else {
            squeezeSetPosition();
        }
        if (bulletShot) {
            if ((getVelocity().getX() * getAccelerationRate().getX() > 0 || getVelocity().getY() * getAccelerationRate().getY() > 0)) {
                setVelocity(new Point(0, 0));
                setAcceleration(new Point(0, 0));
                setAccelerationRate(new Point(0, 0));
                bulletShot = false;
            }
        }
    }
    private void checkProjectile(){
        Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
        double distance = Calculations.getDistance(epsilonCenter.getX(), epsilonCenter.getY(), center.getX(), center.getY());
        if (Math.abs(distance-300) > 50 || hands.size()==0) {
            projectile = false;
            susceptible = true;
            setHandsSusceptible(false);
        }
        else {
            for (int i = 0; i < hands.size(); i++){
                hands.get(i).shoot();
            }
        }
    }
    private void squeezeSetPosition(){
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        for (int i = 0; i < hands.size(); i++) {
            Hand hand = hands.get(i);
            int j = -1;
            if (hand instanceof RightHand){
                j = 1;
            }
            double x = epsilon.getFrame().getX()+j*(hand.getFrame().getWidth()/2)+epsilon.getFrame().getWidth()*((j+1)/2);
            double y = epsilon.getFrame().getY()+20;
            hand.setCenter(new Point(x, y));
            hand.getFrame().setRigid(true);
            hand.setSusceptible(false);
        }
        setCenter((new Point(epsilon.getFrame().getX()+epsilon.getFrame().getWidth()/2, center.getY())));
        if (Math.abs(frame.getY()+frame.getHeight()-epsilon.getFrame().getY()) > 50){
            squeezing = false;
        }
    }
    public void run() {
        while (!died) {
            if (!GameManager.getINSTANCE().isHypnos() && Controller.gameRunning) {
                if (toDisappear) {
                    shrinkage();
                } else {
                    move();
                    if (phase == 1 && !squeezing) {
                        firstPhaseAttack();
                    } else if (phase == 2) {
                        secondPhaseAttack();
                    }
                    checkAoEs();
                }
            }
            try {
                sleep((long) Configs.MODEL_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        interrupt();
    }
    private void checkAoEs() {
        for (int i = 0; i < smileyAoEAttacks.size(); i++){
            smileyAoEAttacks.get(i).update();
        }
    }
    private void firstPhaseAttack() {
        if (hands.size() != 0) {
            Frame epsilonFrame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
            if (Math.abs(frame.getY() + frame.getHeight() - epsilonFrame.getY()) < 30 && (Calculations.isInDomain(frame.getX(),
                    epsilonFrame.getX(), epsilonFrame.getX() + epsilonFrame.getWidth()) ||
                    Calculations.isInDomain(frame.getX() + frame.getWidth(), epsilonFrame.getX(),
                            epsilonFrame.getX() + epsilonFrame.getWidth()))) {
                logger.debug("squeeze");
                squeeze();
            } else {
                Point epsilonCenter = GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter();
                double distance = Calculations.getDistance(epsilonCenter.getX(), epsilonCenter.getY(), center.getX(), center.getY());
                if (Math.abs(distance-300) < 10) {
                    logger.debug("projectile");
                    projectile();
                }
            }
        }
    }
    private void secondPhaseAttack() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttack >= 5000) {
            int x = (int)(Math.random()*7);
            if (x == 0){
                vomit();
            }
            else if (x == 1){
                powerPunch();
            }
            else if (x == 2){
                quake();
            }
            else if (x == 3){
                rapidFire();
            }
            else if (x == 4){
                slap();
            }
            lastAttack = currentTime;
        }
    }
    private void squeeze() {
        squeezing = true;
        projectile = false;
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        for (int i = 0; i < hands.size(); i++) {
            Hand hand = hands.get(i);
            int j = -1;
            if (hand instanceof RightHand){
                j = 1;
            }
            double x = epsilon.getFrame().getX()+j*(hand.getFrame().getWidth()/2)+epsilon.getFrame().getWidth()*((j+1)/2);
            double y = epsilon.getFrame().getY()+20;
            hand.setCenter(new Point(x, y));
            hand.getFrame().setRigid(true);
            hand.setSusceptible(false);
        }
        setCenter((new Point(epsilon.getFrame().getX()+epsilon.getFrame().getWidth()/2,
                epsilon.getFrame().getY()-frame.getHeight()/2)));
        susceptible = true;

    }
    public void setCenter(Point center){
        if (center.getX()-frame.getWidth()/2 >= -50 && center.getX()+frame.getWidth()/2 <= Configs.FRAME_SIZE.width+50){
            frame.setX(center.getX()-width/2-40);
        }
        if (center.getY()-frame.getHeight()/2 >= -50 && center.getY()+frame.getHeight()/2 <= Configs.FRAME_SIZE.height+50){
            frame.setY(center.getY()-height/2-40);
        }
        this.center.setX(frame.getX()+frame.getWidth()/2);
        this.center.setY(frame.getY()+frame.getHeight()/2);
        moveVertexes();
    }
    public void bulletShot(BulletModel bulletModel){
        if (!squeezing) {
            Direction direction = bulletModel.getDirection();
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (direction.getDx()*(center.getX()-epsilon.getCenter().getX()) > 0 && direction.getDy()*(center.getY()-epsilon.getCenter().getY()) > 0) {
                bulletShot = true;
                velocity = new Point(-direction.getDy() * 10, direction.getDx() * 10);
                acceleration = new Point(-direction.getDy() * 10, direction.getDx() * 10);
                accelerationRate = new Point(-acceleration.getX() * 2, -acceleration.getY() * 2);
            }
        }
    }
    private void projectile() {
        projectile = true;
        squeezing = false;
        susceptible = false;
        setHandsSusceptible(true);
    }
    private void startPhase2(){
        fist = new Fist(new Point(800, 300), velocityPower, this);
        Controller.smileyPhase2(this);
        phase = 2;
    }
    private void vomit() {
        susceptible = true;
        setHandsSusceptible(false);
        Frame frame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
        for (int i = 0; i < 5; i++){
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
        double[] location = getLocation();
        logger.debug(location[0]);
        logger.debug(location[1]);
        fist.setDirection(location[0], location[1]);
        fist.setPowerPunch(true);
        powerPunch = true;
    }
    private double[] getLocation(){
        int x = (int)(Math.random()*4);
        if (x == 0){
            return new double[]{0, 0.5};
        }
        else if (x == 1){
            return new double[]{0.5, 0};
        }
        else if (x == 2){
            return new double[]{1, 0.5};
        }
        return new double[]{0.5, 1};
    }
    private void quake() {
        fist.setQuake(true);
        quake = true;
        logger.debug("quake started");
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
        long currentTime = System.currentTimeMillis();
        if (currentTime - lasShot >= 500) {
            double x = Math.random() * Configs.FRAME_SIZE.width;
            double y = Math.random() * Configs.FRAME_SIZE.height;
            BulletModel bulletModel = new BulletModel(center, new Point(x, y), width / 2, 3, false, frame);
            GameManager.getINSTANCE().getGameModel().getEnemiesBullets().add(bulletModel);
            lasShot = currentTime;
            if (System.currentTimeMillis() - rapidFireActivated >= 30000) {
                rapidFire = false;
            }
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
    public void decreaseHP(int x){
        if (susceptible){
            super.decreaseHP(x);
        }
        if (HP < 200 && phase == 1){
            startPhase2();
        }
        logger.debug("HP:"+HP);
    }
    public void die() {
        AudioController.addEnemyDyingSound();
        for (int i = 0; i < hands.size(); i++){
            GameManager.getINSTANCE().getGameModel().getDiedEnemies().add(hands.get(i));
            Controller.removeEnemyView(hands.get(i));
        }
        GameManager.getINSTANCE().getGameModel().getDiedEnemies().add(fist);
        Controller.removeEnemyView(fist);
        Controller.smileyDied(this);
        width = GameManager.configs.DEAD_WIDTH;
        height = GameManager.configs.DEAD_HEIGHT;
        toDisappear = true;
    }
    public void shrinkage(){
        width -= 0.5;
        height -= 0.5;
        if (width <= 4){
            GameManager.getINSTANCE().getGameModel().getDiedEnemies().add(this);
            Controller.removeEnemyView(this);
            died = true;
        }
    }
}
