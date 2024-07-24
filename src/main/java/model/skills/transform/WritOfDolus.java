package model.skills.transform;

import controller.GameManager;
import model.EpsilonModel;
import model.skills.Skill;
import model.skills.attack.WritOfAres;

import java.util.ArrayList;


public class WritOfDolus extends Skill {
    private static boolean dolusUnlocked;
    private static boolean picked;
    private int price = 1500;
    private ArrayList<Skill> skills;
    private int chosenSkill;

    public WritOfDolus() {
        name = "Writ Of Dolus";
    }
    public void pickSkills(){
        skills = new ArrayList<>();
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

    public boolean isUnlocked() {
        return dolusUnlocked;
    }

    public void setUnlocked(boolean dolusUnlocked) {
        WritOfDolus.dolusUnlocked = dolusUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfDolus.picked = picked;
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
