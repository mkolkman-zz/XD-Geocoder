package core.learning.features;

import core.language.dictionary.Dictionary;
import core.language.word.Word;

public class UppercaseFraction extends Feature {
    public UppercaseFraction(Word word, Dictionary dictionary) {
        this.value = (float) (((double) dictionary.getUppercaseCount(word.getText())) / dictionary.getMentionCount(word.getText()));
    }

    @Override
    public String getName() {
        return "UppercaseFraction";
    }
}
