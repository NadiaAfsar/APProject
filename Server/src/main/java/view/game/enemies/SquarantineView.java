package view.game.enemies;

import controller.GameManager;
import model.interfaces.movement.Point;
import view.Rotation;

public class SquarantineView extends EnemyView{
    public SquarantineView(int x, int y, int width, int height, String ID) {
        super(x, y, width, height, GameManager.configs.SQUARANTINE, ID);
    }
}
