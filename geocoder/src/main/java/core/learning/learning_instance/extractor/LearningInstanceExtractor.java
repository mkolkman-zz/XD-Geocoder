package core.learning.learning_instance.extractor;

import core.language.word.DummyWord;
import core.language.word.Word;
import core.learning.features.FeatureExtractor;
import core.learning.features.FeatureVector;
import core.learning.learning_instance.LearningInstance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LearningInstanceExtractor {

    private FeatureExtractor featureExtractor;

    private List<LearningInstance> learningInstances;

    public LearningInstanceExtractor(FeatureExtractor featureExtractor) {
        this.featureExtractor = featureExtractor;
        learningInstances = new ArrayList<LearningInstance>();
    }

    public List<LearningInstance> getLearningInstances() {
        return learningInstances;
    }

    public void extractLearningInstances(Iterator<Word> wordIterator) {

        Word wordBefore = new DummyWord();
        Word word = new DummyWord();
        Word wordAfter = new DummyWord();

        while (wordIterator.hasNext()) {
            wordBefore = word;
            word = wordAfter;
            wordAfter = wordIterator.next();

            if (word != null) {
                FeatureVector features = featureExtractor.extractFeatures(wordBefore, word, wordAfter);

                learningInstances.add(new LearningInstance(word, features, word.getLabel()));
            }
        }

        wordBefore = word;
        word = wordAfter;
        wordAfter = new DummyWord();

        if (word != null) {
            FeatureVector features = featureExtractor.extractFeatures(wordBefore, word, wordAfter);

            learningInstances.add(new LearningInstance(word, features, word.getLabel()));
        }

    }

    public int getLearningInstanceCount() {
        return learningInstances.size();
    }
}
