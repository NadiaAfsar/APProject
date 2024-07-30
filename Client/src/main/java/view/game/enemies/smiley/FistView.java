package view.game.enemies.smiley;

import application.MyApplication;
import controller.GameManager;
import view.game.enemies.EnemyView;

public class FistView extends EnemyView {
    public FistView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.FIST, ID);
    }
}
