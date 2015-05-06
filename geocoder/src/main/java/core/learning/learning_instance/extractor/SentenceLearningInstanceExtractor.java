package core.learning.learning_instance.extractor;

import core.language.word.DummyWord;
import core.language.word.Word;
import core.learning.features.FeatureExtractor;
import core.learning.features.FeatureVector;
import core.learning.label.Label;
import core.learning.learning_instance.LearningInstance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentenceLearningInstanceExtractor implements LearningInstanceExtractor {

    private final FeatureExtractor featureExtractor;
    private List<LearningInstance> learningInstances;

    public SentenceLearningInstanceExtractor(FeatureExtractor featureExtractor) {
        this.featureExtractor = featureExtractor;
        learningInstances = new ArrayList<LearningInstance>();
    }

    @Override
    public List<LearningInstance> getLearningInstances() {
        return learningInstances;
    }

    @Override
    public void extractLearningInstances(Iterator<Word> wordIterator) {

        Word wordBefore = new DummyWord();
        Word word = new DummyWord();
        Word wordAfter = new DummyWord();

        while(wordIterator.hasNext()) {
            List<Word> sentence = new ArrayList<Word>();
            List<FeatureVector> featureSequence = new ArrayList<FeatureVector>();
            List<Label> labelSequence = new ArrayList<Label>();
            for(int j = 0; j < 10 && wordIterator.hasNext(); j++) {
                wordBefore = word;
                word = wordAfter;
                wordAfter = wordIterator.next();

                sentence.add(wordAfter);

                FeatureVector features = featureExtractor.extractFeatures(wordBefore, word, wordAfter);
                featureSequence.add(features);

                labelSequence.add(wordAfter.getLabel());
            }
            learningInstances.add(new LearningInstance(sentence, featureSequence, labelSequence));
        }

    }
}
