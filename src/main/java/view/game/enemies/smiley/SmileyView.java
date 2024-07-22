package view.game.enemies.smiley;

import controller.GameManager;
import view.game.enemies.EnemyView;

public class SmileyView extends EnemyView {
    public SmileyView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.SMILEY, ID);
    }
}
