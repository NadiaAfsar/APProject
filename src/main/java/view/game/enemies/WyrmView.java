package view.game.enemies;

import controller.GameManager;

public class WyrmView extends EnemyView{
    public WyrmView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.WYRM, ID);
    }
}
