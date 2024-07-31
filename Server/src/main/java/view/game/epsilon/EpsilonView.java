package view.game.epsilon;

import application.MyApplication;
import controller.Controller;
import controller.save.Configs;
import model.interfaces.movement.RotatablePoint;

import javax.imageio.ImageIO;
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
    private ArrayList<Vertex> cerberuses;
    public EpsilonView() {
        x = Configs.FRAME_SIZE.width/2;
        y = Configs.FRAME_SIZE.height/2;
        radius = 12;
        try {
            image = ImageIO.read(new File(MyApplication.configs.EPSILON));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        vertexes = new ArrayList<>();
        cerberuses = new ArrayList<>();
    }
    public void update(int x, int y, int radius) {
        this.x  = x;
        this.y = y;
        this.radius = radius;
    }

    public void addVertex(int x, int y) {
        Vertex vertex = new Vertex(new Point(x,y), 3);
        vertexes.add(vertex);
    }
    public void addCerberus(int x, int y){
        Vertex cerberus = new Vertex(new Point(x,y),10);
        cerberuses.add(cerberus);
    }
    public void removeCerbeuses(){
        cerberuses = new ArrayList<>();
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
    public void updateCerberuses(ArrayList<RotatablePoint> cerberuces) {
        synchronized (Controller.cerberusLock) {
            for (int i = 0; i < cerberuses.size(); i++) {
                RotatablePoint vertex = cerberuces.get(i);
                Vertex cerberus = this.cerberuses.get(i);
                cerberus.setCenter(new Point((int) vertex.getRotatedX(), (int) vertex.getRotatedY()));
            }
        }
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

    public ArrayList<Vertex> getCerberuses() {
        return cerberuses;
    }
}
