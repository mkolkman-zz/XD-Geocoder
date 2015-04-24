package core.learning.features;

import core.language.dictionary.Dictionary;
import core.language.word.Word;

public class WordFrequency extends Feature {


    public WordFrequency(Word word, Dictionary dictionary) {
        this.value = dictionary.getMentionCount(word.getText());
    }

    @Override
    public String getName() {
        return "WordFrequency";
    }
}
