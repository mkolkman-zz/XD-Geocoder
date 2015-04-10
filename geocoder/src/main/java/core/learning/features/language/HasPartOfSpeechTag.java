package core.learning.features.language;

import core.learning.features.Feature;
import core.language.pos.PosTag;
import core.language.word.Word;

public class HasPartOfSpeechTag extends Feature {

    private final Word word;
    private final PosTag assigned;
    private final PosTag expected;

    public HasPartOfSpeechTag(Word word, PosTag assigned, PosTag expected) {
        this.word = word;
        this.assigned = assigned;
        this.expected = expected;
        this.value = (float) (assigned == expected ? 1.0 : 0.0);
    }

    public String getName() {
        return "HasPartOfSpeechTag_" + expected.name();
    }
}
