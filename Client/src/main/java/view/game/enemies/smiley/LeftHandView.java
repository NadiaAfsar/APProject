package view.game.enemies.smiley;

import application.MyApplication;
import controller.GameManager;
import view.game.enemies.EnemyView;

public class LeftHandView extends EnemyView {
    public LeftHandView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.LEFT_HAND, ID);
    }
}
