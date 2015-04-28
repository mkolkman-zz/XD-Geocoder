package core.learning.features.dictionary;

import core.language.dictionary.Dictionary;
import core.language.word.Word;
import core.learning.features.Feature;

public class InToponymFrequency extends Feature {

    public InToponymFrequency(Word word, Dictionary dictionary) {
        this.value = dictionary.getInToponymCount(word.getText());
    }

    @Override
    public String getName() {
        return "InToponymFrequency";
    }
}
