package core.learning.classifier.svm;

import ca.uwo.csd.ai.nlp.kernel.CustomKernel;
import ca.uwo.csd.ai.nlp.libsvm.svm_parameter;
import ca.uwo.csd.ai.nlp.mallet.libsvm.SVMClassifier;
import ca.uwo.csd.ai.nlp.mallet.libsvm.SVMClassifierTrainer;
import cc.mallet.types.InstanceList;
import core.learning.learning_instance.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import transformers.learning_instance.MalletInstanceTransformer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class  MalletSvmClassifierTrainer implements ClassifierTrainer {

    private MalletInstanceTransformer instanceTransformer;

    private CustomKernel kernel;
    private SVMClassifierTrainer trainer;
    private SVMClassifier classifier;

    public MalletSvmClassifierTrainer(CustomKernel kernel, MalletInstanceTransformer instanceTransformer) {
        this.kernel = kernel;
        this.instanceTransformer = instanceTransformer;
    }

    @Override
    public Classifier train(List<LearningInstance> trainingData) {
        return this.train(trainingData, new svm_parameter());
    }

    public Classifier train(List<LearningInstance> trainingData, svm_parameter params) {
        InstanceList instances = instanceTransformer.toMalletInstanceList(trainingData);

        trainer = new SVMClassifierTrainer(kernel);

        trainer.setParam(params);

        PrintStream out = System.out;
        System.setOut(new PrintStream(new DummyOutputStream()));
        try {
            classifier = trainer.train(instances);
            return new MalletSvmClassifier(classifier, instanceTransformer);
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
