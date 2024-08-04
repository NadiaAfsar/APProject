package model.game.skills.transform;

import application.MyApplication;
import controller.game_manager.GameManager;
import model.game.EpsilonModel;
import model.game.skills.Skill;

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
    public void pickSkills(GameManager gameManager){
        skills = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            skills.add(chooseSkill(gameManager));
        }
    }

    @Override
    public void activate(GameManager gameManager) {
//        if (isTimeToActivate()) {
//            EpsilonModel epsilon = gameManager.getGameModel().getEpsilon();
//            if (epsilon.getXP() >= 100) {
//                epsilon.setXP(epsilon.getXP()-100);
//                for (int i = 0; i < 2; i++){
//                    skills.get(i).activate(gameManager);
//                }
//                activated = true;
//            }
//        }
    }
    private Skill chooseSkill(GameManager gameManager) {
        int skill = (int)(Math.random()*gameManager.getUnlockedSkills().size())+1;
        if (skill != chosenSkill){
            chosenSkill = skill;
            return gameManager.getUnlockedSkills().get(skill-1);
        }
        return chooseSkill(gameManager);
    }

    public boolean isUnlocked() {
        return dolusUnlocked;
    }

    public void setUnlocked(boolean dolusUnlocked, GameManager gameManager) {
        WritOfDolus.dolusUnlocked = dolusUnlocked;
        MyApplication.configs.WritOfDolusUnlocked = dolusUnlocked;
    }

    public void setPicked(boolean picked) {
        WritOfDolus.picked = picked;
        MyApplication.configs.WritOfDolusPicked = picked;
    }

    public boolean isPicked() {
        return picked;
    }

    @Override
    public int getPrice() {
        return price;
    }
    public static void setBooleans(){
        dolusUnlocked = MyApplication.configs.WritOfDolusUnlocked;
        picked = MyApplication.configs.WritOfDolusPicked;
    }
}
