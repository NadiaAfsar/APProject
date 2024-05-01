package model.skills;

import model.GameManager;

public abstract class Skill {
    private static boolean picked;
    private static boolean unlocked;
    private long lastActivatedTime;
    public Skill() {

    }

    public static boolean isPicked() {
        return picked;
    }

    public static void setPicked(boolean p) {
        picked = p;
    }

    public static boolean isUnlocked() {
        return unlocked;
    }

    public static void setUnlocked(boolean u) {
        unlocked = u;
    }
    protected boolean isTimeToActivate() {
        if (lastActivatedTime != 0) {
            long currentTime = System.currentTimeMillis();
            return (currentTime - lastActivatedTime <= 300000);
        }
        else {
            lastActivatedTime = System.currentTimeMillis();
            return true;
        }
    }
    public abstract void activate();
}
