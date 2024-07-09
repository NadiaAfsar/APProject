package model.game;

import model.enemies.Omenoct;

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
            stuckOmenocts.remove(stuckOmenocts.get(i));
            i--;
        }
    }
}
