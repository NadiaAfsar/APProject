package view.game.enemies.smiley;

import controller.GameManager;
import view.game.enemies.EnemyView;

public class RightHandView extends EnemyView {
    public RightHandView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.RIGHT_HAND, ID);
    }
}
