package view.game.enemies.black_orb;

import controller.GameManager;
import view.game.enemies.EnemyView;

public class BlackOrbVertexView extends EnemyView {
    public BlackOrbVertexView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.BLACK_ORB_VERTEX, ID);
    }
}
