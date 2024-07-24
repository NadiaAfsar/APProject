package model.skills.transform;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;
import model.skills.attack.WritOfAres;

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
    }
    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean p) {
        picked = p;
    }

    public int getPrice() {
        return price;
    }
}
