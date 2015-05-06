package core.learning.classifier;

import core.learning.learning_instance.LearningInstance;

import java.util.List;

public interface ClassifierTrainer {

    Classifier train(List<LearningInstance> trainingData);
}
