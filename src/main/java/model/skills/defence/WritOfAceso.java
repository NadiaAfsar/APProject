package model.skills.defence;

import controller.GameManager;
import model.EpsilonModel;
import model.game.GameModel;
import model.skills.Skill;
import model.skills.attack.WritOfAres;

public class WritOfAceso extends Skill {
    private long lastTimeAdded;
    private static boolean acesoUnlocked;
    private static boolean picked;
    private int price = 500;
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
                GameManager.getINSTANCE().getGameModel().setHPtoIncrease(GameManager.getINSTANCE().getGameModel().getHPtoIncrease()+1);
                activated = true;
            }
        }
    }
    public void increaseHP() {
        if (activated) {
            long currentTime = System.currentTimeMillis();
            if (currentTime-lastTimeAdded >= 1000) {
                EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
                epsilon.setHP(epsilon.getHP() + GameManager.getINSTANCE().getGameModel().getHPtoIncrease());
                lastTimeAdded = currentTime;
            }
        }
    }
    public boolean isUnlocked() {
        return acesoUnlocked;
    }

    public void setUnlocked(boolean u) {
        acesoUnlocked = u;
        if (u){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfAceso());
        }
    }

    public boolean isActivated() {
        return activated;
    }
    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean p) {
        picked = p;
    }

    public int getPrice() {
        return price;
    }
}
