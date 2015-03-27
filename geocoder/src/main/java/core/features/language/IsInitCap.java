package core.features.language;

import core.features.Feature;

public class IsInitCap extends Feature {

    public IsInitCap(String word) {
        boolean isCapitalized = Character.isUpperCase(word.charAt(0));
        this.value = (float) (isCapitalized ? 1.0 : 0.0);
    }

}
