package view.enemies;

import controller.GameManager;
import view.GameView;
import movement.Point;
import view.Rotation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SquarantineView extends EnemyView{
    public SquarantineView(int x, int y) {
        super(x, y, "src/main/resources/square.png");
        GameManager.getINSTANCE().getGameView().add(this);
    }
    @Override
    public void update(Point center, double angle) {
        setX((int)center.getX() - 13);
        setY((int)center.getY() - 13);
        if (getAngle() != angle) {
            setAngle(angle);
            BufferedImage rotatedImage = Rotation.rotate(image, angle);
            setIcon(new ImageIcon(rotatedImage));
            setBounds(getX(), getY(), rotatedImage.getWidth(), rotatedImage.getHeight());
        }
    }
}
