package view.game;

import application.MyApplication;
import model.interfaces.movement.Point;
import view.Rotation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    private BufferedImage image;
    private BufferedImage rotatedImage;
    public EntityView(int x, int y, int width, int height, String path, String ID) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        initialWidth = width;
        initialHeight = height;
        this.ID = ID;
        this.path = path;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rotatedImage = image;
    }
    public void update(Point center, double angle) {
        setX((int) center.getX() - width / 2);
        setY((int) center.getY() - height / 2);
        if (angle != this.angle){
            rotatedImage = Rotation.rotate(image, angle);
            width = (int)(rotatedImage.getWidth()/image.getWidth()*initialWidth);
            height = (int)(rotatedImage.getHeight()/image.getHeight()*initialHeight);
            this.angle = angle;
        }
    }
    public void updateArchmireAoE(int clarity){
        String path = "";
        if (clarity == 4){
            path = MyApplication.configs.AoE2;
        }
        else if (clarity == 3){
            path = MyApplication.configs.AoE3;
        }
        else if (clarity == 2){
            path = MyApplication.configs.AoE4;
        }
        else if (clarity == 1){
            path = MyApplication.configs.AoE5;
        }
        if (!path.equals(this.path) && !path.equals("")){
            this.path = path;
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            rotatedImage = image;
        }
    }
    public void updateAoE(int clarity){
        String path = "";
        if (clarity == 4){
            path = MyApplication.configs.AOE_ATTACK_2;
        }
        else if (clarity == 3){
            path = MyApplication.configs.AOE_ATTACK_3;
        }
        else if (clarity == 2){
            path = MyApplication.configs.AOE_ATTACK_4;
        }
        else if (clarity == 1){
            path = MyApplication.configs.AOE_ATTACK_5;
        }
        if (!path.equals(this.path) && !path.equals("")){
            this.path = path;
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            rotatedImage = image;
        }
    }
    public void updateSmiley(int width, int height, Point center, double angle){
        update(center, angle);
        this.width = width;
        this.height = height;
    }
    public void smileyDied(){
        try {
            image = ImageIO.read(new File(MyApplication.configs.DEAD));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rotatedImage = image;
    }
    public void smileyPhase2(){
        try {
            image = ImageIO.read(new File(MyApplication.configs.SMILEY2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rotatedImage = image;
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

    public BufferedImage getImage() {
        return rotatedImage;
    }

}
