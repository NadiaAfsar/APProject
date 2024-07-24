package model.skills.defence;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;
import model.skills.attack.WritOfAres;

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
                activated = true;
            }
        }
    }

    public static boolean isAthenaUnlocked() {
        return athenaUnlocked;
    }

    public static void setAthenaUnlocked(boolean athenaUnlocked) {
        WritOfAthena.athenaUnlocked = athenaUnlocked;
        if (athenaUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfAthena());
        }
    }

    public static void setPicked(boolean picked) {
        WritOfAthena.picked = picked;
    }

    public static boolean isPicked() {
        return picked;
    }
}
