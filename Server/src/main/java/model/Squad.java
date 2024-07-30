package model;

import java.util.ArrayList;

public class Squad {
    private ArrayList<Client> members;
    private Client owner;

    public ArrayList<Client> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Client> members) {
        this.members = members;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }
}
