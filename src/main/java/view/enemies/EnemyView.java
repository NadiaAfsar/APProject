package view.enemies;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class EnemyView extends JLabel {
    private int x;
    private int y;

    public EnemyView(int x, int y, String path) throws IOException {
        this.x = x;
        this.y = y;
        setIcon(new ImageIcon(ImageIO.read(new File(path))));
    }
}
