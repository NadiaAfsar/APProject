package view.game.enemies.archmire;

import controller.GameManager;
import view.game.enemies.EnemyView;

public class AoEView extends EnemyView {
    public AoEView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.AOE_ATTACK, ID);

    }
}
