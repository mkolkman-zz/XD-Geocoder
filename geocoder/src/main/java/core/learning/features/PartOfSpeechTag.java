package core.learning.features;

import core.language.word.Word;

public class PartOfSpeechTag extends Feature {
    public PartOfSpeechTag(Word word) {
        this.value = word.getPosTag().ordinal();
    }

    @Override
    public String getName() {
        return "PartOfSpeechTag";
    }
}
