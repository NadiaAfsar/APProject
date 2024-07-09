package view.game;

import controller.Constants;
import movement.RotatablePoint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EpsilonView extends JLabel{
    private int x;
    private int y;
    private int radius;
    private ArrayList<JLabel> vertexes;
    private BufferedImage image;
    private GameView gameView;
    public EpsilonView(GameView gameView) {
        this.gameView = gameView;
        x = Constants.FRAME_SIZE.width/2;
        y = Constants.FRAME_SIZE.height/2;
        radius = Constants.EPSILON_RADIUS;
        try {
            image = ImageIO.read(new File("src/main/resources/epsilon.png"));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Image newImage = image.getScaledInstance(radius*2, radius*2, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(newImage));
        setBounds(x,y,2*radius,2*radius);
        vertexes = new ArrayList<>();
    }
    public void update(int x, int y) {
        this.x  = x;
        this.y = y;
        setBounds(x,y,2*radius,2*radius);
    }

    public void addVertex(int x, int y) {
        gameView.remove(this);
        JLabel vertex = new JLabel("●");
        vertex.setFont(new Font("Serif", Font.PLAIN, 10));
        vertex.setForeground(Color.WHITE);
        vertex.setBounds(x-5,y-50,100,100);
        vertexes.add(vertex);
        gameView.add(vertex);
        gameView.add(this);
    }
    public void removeVertexes() {
        for (int i = 0; i < vertexes.size(); i++) {
            gameView.remove(vertexes.get(i));
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
        setIcon(new ImageIcon(image));
    }

}
