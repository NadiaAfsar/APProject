package view.enemies;

import view.GameView;

import java.io.IOException;

public class SquarantineView extends EnemyView{
    public SquarantineView(int x, int y) throws IOException {
        super(x, y, "src/main/resources/square.png");
        setBounds(x,y,26,26);
        GameView.getINSTANCE().add(this);
        GameView.getINSTANCE().getEnemies().add(this);
    }
    @Override
    public void update(int x, int y) {
        setX(x);
        setY(y);
        setBounds(x, y, 26, 26);
    }
}
