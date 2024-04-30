package model.skills;

public abstract class Skill {
    private static boolean picked;
    private static boolean unlocked;

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
    public abstract void activate();
}
