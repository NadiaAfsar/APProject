package model.skills;

import model.EpsilonModel;

public class WritOfProteus extends Skill{
    private static boolean proteusUnlocked;
    private static boolean picked;
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = EpsilonModel.getINSTANCE();
            if (epsilon.getXP() >= 100) {
                EpsilonModel.getINSTANCE().addVertex();
                epsilon.setXP(epsilon.getXP()-100);
            }
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
