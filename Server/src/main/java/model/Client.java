package model;

import model.game.skills.Skill;

import java.util.ArrayList;
import java.util.UUID;

public class Client {
    private String username;
    private Squad squad;
    private int XP;
    private String ID;
    private Status status;
    private ArrayList<Request> sentRequests;
    private ArrayList<Request> receivedRequests;
    private boolean monomachia;
    private boolean colosseum;
    public Client(String username){
        this.username = username;
        ID = UUID.randomUUID().toString();
        status = Status.ONLINE;
        sentRequests = new ArrayList<>();
        receivedRequests = new ArrayList<>();
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
        monomachia = false;
        colosseum = false;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public ArrayList<Request> getSentRequests() {
        return sentRequests;
    }

    public ArrayList<Request> getReceivedRequests() {
        return receivedRequests;
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

    public boolean isMonomachia() {
        return monomachia;
    }

    public boolean isColosseum() {
        return colosseum;
    }

}
