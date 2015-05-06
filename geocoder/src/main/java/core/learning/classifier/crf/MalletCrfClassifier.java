package core.learning.classifier.crf;

import cc.mallet.classify.Trial;
import cc.mallet.fst.CRF;
import core.learning.classifier.Classification;
import core.learning.learning_instance.LearningInstance;
import core.learning.classifier.Classifier;

import java.util.List;

public class MalletCrfClassifier implements Classifier {

    private CRF crf;

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
