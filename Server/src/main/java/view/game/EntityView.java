package view.game;

import model.interfaces.movement.Point;
import view.Rotation;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EntityView {
    private int x;
    private int y;
    private double angle;
    private int width;
    private int height;
    private double initialWidth;
    private double initialHeight;
    private String ID;
    private String path;
    public EntityView(int x, int y, int width, int height, String path, String ID) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initialWidth = width;
        initialHeight = height;
        this.ID = ID;
        this.path = path;
    }
    public void update(Point center, double angle) {
        setX((int)center.getX() - width/2);
        setY((int)center.getY() - height/2);
        this.angle = angle;
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getInitialWidth() {
        return initialWidth;
    }

    public void setInitialWidth(double initialWidth) {
        this.initialWidth = initialWidth;
    }

    public double getInitialHeight() {
        return initialHeight;
    }

    public void setInitialHeight(double initialHeight) {
        this.initialHeight = initialHeight;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
