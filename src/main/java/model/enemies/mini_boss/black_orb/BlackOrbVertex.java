package model.enemies.mini_boss.black_orb;

import controller.Controller;
import controller.GameManager;
import controller.audio.AudioController;
import model.Collectible;
import model.frame.Frame;
import model.interfaces.collision.Collidable;
import model.interfaces.movement.Point;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;
import java.util.UUID;

public class BlackOrbVertex implements Collidable {
    private Frame frame;
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
        width = GameManager.configs.BLACKORBVERTEX_RADIUS*2;
        height = GameManager.configs.BLACKORBVERTEX_RADIUS*2;
        frame = new Frame(width+20, height+20, center.getX()-width/2-10, center.getY()-height/2-10,
                true, false, false);
        lasers = new ArrayList<>();
        frame.getBlackOrbVertices().add(this);
        HP = 30;
        Controller.addBlackOrbVertexView(this);
    }
    private void die(ArrayList<BlackOrbLaser> allLasers) {
        for (int i = 0; i < lasers.size(); i++) {
            allLasers.remove(lasers.get(i));
            Controller.removeLaserView(lasers.get(i));
        }
        addCollective();
        blackOrb.getBlackOrbVertices().remove(this);
        Controller.removeBlackOrbVertexView(this);
        AudioController.addEnemyDyingSound();
    }
    private void addCollective() {
        Collectible collectible = new Collectible((int)center.getX(), (int)center.getY(),30);
        GameManager.getINSTANCE().getGameModel().getCollectives().add(collectible);
        Controller.addCollectibleView(collectible);
    }
    public void decreaseHP(int x) {
        HP -= x;
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
}
