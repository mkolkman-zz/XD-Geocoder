package core.learning.classifier;

import cc.mallet.classify.Trial;
import core.learning.Classification;
import core.learning.LearningInstance;

import java.util.List;

public interface Classifier {

    Classification classify(LearningInstance instance);

    Trial trial(List<LearningInstance> instances);
}
