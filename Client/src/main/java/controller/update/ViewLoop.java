package controller.update;

import controller.game_manager.GameManager;
import controller.save.Configs;

public class ViewLoop extends Thread{
    private GameManager gameManager;
    public ViewLoop(GameManager gameManager){
        this.gameManager = gameManager;
    }
    public void run() {
        while (!gameManager.isEnded()) {
            Update.updateView(gameManager);
            try {
                sleep((long) Configs.FRAME_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
