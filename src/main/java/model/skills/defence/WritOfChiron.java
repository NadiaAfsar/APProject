package model.skills.defence;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;

public class WritOfChiron extends Skill {
    private static boolean chironUnlocked;
    private static boolean picked;
    public WritOfChiron(){
        name = "Writ Of Chiron";
    }
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP() - 100);
                GameManager.getINSTANCE().getGameModel().setChiron(GameManager.getINSTANCE().getGameModel().getChiron()+3);
            }
        }
    }

    public static boolean isChironUnlocked() {
        return chironUnlocked;
    }

    public static boolean isPicked() {
        return picked;
    }
}
