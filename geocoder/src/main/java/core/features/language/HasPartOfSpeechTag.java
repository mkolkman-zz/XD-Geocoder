package core.features.language;

import core.features.Feature;
import core.language.pos.PosTag;
import core.language.word.Word;

public class HasPartOfSpeechTag extends Feature {

    public HasPartOfSpeechTag(Word word, PosTag assigned, PosTag expected) {
        this.value = (float) (assigned == expected ? 1.0 : 0.0);
    }
}
