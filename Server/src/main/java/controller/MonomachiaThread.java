package controller;

import model.RunningGame;

public class MonomachiaThread extends Thread{
    private RunningGame runningGame;
    public MonomachiaThread(RunningGame runningGame){
        this.runningGame = runningGame;
    }
    public void run(){
        try {
            sleep(616000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        runningGame.endGame(null);
    }

}
