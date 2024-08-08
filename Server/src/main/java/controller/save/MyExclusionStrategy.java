package controller.save;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class MyExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return ("runningGame".equals(fieldAttributes.getName()) || "playerNumber".equals(fieldAttributes.getName())
        || "listener".equals(fieldAttributes.getName()));
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
