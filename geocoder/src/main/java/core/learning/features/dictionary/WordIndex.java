package core.learning.features.dictionary;

import core.language.dictionary.Dictionary;
import core.language.word.Word;
import core.learning.features.Feature;

public class WordIndex extends Feature {
    public WordIndex(Word word, Dictionary dictionary) {
        this.value = dictionary.getRank(word.getText());
    }

    @Override
    public String getName() {
        return "WordIndex";
    }
}
