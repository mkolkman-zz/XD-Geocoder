package core.learning.classifier.svm;

import cc.mallet.classify.Trial;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import core.learning.classifier.Classification;
import core.learning.learning_instance.LearningInstance;
import core.learning.classifier.Classifier;
import transformers.learning_instance.MalletInstanceTransformer;

import java.util.List;

public class MalletSvmClassifier implements Classifier {

    private final cc.mallet.classify.Classifier classifier;
    private final MalletInstanceTransformer instanceTransformer;

    public MalletSvmClassifier(cc.mallet.classify.Classifier classifier, MalletInstanceTransformer instanceTransformer) {
        this.classifier = classifier;
        this.instanceTransformer = instanceTransformer;
    }

    @Override
    public Classification classify(LearningInstance input) {
        Instance instance = instanceTransformer.toMalletInstance(input);

        cc.mallet.classify.Classification classification = classifier.classify(instance);
        return null;
    }

    @Override
    public Trial trial(List<LearningInstance> input) {
        InstanceList instances = instanceTransformer.toMalletInstanceList(input);

        return new Trial(classifier, instances);
    }

}
