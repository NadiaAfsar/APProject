package model;

import network.ClientHandler;

import java.util.ArrayList;
import java.util.UUID;

public class Squad {
    private String name;
    private ArrayList<Client> members;
    private String ownerID;
    private String ID;
    public Squad(Client owner, String name){
        ID = UUID.randomUUID().toString();
        this.name = name;
        ownerID = owner.getID();
        members = new ArrayList<Client>(){{add(owner);}};
    }

    public ArrayList<Client> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Client> members) {
        this.members = members;
    }

    public String getOwner() {
        return ownerID;
    }

    public void setOwner(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getName() {
        return name;
    }
}
