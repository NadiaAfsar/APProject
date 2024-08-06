package controller.game_manager;

import model.game.enemies.Enemy;
import model.game.frame.MyFrame;
import model.interfaces.movement.Point;
import network.TCP.ServerListener;

import java.util.ArrayList;

public class Monomachia extends GameManager{
    public Monomachia(ArrayList<ServerListener> listeners) {
        super(listeners);
    }
    public void addEnemy(int waveNumber){
        int enemyNumber = 0;
        if (waveNumber > 2){
            enemyNumber = (int)(Math.random()*8);
        }
        else {
            enemyNumber = (int)(Math.random()*6);
        }
        for (int i = 0; i < gameModel.getEpsilons().size(); i++) {
            MyFrame myFrame = gameModel.getEpsilons().get(i).getInitialMyFrame();
            Point position = GameManagerHelper.getRandomPosition(myFrame.getWidth(), myFrame.getHeight());
            Enemy enemy = GameManagerHelper.getNewEnemy(new Point(myFrame.getX() + position.getX(),
                            myFrame.getY() + position.getY()), gameModel.getEnemyHP(), gameModel.getEnemyVelocity(), enemyNumber,
                    myFrame, this, gameModel.getEpsilons().get(i));
            gameModel.getEnemies().add(enemy);
        }
    }
}
