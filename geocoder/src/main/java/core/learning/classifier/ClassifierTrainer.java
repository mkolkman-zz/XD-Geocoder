package core.learning.classifier;

import core.learning.LearningInstance;

import java.util.List;

public interface ClassifierTrainer {

    Classifier train(List<LearningInstance> trainingData);
}
