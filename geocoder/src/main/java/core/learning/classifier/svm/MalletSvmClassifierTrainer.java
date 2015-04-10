package core.learning.classifier.svm;

import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.types.InstanceList;
import core.learning.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import mallet.transformers.MalletInstanceListTransformer;

import java.util.List;

public class MalletSvmClassifierTrainer implements ClassifierTrainer {

    private MalletInstanceListTransformer instanceListTransformer;

    public MalletSvmClassifierTrainer(MalletInstanceListTransformer instanceListTransformer) {
        this.instanceListTransformer = instanceListTransformer;
    }

    @Override
    public Classifier train(List<LearningInstance> trainingData) {
        InstanceList instances = instanceListTransformer.toMalletInstanceList(trainingData);

        cc.mallet.classify.ClassifierTrainer trainer = new MaxEntTrainer();
        return new MalletSvmClassifier(trainer.train(instances), instanceListTransformer);
    }
}
