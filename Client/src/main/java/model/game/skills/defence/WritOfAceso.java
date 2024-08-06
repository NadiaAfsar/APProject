package model.game.skills.defence;

import application.MyApplication;
import controller.ApplicationManager;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

public class WritOfAceso extends Skill {
    private long lastTimeAdded;
    private static boolean acesoUnlocked;
    private static boolean picked;
    private int price = 500;
    public WritOfAceso() {
        name = "Writ Of Aceso";
    }
    @Override
    public void activate(GameManager gameManager) {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = gameManager.getGameModel().getMyEpsilon();
            if (epsilon.getXP() >= 100) {
                activated = true;
                epsilon.setXP(epsilon.getXP() - 100);
                gameManager.getGameModel().setHPtoIncrease(gameManager.getGameModel().getHPtoIncrease()+1);
                activated = true;
            }
        }
    }
    public void increaseHP(GameManager gameManager) {
        if (activated) {
            long currentTime = System.currentTimeMillis();
            if (currentTime-lastTimeAdded >= 1000) {
                EpsilonModel epsilon = gameManager.getGameModel().getMyEpsilon();
                epsilon.setHP(epsilon.getHP() + gameManager.getGameModel().getHPtoIncrease());
                lastTimeAdded = currentTime;
            }
        }
    }
    public boolean isUnlocked() {
        return acesoUnlocked;
    }

    public void setUnlocked(boolean u, ApplicationManager applicationManager) {
        acesoUnlocked = u;
        if (u){
            applicationManager.getUnlockedSkills().add(new WritOfAceso());
        }
        MyApplication.configs.WritOfAcesoUnlocked = u;
    }

    public boolean isActivated() {
        return activated;
    }
    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean p) {
        picked = p;
        MyApplication.configs.WritOfAcesoPicked = p;
    }

    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        acesoUnlocked = MyApplication.configs.WritOfAcesoUnlocked;
        picked = MyApplication.configs.WritOfAcesoPicked;
    }
}
