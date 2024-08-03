package model.game.skills.attack;

import application.MyApplication;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

public class WritOfAres extends Skill {
    private static boolean aresUnlocked;
    private static boolean picked;
    private int price = 750;

    public WritOfAres() {
        name = "Writ Of Ares";
    }

    @Override
    public void activate(GameManager gameManager) {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                gameManager.getGameModel().setAres(gameManager.getGameModel().getAres() + 2);
                epsilon.setXP(epsilon.getXP()-100);
                activated = true;
            }
        }
    }
    public boolean isUnlocked() {
        return aresUnlocked;
    }

    public void setUnlocked(boolean u, GameManager gameManager) {
        aresUnlocked = u;
        if (u){
            gameManager.getUnlockedSkills().add(new WritOfAres());
        }
        MyApplication.configs.WritOfAresUnlocked = u;
    }
    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean p) {
        picked = p;
        MyApplication.configs.WritOfAresPicked = p;
    }

    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        aresUnlocked = MyApplication.configs.WritOfAresUnlocked;
        picked = MyApplication.configs.WritOfAresPicked;
    }
}
