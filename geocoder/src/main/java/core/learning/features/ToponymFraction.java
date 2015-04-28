package core.learning.features;

import core.language.dictionary.Dictionary;
import core.language.word.Word;

public class ToponymFraction extends Feature {
    public ToponymFraction(Word word, Dictionary dictionary) {
        this.value = (float) (((double) dictionary.getToponymCount(word.getText())) / dictionary.getMentionCount(word.getText()));
    }

    @Override
    public String getName() {
        return "ToponymFraction";
    }
}
