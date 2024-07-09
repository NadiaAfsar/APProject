package view.game.enemies;

import movement.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class EnemyView extends JLabel {
    private int x;
    private int y;
    private double angle;
    protected BufferedImage image;


    public EnemyView(int x, int y, String path) {
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(new File(path));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        setIcon(new ImageIcon(image));
        setBounds(x,y,image.getWidth(), image.getHeight());
    }
    public abstract void update(Point center, double angle);

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
