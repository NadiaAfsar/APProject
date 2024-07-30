package view.game.enemies;

import application.MyApplication;
import controller.GameManager;

public class SquarantineView extends EnemyView{
    public SquarantineView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.SQUARANTINE, ID);
    }
}
