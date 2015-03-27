package core.features.language;

import core.features.Feature;

public class IsAllCaps extends Feature {

    public IsAllCaps(String word) {
        this.value = (float) (word.equals(word.toUpperCase()) ? 1.0 : 0.0);
    }

}
