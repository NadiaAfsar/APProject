package view.game.enemies;

import application.MyApplication;
import controller.GameManager;

public class WyrmView extends EnemyView{
    public WyrmView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.WYRM, ID);
    }
}
