package model.skills;

import model.EpsilonModel;

public class WritOfProteus extends Skill{
    @Override
    public void activate() {
        if (isTimeToActivate()) {
            EpsilonModel.getINSTANCE().addVertex();
        }
    }
}
