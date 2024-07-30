package view.game.enemies.necropick;

import controller.GameManager;
import view.game.enemies.EnemyView;

public class NecropickView extends EnemyView {

    public NecropickView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.NECROPICK, ID);

    }
}
