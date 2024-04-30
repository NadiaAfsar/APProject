package model;


public class GameManager {
    private int totallXP;
    public static GameManager INSTANCE;
    public GameManager() {

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
}
