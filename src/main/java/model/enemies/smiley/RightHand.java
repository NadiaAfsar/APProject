package model.enemies.smiley;

import controller.GameManager;
import model.enemies.Enemy;
import model.frame.Frame;
import model.interfaces.movement.Direction;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;

public class RightHand extends Hand {
    public RightHand(Point center, double velocity) {
        super(center, velocity);
    }

    @Override
    protected void addVertexes() {
        vertexes = new ArrayList<>();
        double[] angles = new double[]{1.33*Math.PI, 1.38*Math.PI, 1.41*Math.PI, 1.29*Math.PI, 1.37*Math.PI, 1.44*Math.PI,
        1.52*Math.PI, 1.58*Math.PI, 1.63*Math.PI, 1.68*Math.PI, 1.78*Math.PI, 1.92*Math.PI, 1.83*Math.PI, 1.86*Math.PI,
        1.89*Math.PI, 0.16*Math.PI, 0.27*Math.PI, 0.37*Math.PI, 0.5*Math.PI, 0.62*Math.PI, 0.8*Math.PI, 0.89*Math.PI,
        0.93*Math.PI, 1.32*Math.PI};
        double[] radius = new double[]{43.9*width/55, 45.8*width/55, 41.48*width/55, 16.4*width/55, 17.46*width/55,
        22.36*width/55, 15.03*width/55, 12.36*width/55, 15.23*width/55, 17.5*width/55, 18.43*width/55, 14.5*width/55,
        21.09*width/55, 24.6*width/55, 26.57*width/55, 28.65*width/55, 36.87*width/55, 41.23*width/55, 50.77*width/55,
        40.85*width/55, 32.8*width/55, 27.8*width/55, 23.53*width/55, 9.43*width/55};
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
