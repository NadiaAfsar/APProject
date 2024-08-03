package model.game.skills;


import controller.game_manager.GameManager;

public abstract class Skill {
    private long lastActivatedTime;
    protected String name;
    protected boolean activated;
    public Skill() {

    }


    protected boolean isTimeToActivate() {
        if (lastActivatedTime != 0) {
            long currentTime = System.currentTimeMillis();
            return (currentTime - lastActivatedTime >= 300000);
        }
        lastActivatedTime = System.currentTimeMillis();
        return true;
    }
    public abstract void activate(GameManager gameManager);

    public String getName() {
        return name;
    }

    public String getStatus() {
        if (activated) {
            return "on";
        }
        return "off";
    }
    public abstract int getPrice();
    public abstract boolean isUnlocked();
    public abstract boolean isPicked();
    public abstract void setUnlocked(boolean b, GameManager gameManager);
    public abstract void setPicked(boolean b);
}
