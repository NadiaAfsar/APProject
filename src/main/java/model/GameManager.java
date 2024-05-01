package model;


import model.skills.Skill;

public class GameManager {
    private int totallXP;
    public static GameManager INSTANCE;
    private Skill pickedSkill;
    public GameManager() {
        totallXP = 2000;
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
}
