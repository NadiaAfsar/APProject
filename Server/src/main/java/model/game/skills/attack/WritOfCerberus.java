package model.game.skills.attack;

import application.MyApplication;
import controller.Controller;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;
import model.interfaces.movement.RotatablePoint;

import java.util.ArrayList;

public class WritOfCerberus extends Skill {
    private static boolean cerberusUnlocked;
    private static boolean picked;
    private int price = 1500;

    public WritOfCerberus() {
        name = "Writ Of Cerberus";
    }

    @Override
    public void activate(GameManager gameManager) {
//        if (isTimeToActivate()) {
//            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
//            if (epsilon.getXP() >= 100) {
//                gameManager.getGameModel().setAstarpe(gameManager.getGameModel().getAstarpe() + 2);
//                epsilon.setXP(epsilon.getXP() - 100);
//                addCerberuses(gameManager);
//                activated = true;
//            }
//        }
    }

    private void addCerberuses(EpsilonModel epsilon) {
//        ArrayList<RotatablePoint> cerberusList = new ArrayList<>();
//        int cerberus = gameManager.getGameModel().getCerberuses()+3;
//        gameManager.getGameModel().setCerberuses(cerberus);
//        double angle = 2 * Math.PI / cerberus;
//        for (int i = 0; i < cerberus; i++) {
//            cerberusList.add(new RotatablePoint(epsilon.getCenter().getX(), epsilon.getCenter().getY(), angle * i - Math.PI / 2,
//                    epsilon.getRadius() + 20));
//        }
//        epsilon.setCerberusList(cerberusList);
//        Controller.addCerberusView(epsilon.getGameManager(), epsilon);
    }

    public boolean isUnlocked() {
        return cerberusUnlocked;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setUnlocked(boolean cerberusUnlocked, GameManager gameManager) {
        WritOfCerberus.cerberusUnlocked = cerberusUnlocked;
        if (cerberusUnlocked){
            gameManager.getUnlockedSkills().add(new WritOfCerberus());
        }
        MyApplication.configs.WritOfCerberusUnlocked = cerberusUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfCerberus.picked = picked;
        MyApplication.configs.WritOfCerberusPicked = picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        cerberusUnlocked = MyApplication.configs.WritOfCerberusUnlocked;
        picked = MyApplication.configs.WritOfCerberusPicked;
    }
}
