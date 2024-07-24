package view.game.epsilon;

import controller.Controller;
import controller.GameManager;
import controller.save.Configs;
import model.interfaces.movement.RotatablePoint;
import view.game.GamePanel;

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
    private ArrayList<Vertex> vertexes;
    private BufferedImage image;
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
        Vertex vertex = new Vertex(new Point(x,y));
        vertexes.add(vertex);
    }
    public void removeVertexes() {
        vertexes = new ArrayList<>();
    }
    public void updateVertexes(ArrayList<RotatablePoint> vertexes) {
        synchronized (Controller.epsilonLock) {
            for (int i = 0; i < vertexes.size(); i++) {
                RotatablePoint vertex = vertexes.get(i);
                Vertex epsilonVertex = this.vertexes.get(i);
                epsilonVertex.setCenter(new Point((int) vertex.getRotatedX(), (int) vertex.getRotatedY()));
            }
        }
    }
    public void increaseSize(int x, int y, int radius) {
        Image image = this.image.getScaledInstance(2*radius,2*radius, Image.SCALE_DEFAULT);
        this.radius = radius;
        update(x,y);
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

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }
}
