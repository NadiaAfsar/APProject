package view.menu;

import controller.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background extends JPanel {
    private BufferedImage backGround;
    private int xSize;
    private int ySize;

    public Background(String path) {
        this.xSize = Constants.FRAME_SIZE.width;
        this.ySize = Constants.FRAME_SIZE.height;
        try {
            backGround = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setBounds(0, 0, xSize, ySize);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backGround != null) {
            g.drawImage(backGround, 0, 0, xSize, ySize, null);
        }
    }
}

