package model.skills.attack;

import controller.GameManager;
import model.EpsilonModel;
import model.game.GameModel;
import model.skills.Skill;

public class WritOfAres extends Skill {
    private static boolean aresUnlocked;
    private static boolean picked;
    private int price = 750;

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
    public boolean isUnlocked() {
        return aresUnlocked;
    }

    public void setUnlocked(boolean u) {
        aresUnlocked = u;
        if (u){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfAres());
        }
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
