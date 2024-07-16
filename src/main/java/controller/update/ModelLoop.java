package controller.update;

import controller.save.Configs;

public class ModelLoop extends Thread{
    public void run() {
        while (true) {
            Update.updateModel();
            try {
                sleep((long)Configs.MODEL_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
