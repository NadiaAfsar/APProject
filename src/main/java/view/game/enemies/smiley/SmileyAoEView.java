package view.game.enemies.smiley;

import controller.GameManager;
import view.game.enemies.EnemyView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SmileyAoEView extends EnemyView {
    private int clarity;
    public SmileyAoEView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.AoE1, ID);
        clarity = 5;
    }
    public void update(int clarity){
        if (this.clarity != clarity){
            this.clarity = clarity;
            String path = null;
            if (clarity == 4){
                path = GameManager.configs.AoE2;
            }
            else if (clarity == 3){
                path = GameManager.configs.AoE3;
            }
            else if (clarity == 2){
                path = GameManager.configs.AoE4;
            }
            else {
                path = GameManager.configs.AoE5;
            }
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
