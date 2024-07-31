package view.game.enemies.black_orb;

import application.MyApplication;
import view.game.enemies.EnemyView;

public class BlackOrbVertexView extends EnemyView {
    public BlackOrbVertexView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, MyApplication.configs.BLACK_ORB_VERTEX, ID);
    }
}
