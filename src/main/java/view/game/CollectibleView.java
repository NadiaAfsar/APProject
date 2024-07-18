package view.game;

import controller.GameManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CollectibleView {
    private String ID;
    private BufferedImage image;
    private int x;
    private int y;
    private int width;
    private int height;
    public CollectibleView(int x, int y, String ID) {
        width = 10;
        height = 10;
        this.x = x-width/2;
        this.y = y-height/2;
        this.ID = ID;
        try {
            image = ImageIO.read(new File(GameManager.configs.COLLECTIVE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getImage() {
        return image;
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

    public String getID() {
        return ID;
    }

}
