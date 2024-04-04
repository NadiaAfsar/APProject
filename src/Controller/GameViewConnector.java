package Controller;

import Model.GameManager;
import Model.MainCharacter;
import View.GameFrame;
import View.InputListener;

import java.io.IOException;

public class GameViewConnector {
    private static GameFrame gameFrame;
    private static GameManager gameManager;
    public GameViewConnector(GameManager gameManager) {
        this.gameManager = gameManager;

    }
    public void newFrame() {
        gameFrame = new GameFrame();
    }

    public static GameFrame getGameFrame() {
        return gameFrame;
    }
    public static void Start() throws IOException {
        gameManager.setInputListener(new InputListener(new MainCharacter()));
    }
}
