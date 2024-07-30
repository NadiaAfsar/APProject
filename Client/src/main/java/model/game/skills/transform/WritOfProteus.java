package model.game.skills.transform;

import application.MyApplication;
import controller.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

public class WritOfProteus extends Skill {
    private static boolean proteusUnlocked;
    private static boolean picked;
    private int price = 1000;

    public WritOfProteus() {
        name = "Writ Of Proteus";
    }

    @Override
    public void activate(GameManager gameManager) {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.addVertex();
                epsilon.setXP(epsilon.getXP()-100);
                activated = true;
            }
        }
    }
    public boolean isUnlocked() {
        return proteusUnlocked;
    }

    public void setUnlocked(boolean u, GameManager gameManager) {
        proteusUnlocked = u;
        if (u){
            gameManager.getUnlockedSkills().add(new WritOfProteus());
        }
        MyApplication.configs.WritOfEmpusaUnlocked = u;
    }
    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean p) {
        picked = p;
        MyApplication.configs.WritOfEmpusaPicked = p;
    }

    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        proteusUnlocked = MyApplication.configs.WritOfProteusUnlocked;
        picked = MyApplication.configs.WritOfProteusPicked;
    }
}
