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
        if (isTimeToActivate()) {
            activated = true;
        }
    }
    public void increaseHP() {
        if (activated) {
            long currentTime = System.currentTimeMillis();
            long spentTime = currentTime - startTime;
            if (spentTime <= 180000) {
                double x = spentTime%1000;
                if (x <= 10) {
                    EpsilonModel epsilon = EpsilonModel.getINSTANCE();
                    epsilon.setHP(epsilon.getHP() + 1);
                }
            } else {
                activated = false;
            }
        }
    }

    public boolean isActivated() {
        return activated;
    }
}
