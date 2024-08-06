package controller.game_manager;

import controller.ApplicationManager;
import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.game_model.MonomachiaGame;
import model.interfaces.movement.Point;
import network.ClientHandler;
import view.game.GameView;

public class Monomachia extends GameManager{
    public Monomachia(ApplicationManager applicationManager) {
        super(applicationManager, true);
    }
    public void startGame(int epsilonNumber) {
        gameView = new GameView(this);
        gameModel = new MonomachiaGame(this, epsilonNumber);
    }

}
