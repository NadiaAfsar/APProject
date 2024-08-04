package model.game.skills.defence;

import application.MyApplication;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

public class WritOfAthena extends Skill {
    private static boolean athenaUnlocked;
    private static boolean picked;
    private int price = 1200;

    public WritOfAthena() {
        name = "Writ Of Athena";
    }

    @Override
    public void activate(GameManager gameManager) {
//        if (isTimeToActivate()) {
//            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
//            if (epsilon.getXP() >= 100) {
//                epsilon.setXP(epsilon.getXP() - 100);
//                gameManager.getGameModel().setWritOfAthena(gameManager.getGameModel().
//                        getWritOfAthena()*0.8);
//                activated = true;
//            }
//        }
    }

    public boolean isUnlocked() {
        return athenaUnlocked;
    }

    public void setUnlocked(boolean athenaUnlocked, GameManager gameManager) {
        WritOfAthena.athenaUnlocked = athenaUnlocked;
        if (athenaUnlocked){
            gameManager.getUnlockedSkills().add(new WritOfAthena());
        }
        MyApplication.configs.WritOfAthenaUnlocked = athenaUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfAthena.picked = picked;
        MyApplication.configs.WritOfAthenaPicked = picked;
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        athenaUnlocked = MyApplication.configs.WritOfAthenaUnlocked;
        picked = MyApplication.configs.WritOfAthenaPicked;
    }
}
