package view.game.epsilon;

import controller.GameManager;
import view.game.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Vertex {
    private int x;
    private int y;
    private int width;
    private int height;
    private int radius;
    private BufferedImage image;
    public Vertex(Point center){
        radius = 3;
        x = (int)center.getX()-radius;
        y = (int)center.getY()-radius;
        width = 2*radius;
        height = 2*radius;
        try {
            image = ImageIO.read(new File(GameManager.configs.CIRCLE));
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
