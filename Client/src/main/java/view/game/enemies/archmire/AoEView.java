package view.game.enemies.archmire;

import application.MyApplication;
import controller.GameManager;
import view.game.enemies.EnemyView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AoEView extends EnemyView {
    private int clarity;
    public AoEView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.AOE_ATTACK_1, ID);
        clarity = 5;
    }
    public void update(int clarity){
        if (this.clarity != clarity){
            this.clarity = clarity;
            String path = null;
            if (clarity == 4){
                path = MyApplication.configs.AOE_ATTACK_2;
            }
            else if (clarity == 3){
                path = MyApplication.configs.AOE_ATTACK_3;
            }
            else if (clarity == 2){
                path = MyApplication.configs.AOE_ATTACK_4;
            }
            else {
                path = MyApplication.configs.AOE_ATTACK_5;
            }
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
