package controller.game_manager;

import controller.ApplicationManager;
import model.game.CheckPoint;
import model.game.Wave;
import model.game_model.MonomachiaGame;
import view.game.GameView;
import view.menu.GameFrame;

public class Monomachia extends GameManager{
    public Monomachia(ApplicationManager applicationManager) {
        super(applicationManager, true);
    }
    public void startGame(int epsilonNumber) {
        competitorXP = 0;
        competitorHP = 100;
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
    public void finishGame(){
        super.finishGame();
        getApplicationManager().setTotalXP(getApplicationManager().getTotalXP()+gameModel.getMyEpsilon().getXP());
    }
    public void checkWinner(String winner){
        if (winner.equals(getApplicationManager().getClientHandler().getClient().getUsername())){
            getApplicationManager().setTotalXP(getApplicationManager().getTotalXP()+80);
            GameFrame.showMessage("You won!");
        }
        else if (winner.equals("TIED")){
            getApplicationManager().setTotalXP(getApplicationManager().getTotalXP()+80);
            GameFrame.showMessage("Tied!");
        }
        else {
            GameFrame.showMessage("You lost!");
        }

    }

}
