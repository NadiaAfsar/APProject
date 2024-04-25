package model;

import controller.Controller;

import java.io.IOException;

public class GameManager {
    public static GameManager INSTANCE;
    public GameManager() {

    }

    public static GameManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }
}
