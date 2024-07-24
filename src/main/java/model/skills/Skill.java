package model.skills;


import java.util.ArrayList;

public abstract class Skill {
    private long lastActivatedTime;
    protected String name;
    protected boolean activated;
    public Skill() {

    }


    protected boolean isTimeToActivate() {
//        if (lastActivatedTime != 0) {
//            long currentTime = System.currentTimeMillis();
//            return (currentTime - lastActivatedTime >= 300000);
//        }
//        else {
//            lastActivatedTime = System.currentTimeMillis();
//            return true;
//        }
        return true;
    }
    public abstract void activate();

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
    public abstract void setUnlocked(boolean b);
    public abstract void setPicked(boolean b);
}
