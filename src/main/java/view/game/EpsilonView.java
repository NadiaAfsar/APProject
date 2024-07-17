package view.game;

import controller.GameManager;
import controller.save.Configs;
import model.interfaces.movement.RotatablePoint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EpsilonView{
    private int x;
    private int y;
    private int radius;
    private ArrayList<JLabel> vertexes;
    private BufferedImage image;
    private GamePanel panel;
    public EpsilonView() {
        x = Configs.FRAME_SIZE.width/2;
        y = Configs.FRAME_SIZE.height/2;
        radius = 12;
        try {
            image = ImageIO.read(new File(GameManager.configs.EPSILON));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        vertexes = new ArrayList<>();
    }
    public void update(int x, int y) {
        this.x  = x;
        this.y = y;
    }

    public void addVertex(int x, int y) {
        JLabel vertex = new JLabel("●");
        vertex.setFont(new Font("Serif", Font.PLAIN, 10));
        vertex.setForeground(Color.WHITE);
        vertex.setBounds(x-5,y-50,100,100);
        vertexes.add(vertex);
        panel.add(vertex);
    }
    public void removeVertexes() {
        for (int i = 0; i < vertexes.size(); i++) {
            panel.remove(vertexes.get(i));
        }
        vertexes = new ArrayList<>();
    }
    public void updateVertexes(ArrayList<RotatablePoint> vertexes) {
        for (int i = 0; i < vertexes.size(); i++) {
            RotatablePoint vertex = vertexes.get(i);
            JLabel vertexJLabel = this.vertexes.get(i);
            vertexJLabel.setBounds((int)vertex.getRotatedX()-5, (int)vertex.getRotatedY()-50, 100, 100);
        }
    }
    public void increaseSize(int x, int y, int radius) {
        Image image = this.image.getScaledInstance(2*radius,2*radius, Image.SCALE_DEFAULT);
        this.radius = radius;
        update(x,y);
    }

    public void setPanel(GamePanel panel) {
        this.panel = panel;
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

    public BufferedImage getImage() {
        return image;
    }
    public int getRadius() {
        return radius;
    }
}
