package core.features.language;

import core.features.Feature;
import core.language.dictionary.Dictionary;

public class IsIthWordInDictionary extends Feature {

    public IsIthWordInDictionary(int i, String word, Dictionary dictionary) {
        this.value = (float) (dictionary.isIthWord(i, word) ? 1.0 : 0.0);
    }
}
