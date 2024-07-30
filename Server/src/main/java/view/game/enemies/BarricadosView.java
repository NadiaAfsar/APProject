package view.game.enemies;

import controller.GameManager;

public class BarricadosView extends EnemyView{
    public BarricadosView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.BARRICODES, ID);
    }
}
