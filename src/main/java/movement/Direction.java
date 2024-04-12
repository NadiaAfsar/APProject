package movement;

import java.awt.geom.Point2D;

public class Direction {
    private double dx;
    private double dy;
    public Direction(Point2D point1, Point2D point2) {
        dx = point2.getX() - point1.getX();
        dy = point2.getY() - point1.getY();
        int xSign = (int) (dx/Math.abs(dx));
        int ySign = (int) (dy/Math.abs(dy));
        if (Math.abs(dx) > Math.abs(dy)) {
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
}
