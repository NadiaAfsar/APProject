package view.game.enemies;

import controller.GameManager;
import movement.Point;
import view.Rotation;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class TrigorathView extends EnemyView {
    public TrigorathView(int x, int y, String ID) {
        super(x, y, "src/main/resources/triangle.png", ID);
        GameManager.getINSTANCE().getGameView().g.add(this);
    }
    @Override
    public void update(Point center, double angle) {
        setX((int)center.getX() - 13);
        setY((int)center.getY() - 15);
        if (getAngle() != angle) {
            setAngle(angle);
            rotatedImage = Rotation.rotate(image, angle);
        }
    }
}
