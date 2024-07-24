package model.skills.defence;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;

public class WritOfMelampus extends Skill {
    private static boolean melampusUnlocked;
    private static boolean picked;
    public WritOfMelampus(){
        name = "Writ Of Melampus";
    }
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP() - 100);
                GameManager.getINSTANCE().getGameModel().setMelampus(GameManager.getINSTANCE().getGameModel().getMelampus()+5);
            }
        }
    }

    public static boolean isMelampusUnlocked() {
        return melampusUnlocked;
    }

    public static boolean isPicked() {
        return picked;
    }
    public static boolean damage(){
        int damage = (int)(Math.random()*100);
        return (damage < 100-GameManager.getINSTANCE().getGameModel().getMelampus());
    }
}
