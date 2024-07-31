package model;

import model.game.skills.Skill;

import java.util.ArrayList;
import java.util.UUID;

public class Client {
    private String username;
    private Squad squad;
    private int XP;
    private String ID;
    private ArrayList<String> requests;
    public Client(String username){
        this.username = username;
        ID = UUID.randomUUID().toString();
        requests = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public String getID() {
        return ID;
    }
}
