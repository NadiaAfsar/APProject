package controller;

import controller.update.ModelLoop;
import controller.update.ViewLoop;
import model.game.skills.Skill;
import model.game.skills.attack.WritOfAres;
import model.game.skills.attack.WritOfAstrape;
import model.game.skills.attack.WritOfCerberus;
import model.game.skills.defence.WritOfAceso;
import model.game.skills.defence.WritOfAthena;
import model.game.skills.defence.WritOfChiron;
import model.game.skills.defence.WritOfMelampus;
import model.game.skills.transform.WritOfDolus;
import model.game.skills.transform.WritOfEmpusa;
import model.game.skills.transform.WritOfProteus;
import network.ClientHandler;
import view.menu.GameFrame;

import java.util.ArrayList;

public class ApplicationManager {
    private int totalXP;
    private int difficulty;
    private int sensitivity;
    private Skill pickedSkill;
    private GameFrame gameFrame;
    private ClientHandler clientHandler;
    private ArrayList<Skill> unlockedSkills;
    private boolean online;
    public ApplicationManager(ClientHandler clientHandler, boolean online){
        this.clientHandler = clientHandler;
        this.online = online;
    }
    public void initialize(){
        totalXP = 10000;
        sensitivity = 2;
        difficulty = 1;
        unlockedSkills = new ArrayList<>();
        setSkills();
        gameFrame = new GameFrame(this, online);
    }
    private void setSkills(){
        WritOfAres.setBooleans();
        WritOfAstrape.setBooleans();
        WritOfCerberus.setBooleans();
        WritOfAceso.setBooleans();
        WritOfAthena.setBooleans();
        WritOfMelampus.setBooleans();
        WritOfChiron.setBooleans();
        WritOfProteus.setBooleans();
        WritOfDolus.setBooleans();
        WritOfEmpusa.setBooleans();
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public Skill getPickedSkill() {
        return pickedSkill;
    }

    public void setPickedSkill(Skill pickedSkill) {
        this.pickedSkill = pickedSkill;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ArrayList<Skill> getUnlockedSkills() {
        return unlockedSkills;
    }

    public void setUnlockedSkills(ArrayList<Skill> unlockedSkills) {
        this.unlockedSkills = unlockedSkills;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
