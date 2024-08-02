package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    //private ArrayList<Client> clients;
    private Map<String, Squad> squads;
    private Map<String, Client> clients;
    private ArrayList<String> squadsName;
    private Map<String, Request> requests;
    public Server(){
        squads = new HashMap<>();
        clients = new HashMap<>();
        squadsName = new ArrayList<>();
        requests = new HashMap<>();
    }

    public Map<String, Squad> getSquads() {
        return squads;
    }

    public void setSquads(Map<String, Squad> squads) {
        this.squads = squads;
    }

    public Map<String, Client> getClients() {
        return clients;
    }

    public void setClients(Map<String, Client> clients) {
        this.clients = clients;
    }

    public ArrayList<String> getSquadsName() {
        return squadsName;
    }

    public void setSquadsName(ArrayList<String> squadsName) {
        this.squadsName = squadsName;
    }

    public Map<String, Request> getRequests() {
        return requests;
    }
}
