package core.learning.classifier.crf;

import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFOptimizableByLabelLikelihood;
import cc.mallet.fst.CRFTrainerByValueGradients;
import cc.mallet.optimize.Optimizable;
import cc.mallet.types.InstanceList;
import core.learning.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import mallet.transformers.MalletInstanceListTransformer;

import java.util.List;

public class MalletCrfClassifierTrainer implements ClassifierTrainer {

    private MalletInstanceListTransformer instanceListTransformer;

    public MalletCrfClassifierTrainer(MalletInstanceListTransformer instanceListTransformer) {
        this.instanceListTransformer = instanceListTransformer;
    }

    @Override
    public Classifier train(List<LearningInstance> input) {
        InstanceList trainingData = instanceListTransformer.toMalletInstanceList(input);

        CRF crf = new CRF(trainingData.getDataAlphabet(), trainingData.getTargetAlphabet());
        crf.addFullyConnectedStatesForLabels();
        crf.setWeightsDimensionAsIn(trainingData, false);

        CRFOptimizableByLabelLikelihood optimizable = new CRFOptimizableByLabelLikelihood(crf, trainingData);
        Optimizable.ByGradientValue[] gradientValues = new Optimizable.ByGradientValue[]{optimizable};
        CRFTrainerByValueGradients trainer = new CRFTrainerByValueGradients(crf, gradientValues);

        trainer.setMaxResets(0);
        trainer.train(trainingData, Integer.MAX_VALUE);

        return new MalletCrfClassifier(trainer.getCRF());
    }
}
