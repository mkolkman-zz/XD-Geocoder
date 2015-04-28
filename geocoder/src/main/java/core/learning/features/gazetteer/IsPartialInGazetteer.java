package core.learning.features.gazetteer;

import core.geo.LocationGazetteer;
import core.language.word.Word;
import core.learning.features.Feature;

public class IsPartialInGazetteer extends Feature {

    public IsPartialInGazetteer(Word word, LocationGazetteer gazetteer) {
        this.value = (float) (gazetteer.containsPartial(word.getText()) ? 1.0 : 0.0);
    }

    @Override
    public String getName() {
        return "IsPartialInGazetteer";
    }
}
