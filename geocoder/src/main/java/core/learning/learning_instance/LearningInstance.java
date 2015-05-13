package core.learning.learning_instance;

import core.language.word.Word;
import core.learning.features.Feature;
import core.learning.features.FeatureVector;
import core.learning.label.Label;

import java.io.Serializable;

public class LearningInstance implements Serializable {

    private Word word;
    private FeatureVector features;
    private Label label;

    public LearningInstance(Word word, FeatureVector features) {
        this(word, features, Label.UNKNOWN);
    }

    public LearningInstance(Word word, FeatureVector features, Label label) {
        this.word = word;
        this.features = features;
        this.label = label;
    }

    public Word getWord() {
        return word;
    }

    public FeatureVector getFeatures() {
        return features;
    }

    public Label getLabel() {
        return label;
    }

    public String toString() {
        String output = "";
        for(Feature feature : features.getFeatureList()) {
            output+= feature.toString() + "\t";
        }
        output+= word.toString() + "\t";
        output+= label.toString();

        return output;
    }

}
