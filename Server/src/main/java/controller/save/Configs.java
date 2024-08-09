package controller.save;

import java.util.ArrayList;

public class Configs {
    private ArrayList<String> squads;
    private ArrayList<String> clients;
    private ArrayList<String> requests;

    public ArrayList<String> getSquads() {
        return squads;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }

    public void setSquads(ArrayList<String> squads) {
        this.squads = squads;
    }

    public ArrayList<String> getClients() {
        return clients;
    }

    public void setClients(ArrayList<String> clients) {
        this.clients = clients;
    }
}
