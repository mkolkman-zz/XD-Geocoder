package core.learning.features.dictionary;

import core.language.dictionary.Dictionary;
import core.language.word.Word;
import core.learning.features.Feature;

public class ToponymFrequency extends Feature {

    public ToponymFrequency(Word word, Dictionary dictionary) {
        this.value = dictionary.getToponymCount(word.getText());
    }

    @Override
    public String getName() {
        return "ToponymFrequency";
    }
}
