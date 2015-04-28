package core.learning.features.dictionary;

import core.language.dictionary.Dictionary;
import core.language.word.Word;
import core.learning.features.Feature;

public class InToponymFraction extends Feature {
    public InToponymFraction(Word word, Dictionary dictionary) {
        if(dictionary.getMentionCount(word.getText()) > 0) {
            this.value = (float) (((double) dictionary.getInToponymCount(word.getText())) / dictionary.getMentionCount(word.getText()));
        } else {
            this.value = (float) 0.5;
        }
    }

    @Override
    public String getName() {
        return "InToponymFraction";
    }
}
