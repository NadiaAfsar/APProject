package model.game.enemies.mini_boss.black_orb;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
import controller.audio.AudioController;
import model.game.Collectible;
import model.game_model.GameModel;
import model.game.frame.MyFrame;
import model.interfaces.collision.Collidable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;
import java.util.UUID;

public class BlackOrbVertex implements Collidable {
    private MyFrame myFrame;
    private Point center;
    private double x;
    private double y;
    private double width;
    private double height;
    private int HP;
    private ArrayList<BlackOrbLaser> lasers;
    private BlackOrb blackOrb;
    private String ID;

    public BlackOrbVertex(Point center, BlackOrb blackOrb) {
        ID = UUID.randomUUID().toString();
        this.center = center;
        this.blackOrb = blackOrb;
        width = MyApplication.configs.BLACKORBVERTEX_RADIUS*2;
        height = MyApplication.configs.BLACKORBVERTEX_RADIUS*2;
        myFrame = new MyFrame(width+50, height+50, center.getX()-width/2-25, center.getY()-height/2-25,
                true, false, width+50, height+50, blackOrb.getGameManager());
        lasers = new ArrayList<>();
        myFrame.getBlackOrbVertices().add(this);
        blackOrb.getGameManager().getGameModel().getFrames().add(myFrame);
        HP = 30;
        Controller.addBlackOrbVertexView(this, blackOrb.getGameManager());
    }
    private void die(ArrayList<BlackOrbLaser> allLasers) {
        for (int i = 0; i < lasers.size(); i++) {
            allLasers.remove(lasers.get(i));
            Controller.removeLaserView(lasers.get(i), blackOrb.getGameManager());
        }
        addCollective();
        blackOrb.getBlackOrbVertices().remove(this);
        Controller.removeBlackOrbVertexView(this, blackOrb.getGameManager());
        AudioController.addEnemyDyingSound();
        if (blackOrb.getBlackOrbVertices().size() == 0){
            blackOrb.setDied(true);
        }
    }
    private void addCollective() {
        Collectible collectible = new Collectible((int)center.getX(), (int)center.getY(),30);
        blackOrb.getGameManager().getGameModel().getCollectibles().add(collectible);
        Controller.addCollectibleView(collectible, blackOrb.getGameManager());
    }
    public void decreaseHP(int x) {
        HP -= x;
        GameModel gameModel = blackOrb.getGameManager().getGameModel();
        gameModel.getMyEpsilon().setHP(gameModel.getMyEpsilon().getHP()+gameModel.getChiron());
        if (HP <= 0) {
            die(blackOrb.getLasers());
        }
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public ArrayList<RotatablePoint> getVertexes() {
        return null;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    public GameManager getGameManager() {
        return blackOrb.getGameManager();
    }

    public ArrayList<BlackOrbLaser> getLasers() {
        return lasers;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getID() {
        return ID;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public BlackOrb getBlackOrb() {
        return blackOrb;
    }
}
