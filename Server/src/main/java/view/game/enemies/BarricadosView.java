package view.game.enemies;

import application.MyApplication;

public class BarricadosView extends EnemyView{
    public BarricadosView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.BARRICODES, ID);
    }
}
