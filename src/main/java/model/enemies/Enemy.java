package model.enemies;

import collision.Collidable;
import controller.Constants;
import model.EpsilonModel;
import movement.Direction;
import movement.Movable;
import movement.Point;
import movement.RotatablePoint;

import java.util.ArrayList;

public abstract class Enemy implements Collidable, Movable {
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
    protected double dx;
    protected double dy;
    protected Direction direction;
    private boolean impact;
    public Enemy(Point center) {
        this.center = center;
        setVertexes();
        acceleration = new Point(0,0);
        accelerationRate = new Point(0,0);
        angularVelocity = 0;
        angularAcceleration = 0;
        angularAccelerationRate = 0;
    }
    public abstract void setVertexes();

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
    @Override
    public void move() {
        setVelocity();
        angle += angularVelocity / Constants.UPS;
        angle %= 2*Math.PI;
        direction = getDirection();
        if (direction.getDx() != dx) {
            dx = direction.getDx();
        }
        if (direction.getDy() != dy) {
            dy = direction.getDy();
        }
        center = new Point(center.getX()+velocity.getX()+dx, center.getY()+velocity.getY()+dy);
        moveVertexes();
        System.out.println(acceleration.getX());
        System.out.println(acceleration.getY());
    }
    protected void setVelocity() {
        setAngle();
        acceleration.setX(acceleration.getX() + accelerationRate.getX() /Constants.UPS);
        acceleration.setY(acceleration.getY() + accelerationRate.getY() /Constants.UPS);
        velocity.setX(velocity.getX()+acceleration.getX()*0.1/ Constants.UPS);
        velocity.setY(velocity.getY()+acceleration.getY()*0.1/ Constants.UPS);
        if (impact) {
            if ((velocity.getX() * accelerationRate.getX() >= 0 || velocity.getY() * accelerationRate.getY() >= 0)) {
                velocity.setX(0);
                velocity.setY(0);
                acceleration = new Point(0, 0);
                accelerationRate = new Point(0, 0);
                impact = false;
            }
        }
    }
    private void setAngle() {
        angularAcceleration += angularAccelerationRate/ Constants.UPS;
        angularVelocity += angularAcceleration/Constants.UPS;
        angle += angularVelocity;
        if (angularVelocity*angularAcceleration < 0) {
            angularAcceleration = 0;
            angularAccelerationRate = 0;
            angularVelocity = 0;
        }
    }

    public double getAngle() {
        return angle;
    }
    private void moveVertexes() {
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

    public RotatablePoint getPosition() {
        return position;
    }
    @Override
    public void impact(RotatablePoint collisionPoint, Collidable collidable) {
        double slope1 = collisionPoint.getRotatedY()-getCenter().getY()/collisionPoint.getRotatedX()-getCenter().getX();
        double slope2 = collidable.getCenter().getY()- getCenter().getY()/collidable.getCenter().getX()- getCenter().getX();
        double slope3 = collisionPoint.getRotatedY()-collidable.getCenter().getY()/collisionPoint.getRotatedX()-collidable.getCenter().getX();
        Direction direction = new Direction(new Point(getCenter().getX(), getCenter().getY()), new Point(collidable.getCenter().getX(), collidable.getCenter().getY()));
        if (slope1 < slope2) {
            this.angularAcceleration = -Math.PI/16;
            this.angularAccelerationRate = Math.PI/8;
            setImpactAcceleration(direction, 1);
        }
        else {
            this.angularAcceleration = Math.PI/16;
            this.angularAccelerationRate = -Math.PI/8;
            setImpactAcceleration(direction, -1);
        }
        if (collidable instanceof Enemy) {
            Enemy enemy = (Enemy) collidable;
            if (slope3 < slope2) {
                enemy.angularAcceleration = -Math.PI;
                enemy.angularAccelerationRate = Math.PI;
                enemy.setImpactAcceleration(direction, 1);
            }
            else {
                enemy.angularAcceleration = Math.PI;
                enemy.angularAccelerationRate = -Math.PI/4;
                enemy.setImpactAcceleration(direction, -1);
            }
        }
        else {

        }
    }
    protected Direction getDirection() {
        return new Direction(getCenter(), EpsilonModel.getINSTANCE().getCenter());
    }

    public void setAcceleration(Point acceleration) {
        this.acceleration = acceleration;
    }

    public void setAccelerationRate(Point accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    public void setAngularAcceleration(double angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }

    public void setAngularAccelerationRate(double angularAccelerationRate) {
        this.angularAccelerationRate = angularAccelerationRate;
    }

    public Point getAcceleration() {
        return acceleration;
    }

    public Point getAccelerationRate() {
        return accelerationRate;
    }

    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    public double getAngularAccelerationRate() {
        return angularAccelerationRate;
    }
    private void setImpactAcceleration(Direction direction, int sign) {
        center.setX(center.getX() - direction.getDx()*200);
        center.setY(center.getY() - direction.getDy()*200);
        setVertexes();
        acceleration.setX(-direction.getDx()*300);
        acceleration.setY(-direction.getDy()*300);
        accelerationRate.setX(direction.getDx()*100);
        accelerationRate.setY(direction.getDy()*100);
        impact = true;
    }

}
