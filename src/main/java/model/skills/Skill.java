package model.skills;


public abstract class Skill {
    private long lastActivatedTime;
    public Skill() {

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
