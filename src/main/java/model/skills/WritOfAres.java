package model.skills;

import model.EpsilonModel;
import model.game.GameModel;

public class WritOfAres extends Skill{
    private static boolean aresUnlocked;
    private static boolean picked;
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = EpsilonModel.getINSTANCE();
            if (epsilon.getXP() >= 100) {
                GameModel gameModel = GameModel.getINSTANCE();
                gameModel.setAres(gameModel.getAres() + 2);
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
