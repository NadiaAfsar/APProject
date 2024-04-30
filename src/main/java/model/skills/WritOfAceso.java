package model.skills;

import model.EpsilonModel;

public class WritOfAceso extends Skill{
    private final long startTime;
    private boolean activated;
    public WritOfAceso() {
        startTime = System.currentTimeMillis();
    }
    @Override
    public void activate() {
        activated = true;
    }
    public void increaseHP() {
        long currentTime = System.currentTimeMillis();
        long spentTime = currentTime - startTime;
        if (spentTime <= 300000) {
            if (spentTime % 1000 == 0) {
                EpsilonModel epsilon = EpsilonModel.getINSTANCE();
                epsilon.setHP(epsilon.getHP() + 1);
            }
        }
        else {
            activated = false;
        }
    }
}
