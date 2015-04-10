package core.learning.features.language;

import core.learning.features.Feature;
import core.language.word.Word;

public class IsAllCaps extends Feature {

    public IsAllCaps(Word word) {
        this.value = (float) (word.getText().equals(word.getText().toUpperCase()) ? 1.0 : 0.0);
    }

    @Override
    public String getName() {
        return "IsAllCaps";
    }
}
