package model.skills;

import model.GameModel;

public class WritOfAres extends Skill{
    @Override
    public void activate() {
        GameModel gameModel = GameModel.getINSTANCE();
        gameModel.setAres(gameModel.getAres()+2);
    }
}
