package core.learning.features.dictionary;

import core.language.dictionary.Dictionary;
import core.language.word.Word;
import core.learning.features.Feature;

public class WordFraction extends Feature {

    public WordFraction(Word word, Dictionary dictionary) {
        this.value = (float) (((double) dictionary.getMentionCount(word.getText())) / dictionary.getWordCount());
    }

    @Override
    public String getName() {
        return "WordFraction";
    }
}
