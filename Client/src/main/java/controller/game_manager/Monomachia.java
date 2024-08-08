package controller.game_manager;

import controller.ApplicationManager;
import model.game.CheckPoint;
import model.game.Wave;
import model.game_model.MonomachiaGame;
import view.game.GameView;

public class Monomachia extends GameManager{
    public Monomachia(ApplicationManager applicationManager) {
        super(applicationManager, true);
    }
    public void startGame(int epsilonNumber) {
        gameView = new GameView(this);
        gameModel = new MonomachiaGame(this, epsilonNumber);
    }
    public void nextWave() {
        gameModel.setWave(gameModel.getWave()+1);
        if (gameModel.getCurrentWave() != null) {
            new CheckPoint(this);
            gameModel.setTotalPR(gameModel.getTotalPR() + gameModel.getCurrentWave().getProgressRate());
            gameModel.setKilledEnemies(gameModel.getKilledEnemies()+gameModel.getCurrentWave().getDiedEnemies());
        }
        gameModel.setCurrentWave(new Wave(gameModel.getWave(), gameModel.getWave()*2, this));

    }

}
