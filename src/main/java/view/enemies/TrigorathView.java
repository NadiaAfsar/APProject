package view.enemies;

import view.GameView;
import movement.Point;
import view.Rotation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TrigorathView extends EnemyView {
    public TrigorathView(int x, int y) {
        super(x, y, "src/main/resources/triangle.png");
        GameView.getINSTANCE().add(this);
        GameView.getINSTANCE().getEnemies().add(this);
    }
    @Override
    public void update(Point center, double angle) {
        setX((int)center.getX() - 13);
        setY((int)center.getY() - 15);
        if (getAngle() != angle) {
            setAngle(angle);
            BufferedImage rotatedImage = Rotation.rotate(image, angle);
            setIcon(new ImageIcon(rotatedImage));
            setBounds(getX(), getY(), rotatedImage.getWidth(), rotatedImage.getHeight());
        }
    }
}
