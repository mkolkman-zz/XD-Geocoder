package core.learning.features;

import core.language.dictionary.Dictionary;
import core.language.word.Word;

public class WordFrequency extends Feature {

    private Word word;
    private Dictionary dictionary;

    public WordFrequency(Word word, Dictionary dictionary) {
        this.word = word;
        this.dictionary = dictionary;
        this.value = dictionary.getMentionCount(word.getText());
    }

    @Override
    public String getName() {
        return "WordFrequency";
    }
}
