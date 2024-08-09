package model;

import application.MyApplication;
import controller.ServerThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private ArrayList<String> clientsName;
    private Map<String, Squad> squads;
    private Map<String, Client> clients;
    private ArrayList<String> squadsName;
    private Map<String, Request> requests;
    private ArrayList<String> requestsName;
    public Server(){
        squads = new HashMap<>();
        clients = new HashMap<>();
        squadsName = new ArrayList<>();
        requests = new HashMap<>();
        clientsName = new ArrayList<>();
        requestsName = new ArrayList<>();
    }
    public void load(){
        MyApplication.readerWriter.loadServerData(this);
        new ServerThread(this).start();
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

    public ArrayList<String> getClientsName() {
        return clientsName;
    }

    public void setClientsName(ArrayList<String> clientsName) {
        this.clientsName = clientsName;
    }

    public void setRequests(Map<String, Request> requests) {
        this.requests = requests;
    }

    public ArrayList<String> getRequestsName() {
        return requestsName;
    }

    public void setRequestsName(ArrayList<String> requestsName) {
        this.requestsName = requestsName;
    }
}
