package model.skills.attack;

import controller.Controller;
import controller.GameManager;
import model.EpsilonModel;
import model.interfaces.movement.RotatablePoint;
import model.skills.Skill;

import java.util.ArrayList;

public class WritOfCerberus extends Skill {
    private static boolean cerberusUnlocked;
    private static boolean picked;
    private long lastAttack;
    private int cerberuses;

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
            }
        }
    }

    private void addCerberuses() {
        EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
        ArrayList<RotatablePoint> cerberusList = new ArrayList<>();
        cerberuses += 3;
        double angle = 2 * Math.PI / cerberuses;
        for (int i = 0; i < cerberuses; i++) {
            cerberusList.add(new RotatablePoint(epsilon.getCenter().getX(), epsilon.getCenter().getY(), angle * i - Math.PI / 2,
                    epsilon.getRadius() + 20));
        }
        epsilon.setCerberusList(cerberusList);
        Controller.addCerberusView(cerberusList);
    }

    public static boolean isCerberusUnlocked() {
        return cerberusUnlocked;
    }

    public static boolean isPicked() {
        return picked;
    }
}
