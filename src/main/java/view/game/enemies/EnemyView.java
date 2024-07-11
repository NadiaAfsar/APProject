package view.game.enemies;

import movement.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class EnemyView {
    private int x;
    private int y;
    private double angle;
    protected BufferedImage image;
    private int width;
    private int height;
    protected BufferedImage rotatedImage;
    private String ID;


    public EnemyView(int x, int y, String path, String ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        try {
            image = ImageIO.read(new File(path));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public abstract void update(Point center, double angle);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getRotatedImage() {
        return rotatedImage;
    }

    public void setRotatedImage(BufferedImage rotatedImage) {
        this.rotatedImage = rotatedImage;
    }

    public String getID() {
        return ID;
    }
}
