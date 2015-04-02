package core.learning;

import core.language.word.Word;
import core.learning.features.Feature;

import java.util.List;

public class LabelledLearningInstance extends LearningInstance {

    private Word word;

    private List<Feature> features;

    private Label label;

    public LabelledLearningInstance(Word word, List<Feature> features, Label label) {
        super(word, features);
        this.label = label;
    }

    public Word getWord() {
        return word;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public Label getLabel() {
        return label;
    }

    public String toString() {
        return super.toString() + "\t" + label.toString();
    }
}
