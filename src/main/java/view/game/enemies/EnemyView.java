package view.game.enemies;

import model.interfaces.movement.Point;
import view.Rotation;

import javax.imageio.ImageIO;
import java.awt.*;
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
    private double initialWidth;
    private double initialHeight;
    protected BufferedImage rotatedImage;
    private String ID;
    private double imageWidth;
    private double imageHeight;


    public EnemyView(int x, int y, int width, int height, String path, String ID) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initialWidth = width;
        initialHeight = height;
        this.ID = ID;
        try {
            image = ImageIO.read(new File(path));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        rotatedImage = image;
    }
    public void update(Point center, double angle) {
        setX((int)center.getX() - width/2);
        setY((int)center.getY() - height/2);
        if (getAngle() != angle) {
            setAngle(angle);
            rotatedImage = Rotation.rotate(image, angle);
            width = (int)(rotatedImage.getWidth()/imageWidth*initialWidth);
            height = (int)(rotatedImage.getHeight()/imageHeight*initialHeight);
        }
    }

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
