package core.features.language;

import core.features.Feature;

public class HasPrefix extends Feature{

    public HasPrefix(String word, String prefix) {
        this.value = (float) (word.startsWith(prefix) ? 1.0 : 0.0);
    }
}
