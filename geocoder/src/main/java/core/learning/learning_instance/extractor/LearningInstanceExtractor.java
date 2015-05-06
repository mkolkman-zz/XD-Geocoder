package core.learning.learning_instance.extractor;

import core.language.word.Word;
import core.learning.learning_instance.LearningInstance;

import java.util.Iterator;
import java.util.List;

public interface LearningInstanceExtractor {

    List<LearningInstance> getLearningInstances();

    void extractLearningInstances(Iterator<Word> wordIterator);

}
