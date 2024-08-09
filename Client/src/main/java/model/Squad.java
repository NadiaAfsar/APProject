package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Squad {
    private String name;
    private ArrayList<String> members;
    private String ownerName;
    private String ID;
    private boolean inBattle;
    private String competitorSquad;
    private int XP;
    private boolean palioxis;
    private boolean adonis;
    private boolean gefion;
    public Squad(Client owner, String name){
        ID = UUID.randomUUID().toString();
        this.name = name;
        ownerName = owner.getUsername();
        members = new ArrayList<>();
        members.add(owner.getUsername());
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public String getOwner() {
        return ownerName;
    }

    public void setOwner(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getName() {
        return name;
    }

    public boolean isInBattle() {
        return inBattle;
    }

    public String getCompetitorSquad() {
        return competitorSquad;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public boolean isPalioxis() {
        return palioxis;
    }

    public void setPalioxis(boolean palioxis) {
        this.palioxis = palioxis;
    }

    public boolean isAdonis() {
        return adonis;
    }

    public void setAdonis(boolean adonis) {
        this.adonis = adonis;
    }

    public boolean isGefion() {
        return gefion;
    }

    public void setGefion(boolean gefion) {
        this.gefion = gefion;
    }
}
