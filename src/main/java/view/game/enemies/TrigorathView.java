package view.game.enemies;

import controller.GameManager;
import model.interfaces.movement.Point;
import view.Rotation;

public class TrigorathView extends EnemyView {
    public TrigorathView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.TRIGORATH, ID);

    }
}
