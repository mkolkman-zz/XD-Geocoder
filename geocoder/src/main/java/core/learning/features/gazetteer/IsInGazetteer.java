package core.learning.features.gazetteer;

import core.learning.features.Feature;
import core.geo.LocationGazetteer;
import core.language.word.Word;

public class IsInGazetteer extends Feature {

    private Word word;
    private LocationGazetteer gazetteer;

    public IsInGazetteer(Word word, LocationGazetteer gazetteer) {
        this.value = (float) (gazetteer.contains(word.getText()) ? 1.0 : 0.0);
    }
}
