package core.learning.features.language;

import core.learning.features.Feature;
import core.language.word.Word;

public class IsInitCap extends Feature {

    public IsInitCap(Word word) {
        boolean isCapitalized = Character.isUpperCase(word.getText().charAt(0));
        this.value = (float) (isCapitalized ? 1.0 : 0.0);
    }

    @Override
    public String getName() {
        return "IsInitCap";
    }
}
