package controller.save;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class MyExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return ("clientEpsilon".equals(fieldAttributes.getName()) || "viewGameManager".equals(fieldAttributes.getName())
        || "gamePanels".equals(fieldAttributes.getName()) || "gamePanelMap".equals(fieldAttributes.getName()) ||
                "initialAngle".equals(fieldAttributes.getName()) || "image".equals(fieldAttributes.getName()) ||
                "rotatedImage".equals(fieldAttributes.getName()));
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
