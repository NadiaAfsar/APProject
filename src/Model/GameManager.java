package Model;

import Controller.GameViewConnector;
import View.InputListener;

public class GameManager {
    private GameViewConnector gameViewConnector;
    private InputListener inputListener;
    public GameManager() {
        gameViewConnector = new GameViewConnector(this);
        gameViewConnector.newFrame();
    }

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }
}
