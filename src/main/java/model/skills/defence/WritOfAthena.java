package model.skills.defence;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;

public class WritOfAthena extends Skill {
    private static boolean athenaUnlocked;
    private static boolean picked;

    public WritOfAthena() {
        name = "Writ Of Chiron";
    }

    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP() - 100);
                GameManager.getINSTANCE().getGameModel().setWritOfAthena(GameManager.getINSTANCE().getGameModel().
                        getWritOfAthena()*0.8);
            }
        }
    }

    public static boolean isAthenaUnlocked() {
        return athenaUnlocked;
    }

    public static boolean isPicked() {
        return picked;
    }
}
