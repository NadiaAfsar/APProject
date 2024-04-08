package model;

import view.GameView;

public class GameModel {
    private double width;
    private double height;
    private double x;
    private double y;
    public static GameModel INSTANCE;
    private boolean decreaseSize;

    public GameModel() {
        x = 0;
        y = 0;
        width = 700;
        height = 700;
        decreaseSize = true;
    }

    public static GameModel getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameModel();
        }
        return INSTANCE;
    }
    public void decreaseSize() {
        if (decreaseSize) {
            width -= 1;
            height -= 1;
            x = (700 - width) / 2;
            y = (700 - width) / 2;
            EpsilonModel.getINSTANCE().setInCenter();
            if (width == 300) {
                decreaseSize = false;
            }
        }
        else if (width > 200 && height > 200) {
            width -= 0.1;
            height -= 0.1;
            x = (700 - width) / 2;
            y = (700 - width) / 2;
        }
    }

    public int getWidth() {
        return (int)width;
    }

    public int getHeight() {
        return (int)height;
    }

    public int getX() {
        return (int)x;
    }

    public int getY() {
        return (int)y;
    }
}
