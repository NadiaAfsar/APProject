package model.enemies;

import model.GameModel;

import java.awt.*;
import java.util.ArrayList;

public class SquarantineModel extends Enemy {
    public SquarantineModel(int x, int y) {
        super(x,y);
        setCenter(x,y);
        GameModel.getINSTANCE().getEnemies().add(this);
        velocity = 1;
    }
    @Override
    public void setVertexes() {
        vertexes = new ArrayList<>();
        int[] x = new int[]{0,26,26,0};
        int[] y = new int[]{0,0,26,26};
        for (int i = 0; i < 4; i++) {
            Point vertex = new Point((int)this.x + x[i], (int)this.y + y[i]);
            vertexes.add(vertex);
        }
    }
    @Override
    protected void setCenter(double x, double y) {
        center = new Point((int)x+13, (int)y+15);
    }

    @Override
    protected void setVelocity() {
        int x = (int)(Math.random()*200);
        if (x == 5 && acceleration == 0) {
            acceleration = 3;
            accelerationRate = -1;
        }
        acceleration += accelerationRate/100;
        velocity += acceleration/100;
        if (velocity <= 1 && (acceleration != 0 || accelerationRate != 0)) {
            acceleration = 0;
            accelerationRate = 0;
        }
    }

}