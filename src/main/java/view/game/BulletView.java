package view.game;

import movement.Direction;
import view.Rotation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BulletView {
    private int x;
    private int y;
    private int width;
    private int height;
    private Direction direction;
    private BufferedImage image;
    private String ID;
    public BulletView(int x, int y, Direction direction, String ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.direction = direction;
        setIcon();
    }
    private double getAngle() {
        double angle = Math.atan(direction.getDy()/direction.getDx());
        if (direction.getDy() < 0 && direction.getDx() < 0) {
            angle += Math.PI;
        }
        return angle;
    }
    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }
    private void setIcon() {
        try {
            image = ImageIO.read(new File("src/main/resources/bullet.png"));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        BufferedImage rotatedImage = Rotation.rotate(image, getAngle());
        image = rotatedImage;
        width = rotatedImage.getWidth();
        height = rotatedImage.getHeight();
    }

    public String getID() {
        return ID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return image;
    }
}
