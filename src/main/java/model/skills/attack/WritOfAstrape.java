package model.skills.attack;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;

public class WritOfAstrape extends Skill {
    private static boolean astrapeUnlocked;
    private static boolean picked;
    private int price = 1000;

    public WritOfAstrape() {
        name = "Writ Of Astrape";
    }
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                GameManager.getINSTANCE().getGameModel().setAstarpe(GameManager.getINSTANCE().getGameModel().getAstarpe()+2);
                epsilon.setXP(epsilon.getXP()-100);
                activated = true;
            }
        }
    }

    @Override
    public int getPrice() {
        return price;
    }

    public boolean isUnlocked() {
        return astrapeUnlocked;
    }

    public void setUnlocked(boolean astrapeUnlocked) {
        WritOfAstrape.astrapeUnlocked = astrapeUnlocked;
        if (astrapeUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfAstrape());
        }
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        WritOfAstrape.picked = picked;
    }
}
