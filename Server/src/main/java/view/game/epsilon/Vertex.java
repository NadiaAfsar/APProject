package view.game.epsilon;

import application.MyApplication;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Vertex {
    private int x;
    private int y;
    private int width;
    private int height;
    private int radius;
    private BufferedImage image;
    public Vertex(Point center, int radius){
        this.radius = radius;
        x = (int)center.getX()-radius;
        y = (int)center.getY()-radius;
        width = 2*radius;
        height = 2*radius;
        try {
            image = ImageIO.read(new File(MyApplication.configs.CIRCLE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public int getRadius() {
        return radius;
    }

    public BufferedImage getImage() {
        return image;
    }
    public void setCenter(Point center){
        x = (int)center.getX()-radius;
        y = (int)center.getY()-radius;
    }

}
