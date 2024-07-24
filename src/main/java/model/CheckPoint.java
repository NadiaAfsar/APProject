package model;

import controller.Controller;
import controller.GameManager;
import model.frame.Frame;
import model.interfaces.movement.Point;

public class CheckPoint extends Thread{
    private Point center;
    private long timeAppeared;
    private boolean disappear;
    public CheckPoint(){
        Frame frame = GameManager.getINSTANCE().getGameModel().getInitialFrame();
        int x = 10+(int)(Math.random()*(frame.getWidth()-100))+(int)frame.getX();
        int y = 10+(int)(Math.random()*(frame.getHeight()-100))+(int)frame.getY();
        Controller.addPortal(new Point(x,y));
        center = new Point(x+72,y+100);
        timeAppeared = System.currentTimeMillis();
        start();
    }
    private void checkEpsilon(){
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        if (Calculations.getDistance(epsilon.getCenter().getX(), epsilon.getCenter().getY(), center.getX(), center.getY())
               <= 72-epsilon.getRadius() ){
            if (Controller.saveGame()) {
                GameManager.getINSTANCE().save();
            }
            else {
                epsilon.setXP(epsilon.getXP()+(int)(GameManager.getINSTANCE().getGameModel().getCurrentWave().getProgressRisk()*0.1));
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
        Controller.removePortal();
        interrupt();
    }
}
