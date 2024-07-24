package model.skills.transform;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;
import model.skills.attack.WritOfAres;

public class WritOfEmpusa extends Skill {
    private static boolean empusaUnlocked;
    private static boolean picked;

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

    public static boolean isEmpusaUnlocked() {
        return empusaUnlocked;
    }

    public static void setEmpusaUnlocked(boolean empusaUnlocked) {
        WritOfEmpusa.empusaUnlocked = empusaUnlocked;
        if (empusaUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfEmpusa());
        }
    }

    public static void setPicked(boolean picked) {
        WritOfEmpusa.picked = picked;
    }

    public static boolean isPicked() {
        return picked;
    }
}
