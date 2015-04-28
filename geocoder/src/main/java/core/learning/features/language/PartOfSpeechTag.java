package core.learning.features.language;

import core.language.word.Word;
import core.learning.features.Feature;

public class PartOfSpeechTag extends Feature {
    public PartOfSpeechTag(Word word) {
        this.value = word.getPosTag().ordinal();
    }

    @Override
    public String getName() {
        return "PartOfSpeechTag";
    }
}
