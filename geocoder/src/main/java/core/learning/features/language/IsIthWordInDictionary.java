package core.learning.features.language;

import core.learning.features.Feature;
import core.language.dictionary.Dictionary;
import core.language.word.Word;

public class IsIthWordInDictionary extends Feature {

    public IsIthWordInDictionary(int i, Word word, Dictionary dictionary) {
        this.value = (float) (dictionary.isIthWord(i, word.getText()) ? 1.0 : 0.0);
    }
}
