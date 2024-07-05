package model.skills;

import controller.GameManager;
import model.EpsilonModel;
import model.game.GameModel;

public class WritOfAceso extends Skill{
    private final long startTime;
    private boolean activated;
    private static boolean acesoUnlocked;
    private static boolean picked;
    public WritOfAceso() {
        startTime = System.currentTimeMillis();
    }
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                activated = true;
                epsilon.setXP(epsilon.getXP() - 100);
            }
        }
    }
    public void increaseHP() {
        if (activated) {
            long currentTime = System.currentTimeMillis();
            long spentTime = currentTime - startTime;
            if (spentTime <= 180000) {
                double x = spentTime%1000;
                if (x <= 10) {
                    EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
                    epsilon.setHP(epsilon.getHP() + 1);
                }
            } else {
                activated = false;
            }
        }
    }
    public static boolean isAcesoUnlocked() {
        return acesoUnlocked;
    }

    public static void setAcesoUnlocked(boolean u) {
        acesoUnlocked = u;
        if (!u) {
            System.out.println(1);
        }
    }

    public boolean isActivated() {
        return activated;
    }
    public static boolean isPicked() {
        return picked;
    }

    public static void setPicked(boolean p) {
        picked = p;
    }
}
