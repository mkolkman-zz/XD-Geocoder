package core.learning.classifier.maxent;

import cc.mallet.classify.Trial;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import core.learning.Classification;
import core.learning.LearningInstance;
import core.learning.classifier.Classifier;
import mallet.transformers.MalletInstanceListTransformer;

import java.util.List;

public class MalletMaxEntClassifier implements Classifier {

    private final cc.mallet.classify.Classifier classifier;
    private final MalletInstanceListTransformer instanceListTransformer;

    public MalletMaxEntClassifier(cc.mallet.classify.Classifier classifier, MalletInstanceListTransformer instanceListTransformer) {
        this.classifier = classifier;
        this.instanceListTransformer = instanceListTransformer;
    }

    @Override
    public Classification classify(LearningInstance input) {
        Instance instance = instanceListTransformer.getInstanceTransformer().toMalletInstance(input);

        cc.mallet.classify.Classification classification = classifier.classify(instance);
        return null;
    }

    public Trial trial(List<LearningInstance> input) {
        InstanceList instances = instanceListTransformer.toMalletInstanceList(input);

        return new Trial(classifier, instances);
    }

}
