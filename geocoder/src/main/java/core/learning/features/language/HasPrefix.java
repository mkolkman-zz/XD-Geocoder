package core.learning.features.language;

import core.learning.features.Feature;
import core.language.word.Word;

public class HasPrefix extends Feature{

    private final Word word;
    private final String prefix;

    public HasPrefix(Word word, String prefix) {
        this.word = word;
        this.prefix = prefix;
        this.value = (float) (word.getText().startsWith(prefix) ? 1.0 : 0.0);
    }

    @Override
    public String getName() {
        return "HasPrefix_" + prefix;
    }
}
