package view.game.enemies.necropick;

import application.MyApplication;
import view.game.enemies.EnemyView;

public class NecropickView extends EnemyView {

    public NecropickView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.NECROPICK, ID);

    }
}
