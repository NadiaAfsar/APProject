package model.skills.attack;

import controller.Controller;
import controller.GameManager;
import model.EpsilonModel;
import model.interfaces.movement.RotatablePoint;
import model.skills.Skill;
import model.skills.transform.WritOfProteus;

import java.util.ArrayList;

public class WritOfCerberus extends Skill {
    private static boolean cerberusUnlocked;
    private static boolean picked;
    private int price = 1500;

    public WritOfCerberus() {
        name = "Writ Of Cerberus";
    }

    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                GameManager.getINSTANCE().getGameModel().setAstarpe(GameManager.getINSTANCE().getGameModel().getAstarpe() + 2);
                epsilon.setXP(epsilon.getXP() - 100);
                addCerberuses();
                activated = true;
            }
        }
    }

    private void addCerberuses() {
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        ArrayList<RotatablePoint> cerberusList = new ArrayList<>();
        int cerberus = GameManager.getINSTANCE().getGameModel().getCerberuses()+3;
        GameManager.getINSTANCE().getGameModel().setCerberuses(cerberus);
        double angle = 2 * Math.PI / cerberus;
        for (int i = 0; i < cerberus; i++) {
            cerberusList.add(new RotatablePoint(epsilon.getCenter().getX(), epsilon.getCenter().getY(), angle * i - Math.PI / 2,
                    epsilon.getRadius() + 20));
        }
        epsilon.setCerberusList(cerberusList);
        Controller.addCerberusView(cerberusList);
    }

    public boolean isUnlocked() {
        return cerberusUnlocked;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setUnlocked(boolean cerberusUnlocked) {
        WritOfCerberus.cerberusUnlocked = cerberusUnlocked;
        if (cerberusUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfCerberus());
        }
        GameManager.configs.WritOfCerberusUnlocked = cerberusUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfCerberus.picked = picked;
        GameManager.configs.WritOfCerberusPicked = picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        cerberusUnlocked = GameManager.configs.WritOfCerberusUnlocked;
        if (cerberusUnlocked){
            GameManager.getINSTANCE().getUnlockedSkills().add(new WritOfCerberus());
        }
        picked = GameManager.configs.WritOfCerberusPicked;
    }
}
