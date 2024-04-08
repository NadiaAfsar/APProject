package model;

import controller.Controller;

public class GameManager {
    public static GameManager INSTANCE;
    public GameManager() {
        Controller.addFrame();
    }

    public static GameManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }
}
