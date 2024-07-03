package movement;

import java.awt.geom.Point2D;

public class Direction {
    private double dx;
    private double dy;
    private int xSign;
    private int ySign;
    public Direction(Point point1, Point point2) {
        dx = point2.getX() - point1.getX();
        dy = point2.getY() - point1.getY();
        xSign = 1;
        ySign = 1;
        if (dx != 0) {
            xSign = (int) (dx / Math.abs(dx));
        }
        if (dy != 0) {
            ySign = (int) (dy / Math.abs(dy));
        }
        if (dx == 0) {
            dy = 0.1*ySign;
        }
        if (dy == 0) {
            dx = 0.1*xSign;
        }
        else if (Math.abs(dx) > Math.abs(dy)) {
            dy = 0.1*Math.abs(dy/dx)*ySign;
            dx = 0.1*xSign;
        }
        else if (Math.abs(dx) < Math.abs(dy)) {
            dx = 0.1*Math.abs(dx/dy)*xSign;
            dy = 0.1*ySign;
        }
        else {
            dx = 0.1*xSign;
            dy = 0.1*ySign;
        }
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public int getxSign() {
        return xSign;
    }

    public int getySign() {
        return ySign;
    }
}
