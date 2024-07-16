package controller.update;

import controller.save.Configs;

public class ViewLoop extends Thread{
    public void run() {
        while (true) {
            Update.updateView();
            try {
                sleep((long) Configs.FRAME_UPDATE_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
