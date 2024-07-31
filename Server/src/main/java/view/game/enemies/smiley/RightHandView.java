package view.game.enemies.smiley;

import application.MyApplication;
import view.game.enemies.EnemyView;

public class RightHandView extends EnemyView {
    public RightHandView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.RIGHT_HAND, ID);
    }
}
