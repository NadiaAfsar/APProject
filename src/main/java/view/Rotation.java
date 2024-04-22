package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Rotation {
    public static BufferedImage rotate(BufferedImage image, double angle) {
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
