package model.skills.attack;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;

public class WritOfAstrape extends Skill {
    private static boolean astrapeUnlocked;
    private static boolean picked;
    public WritOfAstrape() {
        name = "Writ Of Astrape";
    }
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                GameManager.getINSTANCE().getGameModel().setAstarpe(GameManager.getINSTANCE().getGameModel().getAstarpe()+2);
                epsilon.setXP(epsilon.getXP()-100);
            }
        }
    }

    public static boolean isAstrapeUnlocked() {
        return astrapeUnlocked;
    }

    public static void setAstrapeUnlocked(boolean astrapeUnlocked) {
        WritOfAstrape.astrapeUnlocked = astrapeUnlocked;
    }

    public static boolean isPicked() {
        return picked;
    }

    public static void setPicked(boolean picked) {
        WritOfAstrape.picked = picked;
    }
}
