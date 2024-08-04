package model.game.skills.transform;

import application.MyApplication;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

public class WritOfEmpusa extends Skill {
    private static boolean empusaUnlocked;
    private static boolean picked;
    private int price = 750;

    public WritOfEmpusa() {
        name = "Writ Of Empusa";
    }

    @Override
    public void activate(GameManager gameManager) {
//        if (isTimeToActivate()) {
//            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
//            if (epsilon.getXP() >= 100) {
//                epsilon.setXP(epsilon.getXP()-100);
//                epsilon.setRadius((int)(epsilon.getRadius()*0.9));
//                activated = true;
//            }
//        }
    }

    public boolean isUnlocked() {
        return empusaUnlocked;
    }

    public void setUnlocked(boolean empusaUnlocked, GameManager gameManager) {
        WritOfEmpusa.empusaUnlocked = empusaUnlocked;
        if (empusaUnlocked){
            gameManager.getUnlockedSkills().add(new WritOfEmpusa());
        }
        MyApplication.configs.WritOfEmpusaUnlocked = empusaUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfEmpusa.picked = picked;
        MyApplication.configs.WritOfEmpusaPicked = picked;
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        empusaUnlocked = MyApplication.configs.WritOfEmpusaUnlocked;
        picked = MyApplication.configs.WritOfEmpusaPicked;
    }
}
