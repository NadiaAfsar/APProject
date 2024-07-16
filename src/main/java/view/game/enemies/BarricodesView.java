package view.game.enemies;

import controller.GameManager;

public class BarricodesView extends EnemyView{
    public BarricodesView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.BARRICODES, ID);
    }
}
