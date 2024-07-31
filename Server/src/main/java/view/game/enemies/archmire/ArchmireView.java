package view.game.enemies.archmire;

import application.MyApplication;
import view.game.enemies.EnemyView;

public class ArchmireView extends EnemyView {
    public ArchmireView(int x, int y, int width, int height,  String ID) {
        super(x, y, width, height, MyApplication.configs.ARCHMIRE, ID);
    }
}
