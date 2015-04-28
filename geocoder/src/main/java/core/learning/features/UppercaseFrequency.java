package core.learning.features;

import core.language.dictionary.Dictionary;
import core.language.word.Word;

public class UppercaseFrequency extends Feature {
    public UppercaseFrequency(Word word, Dictionary dictionary) {
        this.value = dictionary.getUppercaseCount(word.getText());
    }

    @Override
    public String getName() {
        return "UppercaseFrequency";
    }
}
