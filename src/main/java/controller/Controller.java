package controller;

import model.EpsilonModel;
import model.GameModel;
import view.EpsilonView;
import view.GameFrame;
import view.GameView;

import java.io.IOException;

public class Controller {
    public static void addFrame() {
        GameFrame.getINSTANCE();
    }
    public static void startGame() throws IOException {
        GameModel.getINSTANCE();
        GameView.getINSTANCE();
        EpsilonModel.getINSTANCE();
        EpsilonView.getINSTANCE();
        GameView.getINSTANCE().add(EpsilonView.getINSTANCE());
        new Update();
    }

}
