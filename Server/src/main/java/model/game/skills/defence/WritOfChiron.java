package model.game.skills.defence;

import controller.GameManager;
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
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP() - 100);
                GameManager.getINSTANCE().getGameModel().setChiron(GameManager.getINSTANCE().getGameModel().getChiron()+3);
                activated = true;
            }
        }
    }

    public boolean isUnlocked() {
        return chironUnlocked;
    }

    public void setUnlocked(boolean chironUnlocked) {
        WritOfChiron.chironUnlocked = chironUnlocked;
        if (chironUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfChiron());
        }
        GameManager.configs.WritOfChironUnlocked = chironUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfChiron.picked = picked;
        GameManager.configs.WritOfChironPicked = picked;
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        chironUnlocked = GameManager.configs.WritOfChironUnlocked;
        if (chironUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfChiron());
        }
        picked = GameManager.configs.WritOfChironPicked;
    }
}
