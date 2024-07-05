package model.enemies;

import collision.Collidable;
import collision.Impactable;
import controller.GameManager;
import model.EpsilonModel;
import model.game.GameModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Enemy implements Collidable, Movable, Impactable {
    protected RotatablePoint position;
    protected Point center;
    protected ArrayList<RotatablePoint> vertexes;
    protected Point velocity;
    protected Point acceleration;
    protected Point accelerationRate;
    protected double angularVelocity;
    protected double angularAcceleration;
    protected double angularAccelerationRate;
    protected double angle;
    protected int HP;
    protected boolean impact;
    private final String ID;
    private double velocityPower;
    public Enemy(Point center, double velocity) {
        ID = UUID.randomUUID().toString();
        this.center = center;
        this.moveVertexes();
        acceleration = new Point(0,0);
        accelerationRate = new Point(0,0);
        angularVelocity = 0;
        angularAcceleration = 0;
        angularAccelerationRate = 0;
        velocityPower = velocity;
    }

    public int getX() {
        return (int) position.getRotatedX();
    }

    public int getY() {
        return (int) position.getRotatedY();
    }

    public ArrayList<RotatablePoint> getVertexes() {
        return vertexes;
    }

    public Point getCenter() {
        return center;
    }
//    @Override
//    public void move() {
//        direction = getDirection();
//        dx = direction.getDx()*v;
//        dy = direction.getDy()*v;
//        setAngle();
//        setAcceleration();
//        setVelocity();
//        if (impact) {
//            if ((velocity.getX() * accelerationRate.getX() >= 0 || velocity.getY() * accelerationRate.getY() >= 0)) {
//                velocity.setX(0);
//                velocity.setY(0);
//                acceleration = new Point(0, 0);
//                accelerationRate = new Point(0, 0);
//                impact = false;
//            }
//        }
//        angle += angularVelocity / Constants.UPS;
//        angle %= 2*Math.PI;
//        center = new Point(center.getX()+velocity.getX()+dx, center.getY()+velocity.getY()+dy);
//        moveVertexes();
//    }
//    protected void setVelocity() {
//        velocity.setX(velocity.getX()+acceleration.getX()*0.1/ Constants.UPS);
//        velocity.setY(velocity.getY()+acceleration.getY()*0.1/ Constants.UPS);
//    }
//    protected void setAcceleration() {
//        acceleration.setX(acceleration.getX() + accelerationRate.getX() /Constants.UPS);
//        acceleration.setY(acceleration.getY() + accelerationRate.getY() /Constants.UPS);
//    }
//    private void setAngle() {
//        angularAcceleration += angularAccelerationRate/ Constants.UPS;
//        angularVelocity += angularAcceleration/Constants.UPS;
//        angle += angularVelocity;
//        if (angularVelocity*angularAcceleration < 0) {
//            angularAcceleration = 0;
//            angularAccelerationRate = 0;
//            angularVelocity = 0;
//        }
//    }

    public double getAngle() {
        return angle;
    }
    public void moveVertexes() {
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            vertex.setX(center.getX());
            vertex.setY(center.getY());
            vertex.setAngle(vertex.getAngle()+angle);
        }
        position.setX(center.getX());
        position.setY(center.getY());
        position.setAngle(position.getAngle()+angle);
    }

    @Override
    public void setCenter(Point center) {
        this.center = center;
    }

    @Override
    public Point getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    @Override
    public Point getAcceleration() {
        return acceleration;
    }

    @Override
    public void setAcceleration(Point acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public Point getAccelerationRate() {
        return accelerationRate;
    }

    @Override
    public void setAccelerationRate(Point accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    @Override
    public double getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    @Override
    public void setAngularAcceleration(double angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }

    @Override
    public double getAngularAccelerationRate() {
        return angularAccelerationRate;
    }

    @Override
    public void setAngularAccelerationRate(double angularAccelerationRate) {
        this.angularAccelerationRate = angularAccelerationRate;
    }

    @Override
    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isImpact() {
        return impact;
    }

    @Override
    public void setImpact(boolean impact) {
        this.impact = impact;
    }

//    public void impact(Point collisionPoint, Collidable collidable) {
//        double slope1 = collisionPoint.getY()-getCenter().getY()/collisionPoint.getX()-getCenter().getX();
//        double slope2 = collidable.getCenter().getY()- getCenter().getY()/collidable.getCenter().getX()- getCenter().getX();
//        double slope3 = collisionPoint.getY()-collidable.getCenter().getY()/collisionPoint.getX()-collidable.getCenter().getX();
//        if (slope1 < slope2) {
//            this.angularVelocity = -Math.PI/64;
//            this.angularAcceleration = -Math.PI/8;
//            this.angularAccelerationRate = Math.PI/4;
//        }
//        else {
//            this.angularVelocity = Math.PI/64;
//            this.angularAcceleration = Math.PI/8;
//            this.angularAccelerationRate = -Math.PI/4;
//        }
//        if (collidable instanceof Enemy) {
//            Enemy enemy = (Enemy) collidable;
//            if (slope3 < slope2) {
//                enemy.angularVelocity = -Math.PI/64;
//                enemy.angularAcceleration = -Math.PI/8;
//                enemy.angularAccelerationRate = Math.PI/4;
//            }
//            else {
//                enemy.angularVelocity = Math.PI/64;
//                enemy.angularAcceleration = Math.PI/8;
//                enemy.angularAccelerationRate = -Math.PI/4;
//            }
//        }
//        else {
//
//
//        }
//        impactOnOthers(collisionPoint);
//    }
    public Direction getDirection() {
        return new Direction(getCenter(), GameManager.getINSTANCE().getGameModel().getEpsilon().getCenter());
    }


//    public void setImpactAcceleration(Direction direction, double distance) {
//        center.setX(center.getX() - direction.getDx() * distance);
//        center.setY(center.getY() - direction.getDy() * distance);
//        this.moveVertexes();
//        acceleration.setX(-direction.getDx()*distance*1.5);
//        acceleration.setY(-direction.getDy()*distance*1.5);
//        accelerationRate.setX(direction.getDx()*distance*5/3);
//        accelerationRate.setY(direction.getDy()*distance*5/3);
//        impact = true;
//
//    }
//    public void impactOnOthers(Point collisionPoint) {
//        ArrayList<Enemy> enemies = GameManager.getINSTANCE().getGameModel().getEnemies();
//        for (int i = 0; i < enemies.size(); i++) {
//            Enemy enemy = enemies.get(i);
//            if (Math.abs(enemy.getCenter().getX() - collisionPoint.getX()) <= 100 && Math.abs(enemy.getCenter().getY() - collisionPoint.getY()) <= 100) {
//                double x = Math.sqrt(Math.pow(enemy.getCenter().getX() - collisionPoint.getX(),2) + Math.pow(enemy.getCenter().getY() - collisionPoint.getY(),2));
//                Direction direction = new Direction(new Point(enemy.getCenter().getX(), enemy.getCenter().getY()), new Point(collisionPoint.getX(), collisionPoint.getY()));
//                enemy.setImpactAcceleration(direction, 140-x);
//            }
//        }
//        EpsilonModel epsilon = EpsilonModel.getINSTANCE();
//        if (Math.abs(epsilon.getX() - collisionPoint.getX()) <= 100 && Math.abs(epsilon.getY() - collisionPoint.getY()) <= 100) {
//            double x = Math.sqrt(Math.pow(epsilon.getX() - collisionPoint.getX(),2) + Math.pow(epsilon.getY() - collisionPoint.getY(),2));
//            Direction direction = new Direction(new Point(epsilon.getCenter().getX(), epsilon.getCenter().getY()), new Point(collisionPoint.getX(), collisionPoint.getY()));
//            epsilon.setImpactAcceleration(direction, 140-x);
//        }
//    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getID() {
        return ID;
    }
    public boolean died(int x) {
        HP -= x;
        if (getHP() <= 0) {
//            Controller.addEnemyDyingSound();
//            GameModel.getINSTANCE().getEnemies().remove(this);
//            Controller.removeEnemy(this);
            addCollective(GameManager.getINSTANCE().getGameModel());
            return true;
        }
        return false;
    }

    @Override
    public double getVelocityPower() {
        return velocityPower;
    }

    public abstract void addCollective(GameModel gameModel);
}
