package core.learning.classifier.crf;

import cc.mallet.classify.Trial;
import cc.mallet.fst.CRF;
import cc.mallet.types.InstanceList;
import core.learning.classifier.Classification;
import core.learning.learning_instance.LearningInstance;
import core.learning.classifier.Classifier;
import transformers.learning_instance.MalletInstanceTransformer;

import java.util.List;

public class MalletCrfClassifier implements Classifier {

    private CRF crf;
    private MalletInstanceTransformer instanceTransformer;

    public MalletCrfClassifier(CRF crf, MalletInstanceTransformer instanceTransformer) {
        this.crf = crf;
        this.instanceTransformer = instanceTransformer;
    }

    @Override
    public Classification classify(LearningInstance instance) {
        return null;
    }

    @Override
    public Trial trial(List<LearningInstance> input) {
        return null;
    }
}
