package core.features.language;

import core.features.Feature;

public class HasSuffix extends Feature {

    public HasSuffix(String word, String suffix) {
        this.value = (float) (word.endsWith(suffix) ? 1.0 : 0.0);
    }
}
