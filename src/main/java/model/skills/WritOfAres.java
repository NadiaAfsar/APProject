package model.skills;

import controller.GameManager;
import model.EpsilonModel;
import model.game.GameModel;

public class WritOfAres extends Skill{
    private static boolean aresUnlocked;
    private static boolean picked;
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                GameManager gameManager = GameManager.getINSTANCE();
                gameManager.setAres(gameManager.getAres() + 2);
                epsilon.setXP(epsilon.getXP()-100);
            }
        }
    }
    public static boolean isAresUnlocked() {
        return aresUnlocked;
    }

    public static void setAresUnlocked(boolean u) {
        aresUnlocked = u;
        if (!u) {
            System.out.println(2);
        }
    }
    public static boolean isPicked() {
        return picked;
    }

    public static void setPicked(boolean p) {
        picked = p;
    }
}
