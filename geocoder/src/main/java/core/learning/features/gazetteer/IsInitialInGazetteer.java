package core.learning.features.gazetteer;

import core.gazetteer.LocationGazetteer;
import core.language.word.Word;
import core.learning.features.Feature;

public class IsInitialInGazetteer extends Feature {

    public IsInitialInGazetteer(Word word, LocationGazetteer gazetteer) {
        this.value = (float) (gazetteer.containsInitial(word.getText()) ? 1.0 : 0.0);
    }

    @Override
    public String getName() {
        return "IsPartialInGazetteer";
    }
}
