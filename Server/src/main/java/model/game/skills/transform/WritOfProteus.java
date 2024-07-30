package model.game.skills.transform;

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
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
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

    public void setUnlocked(boolean u) {
        proteusUnlocked = u;
        if (u){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfProteus());
        }
        GameManager.configs.WritOfEmpusaUnlocked = u;
    }
    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean p) {
        picked = p;
        GameManager.configs.WritOfEmpusaPicked = p;
    }

    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        proteusUnlocked = GameManager.configs.WritOfProteusUnlocked;
        if (proteusUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfProteus());
        }
        picked = GameManager.configs.WritOfProteusPicked;
    }
}
