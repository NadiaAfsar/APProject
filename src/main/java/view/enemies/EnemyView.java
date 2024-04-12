package view.enemies;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public abstract class EnemyView extends JLabel {
    private int x;
    private int y;

    public EnemyView(int x, int y, String path) throws IOException {
        this.x = x;
        this.y = y;
        setIcon(new ImageIcon(ImageIO.read(new File(path))));
    }
    public abstract void update(int x, int y);

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
}
