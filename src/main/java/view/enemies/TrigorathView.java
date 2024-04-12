package view.enemies;

import view.GameView;

import java.io.IOException;

public class TrigorathView extends EnemyView {
    public TrigorathView(int x, int y) throws IOException {
        super(x, y, "src/main/resources/triangle.png");
        setBounds(x,y,26,24);
        GameView.getINSTANCE().add(this);
        GameView.getINSTANCE().getEnemies().add(this);
    }
    @Override
    public void update(int x, int y) {
        setX(x);
        setY(y);
        setBounds(x, y, 26, 24);
    }
}
