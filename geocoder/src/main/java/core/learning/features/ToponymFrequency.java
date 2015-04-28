package core.learning.features;

import core.language.dictionary.Dictionary;
import core.language.word.Word;

public class ToponymFrequency extends Feature {

    public ToponymFrequency(Word word, Dictionary dictionary) {
        this.value = dictionary.getToponymCount(word.getText());
    }

    @Override
    public String getName() {
        return "ToponymFrequency";
    }
}
