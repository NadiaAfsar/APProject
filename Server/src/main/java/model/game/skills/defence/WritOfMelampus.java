package model.game.skills.defence;

import application.MyApplication;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

public class WritOfMelampus extends Skill {
    private static boolean melampusUnlocked;
    private static boolean picked;
    private int price = 750;
    public WritOfMelampus(){
        name = "Writ Of Melampus";
    }
    @Override
    public void activate(GameManager gameManager) {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP() - 100);
                gameManager.getGameModel().setMelampus(gameManager.getGameModel().getMelampus()+5);
                activated = true;
            }
        }
    }

    public boolean isUnlocked() {
        return melampusUnlocked;
    }

    public boolean isPicked() {
        return picked;
    }
    public static boolean damage(GameManager gameManager){
        int damage = (int)(Math.random()*100);
        return (damage < 100-gameManager.getGameModel().getMelampus());
    }

    public void setUnlocked(boolean melampusUnlocked, GameManager gameManager) {
        WritOfMelampus.melampusUnlocked = melampusUnlocked;
        if (melampusUnlocked){
            gameManager.getUnlockedSkills().add(new WritOfMelampus());
        }
        MyApplication.configs.WritOfMelampusUnlocked = melampusUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfMelampus.picked = picked;
        MyApplication.configs.WritOfMelampusPicked = picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        melampusUnlocked = MyApplication.configs.WritOfMelampusUnlocked;
        picked = MyApplication.configs.WritOfMelampusPicked;
    }
}
