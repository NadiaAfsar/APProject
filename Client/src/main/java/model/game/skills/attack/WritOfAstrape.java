package model.game.skills.attack;

import application.MyApplication;
import controller.ApplicationManager;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

public class WritOfAstrape extends Skill {
    private static boolean astrapeUnlocked;
    private static boolean picked;
    private int price = 1000;

    public WritOfAstrape() {
        name = "Writ Of Astrape";
    }
    @Override
    public void activate(GameManager gameManager) {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = gameManager.getGameModel().getMyEpsilon();
            if (epsilon.getXP() >= 100) {
                gameManager.getGameModel().setAstarpe(gameManager.getGameModel().getAstarpe()+2);
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

    public void setUnlocked(boolean astrapeUnlocked, ApplicationManager applicationManager) {
        WritOfAstrape.astrapeUnlocked = astrapeUnlocked;
        if (astrapeUnlocked){
            applicationManager.getUnlockedSkills().add(new WritOfAstrape());
        }
        MyApplication.configs.WritOfAstrapeUnlocked = astrapeUnlocked;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        WritOfAstrape.picked = picked;
        MyApplication.configs.WritOfAstrapePicked = picked;
    }
    public static void setBooleans(){
        astrapeUnlocked = MyApplication.configs.WritOfAstrapeUnlocked;
        picked = MyApplication.configs.WritOfAstrapePicked;
    }
}
