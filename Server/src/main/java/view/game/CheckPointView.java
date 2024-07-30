package view.game;


import controller.GameManager;
import model.interfaces.movement.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class CheckPointView {
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage image;
    public CheckPointView(Point point){
        x = (int)point.getX();
        y = (int)point.getY();
        width = 72;
        height = 100;
        try {
            image = ImageIO.read(new File(GameManager.configs.PORTAL));
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

    public BufferedImage getImage() {
        return image;
    }
}
