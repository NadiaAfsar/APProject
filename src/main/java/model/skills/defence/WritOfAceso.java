package model.skills.defence;

import controller.GameManager;
import model.EpsilonModel;
import model.game.GameModel;
import model.skills.Skill;

public class WritOfAceso extends Skill {
    private long lastTimeAdded;
    private static boolean acesoUnlocked;
    private static boolean picked;
    private int HPtoIncrease;
    public WritOfAceso() {
        name = "Writ Of Aceso";
    }
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                activated = true;
                epsilon.setXP(epsilon.getXP() - 100);
                HPtoIncrease++;
            }
        }
    }
    public void increaseHP() {
        if (activated) {
            long currentTime = System.currentTimeMillis();
            if (currentTime-lastTimeAdded >= 1000) {
                EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
                epsilon.setHP(epsilon.getHP() + HPtoIncrease);
                lastTimeAdded = currentTime;
            }
        }
    }
    public static boolean isAcesoUnlocked() {
        return acesoUnlocked;
    }

    public static void setAcesoUnlocked(boolean u) {
        acesoUnlocked = u;
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
