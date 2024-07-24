package model.skills.transform;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;
import model.skills.attack.WritOfAres;

import java.util.ArrayList;


public class WritOfDolus extends Skill {
    private static boolean dolusUnlocked;
    private static boolean picked;
    private ArrayList<Skill> skills;
    private int chosenSkill;

    public WritOfDolus() {
        name = "Writ Of Dolus";
        for (int i = 0; i < 2; i++){
            skills.add(chooseSkill());
        }
    }

    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel epsilon = GameManager.getINSTANCE().getGameModel().getEpsilon();
            if (epsilon.getXP() >= 100) {
                epsilon.setXP(epsilon.getXP()-100);
                for (int i = 0; i < 2; i++){
                    skills.get(i).activate();
                }
                activated = true;
            }
        }
    }
    private Skill chooseSkill() {
        int skill = (int)(Math.random()*GameManager.getINSTANCE().getUnlockedSkills().size())+1;
        if (skill != chosenSkill){
            chosenSkill = skill;
            return GameManager.getINSTANCE().getUnlockedSkills().get(skill-1);
        }
        return chooseSkill();
    }

    public static boolean isDolusUnlocked() {
        return dolusUnlocked;
    }

    public static boolean isPicked() {
        return picked;
    }

}
