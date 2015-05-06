package core.learning.learning_instance;

import core.language.word.Word;
import core.learning.features.Feature;
import core.learning.features.FeatureVector;
import core.learning.label.Label;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LearningInstance implements Serializable {

    private Word word;
    private FeatureVector features;
    private Label label;

    private List<Word> sentence;
    private List<FeatureVector> featureSequence;
    private List<Label> labelSequence;

    public LearningInstance(Word word, FeatureVector features) {
        this(word, features, Label.UNKNOWN);
    }

    public LearningInstance(Word word, FeatureVector features, Label label) {
        this.word = word;
        this.features = features;
        this.label = label;
    }

    public LearningInstance(List<Word> sentence, List<FeatureVector> featureSequence) {
        this(sentence, featureSequence, null);
    }

    public LearningInstance(List<Word> sentence, List<FeatureVector> featureSequence, List<Label> labelSequence) {
        this.sentence = sentence;
        this.featureSequence = featureSequence;
        if(labelSequence == null) {
            labelSequence = new ArrayList<Label>();
            Iterator<Word> wordIterator = sentence.iterator();
            while(wordIterator.hasNext()) {
                labelSequence.add(Label.UNKNOWN);
            }
        }
        this.labelSequence = labelSequence;
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

    public List<Word> getSentence() {
        return sentence;
    }

    public List<FeatureVector> getFeatureSequence() {
        return featureSequence;
    }

    public List<Label> getLabelSequence() {
        return labelSequence;
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
