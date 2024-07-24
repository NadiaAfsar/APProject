package model.skills.attack;

import controller.GameManager;
import model.EpsilonModel;
import model.game.GameModel;
import model.skills.Skill;

public class WritOfAres extends Skill {
    private static boolean aresUnlocked;
    private static boolean picked;

    public WritOfAres() {
        name = "Writ Of Ares";
    }

    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                GameManager gameManager = GameManager.getINSTANCE();
                gameManager.getGameModel().setAres(gameManager.getGameModel().getAres() + 2);
                epsilon.setXP(epsilon.getXP()-100);
                activated = true;
            }
        }
    }
    public static boolean isAresUnlocked() {
        return aresUnlocked;
    }

    public static void setAresUnlocked(boolean u) {
        aresUnlocked = u;
    }
    public static boolean isPicked() {
        return picked;
    }

    public static void setPicked(boolean p) {
        picked = p;
    }
}
