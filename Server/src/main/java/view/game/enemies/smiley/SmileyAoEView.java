package view.game.enemies.smiley;

import application.MyApplication;
import view.game.enemies.EnemyView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SmileyAoEView extends EnemyView {
    private int clarity;
    public SmileyAoEView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.AoE1, ID);
        clarity = 6;
    }
    public void update(int clarity){
        if (this.clarity != clarity){
            this.clarity = clarity;
            String path = null;
            if (clarity == 5){
                path = MyApplication.configs.AoE1;
            }
            else if (clarity == 4){
                path = MyApplication.configs.AoE2;
            }
            else if (clarity == 3){
                path = MyApplication.configs.AoE3;
            }
            else if (clarity == 2){
                path = MyApplication.configs.AoE4;
            }
            else {
                path = MyApplication.configs.AoE5;
            }
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
