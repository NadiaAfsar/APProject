package model.skills.defence;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;
import model.skills.attack.WritOfAres;
import model.skills.transform.WritOfProteus;

public class WritOfAthena extends Skill {
    private static boolean athenaUnlocked;
    private static boolean picked;
    private int price = 1200;

    public WritOfAthena() {
        name = "Writ Of Athena";
    }

    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP() - 100);
                GameManager.getINSTANCE().getGameModel().setWritOfAthena(GameManager.getINSTANCE().getGameModel().
                        getWritOfAthena()*0.8);
                activated = true;
            }
        }
    }

    public boolean isUnlocked() {
        return athenaUnlocked;
    }

    public void setUnlocked(boolean athenaUnlocked) {
        WritOfAthena.athenaUnlocked = athenaUnlocked;
        if (athenaUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfAthena());
        }
        GameManager.configs.WritOfAthenaUnlocked = athenaUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfAthena.picked = picked;
        GameManager.configs.WritOfAthenaPicked = picked;
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        athenaUnlocked = GameManager.configs.WritOfAthenaUnlocked;
        if (athenaUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfAthena());
        }
        picked = GameManager.configs.WritOfAthenaPicked;
    }
}
