package model.game.enemies.smiley;

import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;

public class LeftHand extends Hand {
    public LeftHand(Point center, double velocity) {
        super(center, velocity);
    }

    @Override
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1.68*Math.PI, 0.07*Math.PI, 0.11*Math.PI, 0.2*Math.PI, 0.38*Math.PI, 0.5*Math.PI,
        0.63*Math.PI, 0.73*Math.PI, 0.84*Math.PI, 1.11*Math.PI, 1.14*Math.PI, 1.17*Math.PI, 1.08*Math.PI, 1.22*Math.PI,
        1.32*Math.PI, 1.37*Math.PI, 1.42*Math.PI, 1.48*Math.PI, 1.56*Math.PI, 1.63*Math.PI, 1.71*Math.PI, 1.59*Math.PI,
        1.62*Math.PI, 1.67*Math.PI};
        double[] radius = new double[]{9.43*width/55, 23.53*width/55, 27.8*width/55, 32.8*width/55, 40.85*width/55,
                50.77*width/55, 41.23*width/55, 36.87*width/55, 28.65*width/55, 26.57*width/55, 24.6*width/55, 21.09*width/55,
                14.5*width/55, 18.43*width/55, 17.5*width/55, 15.23*width/55, 12.36*width/55, 15.03*width/55, 22.36*width/55,
                17.46*width/55, 16.4*width/55, 41.48*width/55, 45.8*width/55, 43.9*width/55};
        for (int i = 0; i < 24; i++) {
            RotatablePoint vertex = new RotatablePoint(center.getX(), center.getY(), angles[i]+angle, radius[i]);
            vertexes.add(vertex);
        }
        position = new RotatablePoint(center.getX(), center.getY(), 1.32*Math.PI, 50.77*width/55);
    }


    @Override
    public Direction getDirection() {
        return null;
    }
}
