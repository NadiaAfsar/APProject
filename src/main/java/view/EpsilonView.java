package view;

import controller.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EpsilonView extends JLabel{
    private int x;
    private int y;
    private int radius;
    public static EpsilonView INSTANCE;
    public EpsilonView() throws IOException {
        x = Constants.FRAME_SIZE.width/2;
        y = Constants.FRAME_SIZE.height/2;
        radius = Constants.EPSILON_RADIUS;
        setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/epsilon.png"))));
        setBounds(x,y,2*radius,2*radius);
    }
    public void update(int x, int y) {
        this.x  = x;
        this.y = y;
        setBounds(x,y,2*radius,2*radius);
    }

    public static EpsilonView getINSTANCE() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new EpsilonView();
        }
        return INSTANCE;
    }
}
