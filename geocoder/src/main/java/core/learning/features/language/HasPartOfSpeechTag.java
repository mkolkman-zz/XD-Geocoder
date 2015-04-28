package core.learning.features.language;

import core.learning.features.Feature;
import core.language.pos.PosTag;
import core.language.word.Word;

public class HasPartOfSpeechTag extends Feature {

    private final PosTag expected;

    public HasPartOfSpeechTag(Word word, PosTag expected) {
        this.expected = expected;
        this.value = (float) (word.getPosTag() == expected ? 1.0 : 0.0);
    }

    public String getName() {
        return "HasPartOfSpeechTag_" + expected.name();
    }
}
