package core.learning.classifier.maxent;

import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.types.InstanceList;
import core.learning.learning_instance.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import transformers.learning_instance.MalletInstanceTransformer;

import java.util.List;

public class MalletMaxEntClassifierTrainer implements ClassifierTrainer {

    private MalletInstanceTransformer instanceTransformer;

    public MalletMaxEntClassifierTrainer(MalletInstanceTransformer instanceTransformer) {
        this.instanceTransformer = instanceTransformer;
    }

    @Override
    public Classifier train(List<LearningInstance> trainingData) {
        InstanceList instances = instanceTransformer.toMalletInstanceList(trainingData);

        cc.mallet.classify.ClassifierTrainer trainer = new MaxEntTrainer();
        return new MalletMaxEntClassifier(trainer.train(instances), instanceTransformer);
    }
}
