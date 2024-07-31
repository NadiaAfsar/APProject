package model.game;

import controller.Controller;
import controller.GameManager;
import model.Calculations;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;

public class CheckPoint extends Thread{
    private Point center;
    private long timeAppeared;
    private boolean disappear;
    private GameManager gameManager;
    public CheckPoint(GameManager gameManager){
        MyFrame myFrame = gameManager.getGameModel().getInitialFrame();
        int x = 10+(int)(Math.random()*(myFrame.getWidth()-100))+(int) myFrame.getX();
        int y = 10+(int)(Math.random()*(myFrame.getHeight()-100))+(int) myFrame.getY();
        Controller.addPortal(new Point(x,y), gameManager);
        center = new Point(x+72,y+100);
        timeAppeared = System.currentTimeMillis();
        start();
    }
    private void checkEpsilon(){
        EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
        if (Calculations.getDistance(epsilon.getCenter().getX(), epsilon.getCenter().getY(), center.getX(), center.getY())
               <= 50-epsilon.getRadius() ){
            Controller.gameRunning = false;
            if (Controller.saveGame(gameManager)) {
                gameManager.save();
                Controller.gameRunning = true;
            }
            else {
                epsilon.setXP(epsilon.getXP()+(int)(gameManager.getGameModel().getCurrentWave().getProgressRisk()*0.1));
                Controller.gameRunning = true;
            }
            disappear = true;
        }
    }
    public void run(){
        while (!disappear){
            checkEpsilon();
            if (System.currentTimeMillis()-timeAppeared >= 6000){
                disappear = true;
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Controller.removePortal(gameManager);
        interrupt();
    }
}
