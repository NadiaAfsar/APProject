package controller.game_manager;

import controller.ApplicationManager;
import model.game_model.ColosseumGame;
import view.game.GameView;

public class Colosseum extends GameManager{
    public Colosseum(ApplicationManager applicationManager) {
        super(applicationManager, true);
    }
    public void startGame(int epsilonNumber) {
        competitorXP = 0;
        competitorHP = 100;
        gameView = new GameView(this);
        gameModel = new ColosseumGame(this, epsilonNumber);
    }
    public void finishGame(){
        getApplicationManager().setTotalXP(getApplicationManager().getTotalXP()+gameModel.getMyEpsilon().getXP());
    }
    public void removeOtherEpsilon(){
        for (int i = 0; i < gameModel.getEpsilons().size(); i++){
            if (!gameModel.getEpsilons().get(i).equals(gameModel.getMyEpsilon())){
                gameModel.getEpsilons().remove(gameModel.getEpsilons().get(i));
                return;
            }
        }
    }
}
