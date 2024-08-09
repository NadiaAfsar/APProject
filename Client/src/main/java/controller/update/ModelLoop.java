package controller.update;

import controller.game_manager.GameManager;
import controller.save.Configs;

public class ModelLoop extends Thread{
    private GameManager gameManager;
    public ModelLoop(GameManager gameManager){
        this.gameManager = gameManager;
    }
    public void run() {
        while (!gameManager.isEnded()) {
            Update.updateModel(gameManager);
            try {
                sleep((long)Configs.MODEL_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
