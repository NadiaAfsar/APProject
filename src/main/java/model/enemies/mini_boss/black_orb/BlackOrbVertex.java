package model.enemies.mini_boss.black_orb;

import model.Frame;
import movement.Point;

public class BlackOrbVertex {
    private Frame frame;
    private Point center;
    private double width;
    private double height;

    public BlackOrbVertex(Point center) {
        this.center = center;
        frame = new Frame(width+20, height+20, center.getX()-width/2-10, center.getY()-height/2-10,
                true, false);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Point getCenter() {
        return center;
    }
}
