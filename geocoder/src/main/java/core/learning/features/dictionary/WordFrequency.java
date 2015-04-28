package core.learning.features.dictionary;

import core.language.dictionary.Dictionary;
import core.language.word.Word;
import core.learning.features.Feature;

public class WordFrequency extends Feature {


    public WordFrequency(Word word, Dictionary dictionary) {
        this.value = dictionary.getMentionCount(word.getText());
    }

    @Override
    public String getName() {
        return "WordFrequency";
    }
}
