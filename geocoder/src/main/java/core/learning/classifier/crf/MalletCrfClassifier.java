package core.learning.classifier.crf;

import cc.mallet.classify.Trial;
import cc.mallet.fst.CRF;
import core.learning.Classification;
import core.learning.LearningInstance;
import core.learning.classifier.Classifier;
import mallet.transformers.MalletInstanceTransformer;

import java.util.List;

public class MalletCrfClassifier implements Classifier {

    private CRF crf;
    private MalletInstanceTransformer instanceTransformer;

    public MalletCrfClassifier(CRF crf) {
        this.crf = crf;
    }

    @Override
    public Classification classify(LearningInstance instance) {
        return null;
    }

    @Override
    public Trial trial(List<LearningInstance> instances) {
        return null;
    }
}
