package core.learning.features.dictionary;

import core.language.dictionary.Dictionary;
import core.language.word.Word;
import core.learning.features.Feature;

public class UppercaseFrequency extends Feature {
    public UppercaseFrequency(Word word, Dictionary dictionary) {
        this.value = dictionary.getUppercaseCount(word.getText());
    }

    @Override
    public String getName() {
        return "UppercaseFrequency";
    }
}
