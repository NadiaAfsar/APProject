package model;

import model.game.skills.Skill;

import java.util.ArrayList;
import java.util.UUID;

public class Client {
    private String username;
    private Squad squad;
    private int XP;
    private String ID;
    private ArrayList<Skill> unlockedSkills;
    private Status status;
    private ArrayList<Request> sentRequests;
    private ArrayList<Request> receivedRequests;
    public Client(){
        ID = UUID.randomUUID().toString();
        sentRequests = new ArrayList<>();
        receivedRequests = new ArrayList<>();
        status = Status.ONLINE;
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

    public ArrayList<Skill> getUnlockedSkills() {
        if (unlockedSkills == null){
            unlockedSkills = new ArrayList<>();
        }
        return unlockedSkills;
    }

    public String getID() {
        return ID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Request> getSentRequests() {
        return sentRequests;
    }

    public ArrayList<Request> getReceivedRequests() {
        return receivedRequests;
    }
}
