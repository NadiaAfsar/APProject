package model.skills.transform;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;
import model.skills.attack.WritOfAres;

public class WritOfEmpusa extends Skill {
    private static boolean empusaUnlocked;
    private static boolean picked;
    private int price = 750;

    public WritOfEmpusa() {
        name = "Writ Of Empusa";
    }

    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP()-100);
                epsilon.setRadius((int)(epsilon.getRadius()*0.9));
                activated = true;
            }
        }
    }

    public boolean isUnlocked() {
        return empusaUnlocked;
    }

    public void setUnlocked(boolean empusaUnlocked) {
        WritOfEmpusa.empusaUnlocked = empusaUnlocked;
        if (empusaUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfEmpusa());
        }
    }

    public void setPicked(boolean picked) {
        WritOfEmpusa.picked = picked;
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
