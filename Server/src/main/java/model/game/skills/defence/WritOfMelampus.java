package model.game.skills.defence;

import controller.GameManager;
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
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP() - 100);
                GameManager.getINSTANCE().getGameModel().setMelampus(GameManager.getINSTANCE().getGameModel().getMelampus()+5);
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
    public static boolean damage(){
        int damage = (int)(Math.random()*100);
        return (damage < 100-GameManager.getINSTANCE().getGameModel().getMelampus());
    }

    public void setUnlocked(boolean melampusUnlocked) {
        WritOfMelampus.melampusUnlocked = melampusUnlocked;
        if (melampusUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfMelampus());
        }
        GameManager.configs.WritOfMelampusUnlocked = melampusUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfMelampus.picked = picked;
        GameManager.configs.WritOfMelampusPicked = picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        melampusUnlocked = GameManager.configs.WritOfMelampusUnlocked;
        if (melampusUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfMelampus());
        }
        picked = GameManager.configs.WritOfMelampusPicked;
    }
}
