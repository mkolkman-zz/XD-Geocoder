package core.learning.classifier.crf;

import cc.mallet.fst.*;
import cc.mallet.optimize.Optimizable;
import cc.mallet.types.InstanceList;
import core.learning.learning_instance.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import transformers.learning_instance.MalletInstanceTransformer;

import java.util.List;

public class MalletCrfClassifierTrainer implements ClassifierTrainer {

    private MalletInstanceTransformer instanceTransformer;
    private CRFTrainerByValueGradients trainer;

    private TransducerEvaluator evaluator;

    public MalletCrfClassifierTrainer(MalletInstanceTransformer instanceTransformer) {
        this.instanceTransformer = instanceTransformer;
    }

    @Override
    public Classifier train(List<LearningInstance> input) {
        InstanceList trainingData = instanceTransformer.toMalletSequenceInstanceList(input);

        CRF crf = new CRF(trainingData.getDataAlphabet(), trainingData.getTargetAlphabet());
        crf.addFullyConnectedStatesForLabels();
        crf.setWeightsDimensionAsIn(trainingData, false);

        CRFOptimizableByLabelLikelihood optimizable = new CRFOptimizableByLabelLikelihood(crf, trainingData);
        Optimizable.ByGradientValue[] gradientValues = new Optimizable.ByGradientValue[]{optimizable};
        trainer = new CRFTrainerByValueGradients(crf, gradientValues);

        trainer.setMaxResets(0);

        trainer.addEvaluator(evaluator);

        trainer.train(trainingData, Integer.MAX_VALUE);

        return new MalletCrfClassifier(trainer.getCRF(), instanceTransformer);
    }

    public void setEvaluator(TransducerEvaluator evaluator) {
        this.evaluator = evaluator;
    }
}
