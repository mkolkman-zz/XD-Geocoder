package core.learning.features.language;

import core.learning.features.Feature;
import core.language.word.Word;

public class HasPrefix extends Feature{

    public HasPrefix(Word word, String prefix) {
        this.value = (float) (word.getText().startsWith(prefix) ? 1.0 : 0.0);
    }
}
