package movement;

import controller.Constants;


public interface Movable {
    default void move() {
        calculateAccelerationRate();
        setAcceleration(calculateAcceleration());
        setVelocity(calculateVelocity());
        specialMove();
        Direction direction = getDirection();
        double dx = direction.getDx()*getVelocityPower();
        double dy = direction.getDy()*getVelocityPower();
        setCenter(new Point(getCenter().getX()+getVelocity().getX()+dx, getCenter().getY()+getVelocity().getY()+dy));
        setAngularAcceleration(calculateAngularAcceleration());
        setAngularVelocity(calculateAngularVelocity());
        setAngle(calculateAngle());
        calculateAngularAccelerationRate();
        moveVertexes();
    };
    default Point calculateAcceleration() {
        return new Point(getAcceleration().getX()+ getAccelerationRate().getX() / Constants.UPS,
                getAcceleration().getY() + getAccelerationRate().getY() / Constants.UPS);
    }
    default Point calculateVelocity() {
        return new Point(getVelocity().getX()+getAcceleration().getX()*0.1/ Constants.UPS,
        getVelocity().getY()+getAcceleration().getY()*0.1/ Constants.UPS);
    }
    default void calculateAccelerationRate() {
        if (isImpact()) {
            if ((getVelocity().getX() * getAccelerationRate().getX() > 0 || getVelocity().getY() * getAccelerationRate().getY() > 0)) {
                setVelocity(new Point(0, 0));
                setAcceleration(new Point(0, 0));
                setAccelerationRate(new Point(0, 0));
                setImpact(false);
            }
        }
    }
    boolean isImpact();
    void setCenter(Point center);
    void setImpact(boolean impact);
    void moveVertexes();
    Point getAcceleration();
    void setAcceleration(Point acceleration);
    Point getAccelerationRate();
    void setAccelerationRate(Point accelerationRate);
    void setVelocity(Point velocity);
    Point getVelocity();
    double getVelocityPower();
    Direction getDirection();
    Point getCenter();
    double getAngle();
    double getAngularVelocity();
    double getAngularAcceleration();
    void setAngle(double angle);
    void setAngularVelocity(double velocity);
    void setAngularAcceleration(double acceleration);
    default double calculateAngularVelocity() {
        return getAngularVelocity() + getAngularAcceleration() / Constants.UPS;
    }
    default double calculateAngularAcceleration() {
        return getAngularAcceleration() + getAngularAccelerationRate() / Constants.UPS;
    }
    void setAngularAccelerationRate(double rate);
    double getAngularAccelerationRate();
    default double calculateAngle() {
        double angle = getAngle() + getAngularVelocity();
        return angle % (2*Math.PI);
    }
    default void calculateAngularAccelerationRate() {
        if (getAngularVelocity()*getAngularAcceleration() < 0) {
            setAngularAcceleration(0);
            setAngularAccelerationRate(0);
            setAngularVelocity(0);
        }
    }
    void specialMove();


}
