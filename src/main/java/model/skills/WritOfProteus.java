package model.skills;

import model.EpsilonModel;

public class WritOfProteus extends Skill{
    private static boolean proteusUnlocked;
    private static boolean picked;
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel.getINSTANCE().addVertex();
        }
    }
    public static boolean isProteusUnlocked() {
        return proteusUnlocked;
    }

    public static void setProteusUnlocked(boolean u) {
        proteusUnlocked = u;
        if (!u) {
            System.out.println(3);
        }
    }
    public static boolean isPicked() {
        return picked;
    }

    public static void setPicked(boolean p) {
        picked = p;
    }
}
