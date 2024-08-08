package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RunningGame {
    private ArrayList<Client> clientsInGame;
    private Map<Client, ArrayList<Entity>> addedEnemies;
    private Map<Client, Entity> epsilons;
    private String game;
    private Map<Client, ArrayList<Entity>> bullets;
    private int enemiesAdded;

    public RunningGame(ArrayList<Client> clientsInGame, String game) {
        this.clientsInGame = clientsInGame;
        this.game = game;
        epsilons = new HashMap<>();
        addedEnemies = new HashMap<>();
        bullets = new HashMap<>();
        for (int i = 0; i < clientsInGame.size(); i++){
            epsilons.put(clientsInGame.get(i), new Entity(0,0,0));
            addedEnemies.put(clientsInGame.get(i), new ArrayList<>());
            bullets.put(clientsInGame.get(i), new ArrayList<>());
        }

    }

    public ArrayList<Client> getClientsInGame() {
        return clientsInGame;
    }

    public Map<Client, ArrayList<Entity>> getAddedEnemies() {
        return addedEnemies;
    }

    public Map<Client, Entity> getEpsilons() {
        return epsilons;
    }

    public String getGame() {
        return game;
    }

    public Map<Client, ArrayList<Entity>> getBullets() {
        return bullets;
    }

    public int getEnemiesAdded() {
        return enemiesAdded;
    }

    public void setEnemiesAdded(int enemiesAdded) {
        this.enemiesAdded = enemiesAdded;
    }
}
