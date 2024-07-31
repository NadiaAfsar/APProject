package view.game.enemies.smiley;

import application.MyApplication;
import model.interfaces.movement.Point;
import view.game.enemies.EnemyView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SmileyView extends EnemyView {
    public SmileyView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.SMILEY1, ID);
    }
    public void phase2() {
        try {
            image = ImageIO.read(new File(MyApplication.configs.SMILEY2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rotatedImage = image;

    }
    public void update(int width, int height, Point center, double angle){
        this.setWidth(width);
        this.setHeight(height);
        super.update(center, angle);
    }
    public void die(){
        try {
            image = ImageIO.read(new File(MyApplication.configs.DEAD));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rotatedImage = image;
    }
}
