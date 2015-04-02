package core.learning;

import core.language.word.Word;
import core.learning.features.Feature;

import java.util.List;

public class LearningInstance {

    private Word word;

    private List<Feature> features;

    public LearningInstance(Word word, List<Feature> features) {
        this.word = word;
        this.features = features;
    }

    public String toString() {
        String output = "";
        for(Feature feature : features) {
            output+= feature.toString() + "\t";
        }
        output+= word.toString();
        return output;
    }

}
