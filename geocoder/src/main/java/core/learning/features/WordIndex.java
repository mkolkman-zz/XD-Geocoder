package core.learning.features;

import core.language.dictionary.Dictionary;
import core.language.word.Word;

public class WordIndex extends Feature {
    public WordIndex(Word word, Dictionary dictionary) {
        this.value = dictionary.getRank(word.getText());
    }

    @Override
    public String getName() {
        return "WordIndex";
    }
}
