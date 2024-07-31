package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    //private ArrayList<Client> clients;
    private Map<String, Squad> squads;
    private Map<String, Client> clients;
    public Server(){
        squads = new HashMap<>();
        clients = new HashMap<>();
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
}
