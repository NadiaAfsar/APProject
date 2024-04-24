package movement;

import controller.Constants;

public class RotatablePoint {
    private double x;
    private double y;
    private double rotatedX;
    private double rotatedY;
    private double angle;
    private double radius;

    public RotatablePoint(double x, double y, double angle, double radius) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.radius = radius;
        setRotatedX();
        setRotatedY();
    }

    public RotatablePoint(double rotatedX, double rotatedY) {
        this.rotatedX = rotatedX;
        this.rotatedY = rotatedY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        setRotatedX();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        setRotatedY();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
    public double getRotatedX() {
        return rotatedX;
    }
    private void setRotatedX() {
        rotatedX = x+Math.cos(angle)*radius;
    }
    private void setRotatedY() {
        rotatedY = y + Math.sin(angle)*radius;
    }
    public double getRotatedY() {
        return rotatedY;
    }

    public void setRotatedX(double rotatedX) {
        this.rotatedX = rotatedX;
    }

    public void setRotatedY(double rotatedY) {
        this.rotatedY = rotatedY;
    }
}
