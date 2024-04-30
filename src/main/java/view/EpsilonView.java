package view;

import controller.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EpsilonView extends JLabel{
    private int x;
    private int y;
    private int radius;
    public static EpsilonView INSTANCE;
    private ArrayList<JLabel> vertexes;
    public EpsilonView() {
        x = Constants.FRAME_SIZE.width/2;
        y = Constants.FRAME_SIZE.height/2;
        radius = Constants.EPSILON_RADIUS;
        try {
            setIcon(new ImageIcon(ImageIO.read(new File("src/main/resources/epsilon.png"))));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        setBounds(x,y,2*radius,2*radius);
        vertexes = new ArrayList<>();
    }
    public void update(int x, int y) {
        this.x  = x;
        this.y = y;
        setBounds(x,y,2*radius,2*radius);
    }

    public static EpsilonView getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new EpsilonView();
        }
        return INSTANCE;
    }
    public void addVertex(int x, int y) {
        JLabel vertex = new JLabel("●");
    }
}
