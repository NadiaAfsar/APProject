package view.enemies;

import movement.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class EnemyView extends JLabel {
    private int x;
    private int y;
    private double angle;
    protected BufferedImage image;


    public EnemyView(int x, int y, String path) throws IOException {
        this.x = x;
        this.y = y;
        image = ImageIO.read(new File(path));
        setIcon(new ImageIcon(image));
        setBounds(x,y,image.getWidth(), image.getHeight());
    }
    public abstract void update(Point center, double angle);

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
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
    protected BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));
        int newWidth = (int)Math.round(image.getWidth()*cos + image.getHeight()*sin);
        int newHeight = (int)Math.round(image.getWidth()*sin + image.getHeight()*cos);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = rotatedImage.createGraphics();
        int x = (newWidth - image.getWidth())/2;
        int y = (newHeight - image.getHeight())/2;
        AffineTransform at = new AffineTransform();
        at.setToRotation(angle, x + image.getWidth()/2, y + image.getHeight()/2);
        at.translate(x, y);
        g2D.setTransform(at);
        g2D.drawImage(image, 0, 0, null);
        g2D.dispose();
        return rotatedImage;
    }
}
