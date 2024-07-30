package model.game.frame;

import model.game.enemies.normal.Omenoct;

import java.util.ArrayList;

public class Side {
    ArrayList<Omenoct> stuckOmenocts;
    public Side() {
        stuckOmenocts = new ArrayList<>();
    }

    public ArrayList<Omenoct> getStuckOmenocts() {
        return stuckOmenocts;
    }
    public void separateAll() {
        for (int i = 0; i < stuckOmenocts.size(); i++) {
            stuckOmenocts.get(i).separate();
        }
        stuckOmenocts = new ArrayList<>();
    }
    public void shootAll() {
        for (int i = 0; i < stuckOmenocts.size(); i++) {
            stuckOmenocts.get(i).decreaseHP(5);
        }
    }
}
