package core.features.language;

import core.features.Feature;
import core.language.pos.POSTag;

public class HasPartOfSpeechTag extends Feature {

    public HasPartOfSpeechTag(String word, POSTag assigned, POSTag expected) {
        this.value = (float) (assigned == expected ? 1.0 : 0.0);
    }
}
