package model;


import model.skills.Skill;

public class GameManager {
    private int totallXP;
    public static GameManager INSTANCE;
    private static int difficulty;
    private static int sensitivity;
    private Skill pickedSkill;
    public GameManager() {
        totallXP = 2000;
        sensitivity = 2;
        difficulty = 1;
    }

    public static GameManager getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    public int getTotallXP() {
        return totallXP;
    }

    public void setTotallXP(int totallXP) {
        this.totallXP = totallXP;
    }

    public static void setINSTANCE(GameManager INSTANCE) {
        GameManager.INSTANCE = INSTANCE;
    }

    public Skill getPickedSkill() {
        return pickedSkill;
    }

    public void setPickedSkill(Skill pickedSkill) {
        this.pickedSkill = pickedSkill;
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(int difficulty) {
        GameManager.difficulty = difficulty;
    }

    public static int getSensitivity() {
        return sensitivity;
    }

    public static void setSensitivity(int sensitivity) {
        GameManager.sensitivity = sensitivity;
    }
}
