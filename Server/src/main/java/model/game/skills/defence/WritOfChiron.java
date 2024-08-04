package model.game.skills.defence;

import application.MyApplication;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

public class WritOfChiron extends Skill {
    private static boolean chironUnlocked;
    private static boolean picked;
    private int price = 900;
    public WritOfChiron(){
        name = "Writ Of Chiron";
    }
    @Override
    public void activate(GameManager gameManager) {
//        if (isTimeToActivate()) {
//            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
//            if (epsilon.getXP() >= 100) {
//                epsilon.setXP(epsilon.getXP() - 100);
//                gameManager.getGameModel().setChiron(gameManager.getGameModel().getChiron()+3);
//                activated = true;
//            }
//        }
    }

    public boolean isUnlocked() {
        return chironUnlocked;
    }

    public void setUnlocked(boolean chironUnlocked, GameManager gameManager) {
        WritOfChiron.chironUnlocked = chironUnlocked;
        if (chironUnlocked){
            gameManager.getUnlockedSkills().add(new WritOfChiron());
        }
        MyApplication.configs.WritOfChironUnlocked = chironUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfChiron.picked = picked;
        MyApplication.configs.WritOfChironPicked = picked;
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        chironUnlocked = MyApplication.configs.WritOfChironUnlocked;
        picked = MyApplication.configs.WritOfChironPicked;
    }
}
