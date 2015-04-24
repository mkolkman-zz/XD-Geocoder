package core.learning.classifier.svm;

import ca.uwo.csd.ai.nlp.kernel.CustomKernel;
import ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import ca.uwo.csd.ai.nlp.libsvm.svm_parameter;
import ca.uwo.csd.ai.nlp.mallet.libsvm.SVMClassifierTrainer;
import cc.mallet.types.InstanceList;
import core.learning.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import mallet.transformers.MalletInstanceListTransformer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class MalletSvmClassifierTrainer implements ClassifierTrainer {

    private CustomKernel kernel;
    private MalletInstanceListTransformer instanceListTransformer;

    public MalletSvmClassifierTrainer(CustomKernel kernel, MalletInstanceListTransformer instanceListTransformer) {
        this.kernel = kernel;
        this.instanceListTransformer = instanceListTransformer;
    }

    @Override
    public Classifier train(List<LearningInstance> trainingData) {
        return this.train(trainingData, new svm_parameter());
    }

    public Classifier train(List<LearningInstance> trainingData, svm_parameter params) {
        InstanceList instances = instanceListTransformer.toMalletInstanceList(trainingData);

        SVMClassifierTrainer trainer = new SVMClassifierTrainer(kernel);

        trainer.setParam(params);

        PrintStream out = System.out;
        System.setOut(new PrintStream(new DummyOutputStream()));
        try {
            return new MalletSvmClassifier(trainer.train(instances), instanceListTransformer);
        } finally {
            System.setOut(out);
        }
    }

    private static class DummyOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
        }
    }
}
