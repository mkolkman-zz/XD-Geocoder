package core.learning.emperical;

import ca.uwo.csd.ai.nlp.kernel.CustomKernel;
import ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import ca.uwo.csd.ai.nlp.kernel.RBFKernel;
import ca.uwo.csd.ai.nlp.libsvm.svm_parameter;
import core.learning.emperical.setup.BinarySvmExperimentSetup;
import core.learning.emperical.setup.ExperimentSetup;
import core.learning.learning_instance.LearningInstance;
import core.learning.evaluator.Metric;
import core.learning.classifier.Classifier;
import core.learning.classifier.svm.MalletSvmClassifierTrainer;
import core.learning.evaluator.Evaluator;
import core.learning.evaluator.lgl.BinaryLglEvaluator;
import io.corpus.xml.XMLStreamReaderFactory;
import transformers.feature_vector.MalletFeatureVectorTransformer;
import transformers.learning_instance.MalletInstanceTransformer;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EmpericalBinarySvmClassifierTest extends EmpericalClassifierTest {

    private static final String LGL_MULTIPLE_ARTICLES_TEST_FILE = "/core/lgl-multiple-articles-test.xml";
    private static final String LGL_ARTICLES_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/corpora/LGL/articles.xml";

    private static final String ENGLISH_TAGGER_MODEL = "/core/language/pos/english-left3words-distsim.tagger";

//    public static final String LGL_CORPUS_FILE = "E:\\Development\\java\\XD-Geocoder\\geocoder\\src\\test\\resources\\core\\lgl-multiple-articles-test.xml";
//    private static final String GEONAMES_GAZETTEER_FILE = "E:\\Development\\gazetteers\\geonames\\allCountries.txt";
//    public static final String FEATURE_FILE = "E:\\Development\\features\\xd-features.ser";
//    public static final String EXPERIMENT_RESULTS_FILE = "E:\\Development\\results\\xd-results.csv";

    public static final String FEATURE_FILE = "/home/mike/data/xd-features.ser";
    public static final String LGL_CORPUS_FILE = "/home/mike/dev/MasterThesis/XD-Geocoder/geocoder/src/test/resources/core/lgl-multiple-articles-test.xml";
    public static final String GEONAMES_GAZETTEER_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/gazetteers/geonames/allCountries.txt";
    public static final String EXPERIMENT_RESULTS_FILE = "/home/mike/data/xd-svm-results.csv";

    MalletSvmClassifierTrainer trainer;

    @Override
    @Test
    public void testOnLglCorpus() throws IOException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException, ClassNotFoundException {

        ExperimentSetup experimentSetup = new BinarySvmExperimentSetup();
        List<LearningInstance> learningInstances = experimentSetup.getLearningInstances(
                LGL_CORPUS_FILE,
                GEONAMES_GAZETTEER_FILE,
                getClass().getResource(ENGLISH_TAGGER_MODEL).toString(),
                FEATURE_FILE
        );

        super.populateTrainingAndTestInstanceLists(learningInstances);

        PrintStream out = new PrintStream(new FileOutputStream(EXPERIMENT_RESULTS_FILE));
        System.setOut(out);

        double[] gamma_list = new double[]{0.75};
        double[] cost_list = new double[]{0.75};
        double[] weight_b_list = new double[]{50, 60, 70};
        double[] weight_i_list = new double[]{150, 165, 180};
        for (double gamma : gamma_list) {
            for (double cost : cost_list) {
                for (double weight_b : weight_b_list) {
                    for(double weight_i : weight_i_list) {
                        System.out.print(gamma + ", ");
                        System.out.print(cost + ", ");
                        System.out.print(weight_b + ", ");
                        System.out.print(weight_i + ", ");
                        System.out.print(1.0 + ", ");
                        super.printPerformanceMetricsWithoutHeader(doExperiment(gamma, cost, weight_b, weight_i, 1.0));
                    }
                }
            }
        }
    }

    @Override
    public List<Metric> doExperiment(double gamma, double cost, double weight_b, double weight_i, double weight_o) {
        trainer = makeClassifierTrainer(makeSvmKernel(gamma, cost));

        svm_parameter params = makeTrainingParams(weight_b, weight_i, weight_o);

        Classifier classifier = trainer.train(trainingInstances, params);

        Evaluator evaluator = new BinaryLglEvaluator(classifier.trial(testInstances));

        return evaluator.getPerformanceMetrics();
    }

    public MalletSvmClassifierTrainer makeClassifierTrainer(CustomKernel kernel) {
        MalletInstanceTransformer instanceTransformer = new MalletInstanceTransformer(new MalletFeatureVectorTransformer());

        return new MalletSvmClassifierTrainer(kernel, instanceTransformer);
    }

    private CustomKernel makeSvmKernel(double gamma, double cost) {
        List<CustomKernel> kernels = new ArrayList<CustomKernel>();
        kernels.add(new LinearKernel());
        svm_parameter kernelParams = new svm_parameter();
        kernelParams.gamma = gamma;
        kernelParams.C = cost;
        kernels.add(new RBFKernel(kernelParams));

        return new ca.uwo.csd.ai.nlp.kernel.CompositeKernel(kernels);
    }

    private svm_parameter makeTrainingParams(double weightB, double weightI, double weightO) {
        svm_parameter params = new svm_parameter();
        params.weight_label = new int[]{1, 2, 3};
        params.weight = new double[]{weightB, weightO, weightI};
        params.nr_weight = 3;
        return params;
    }

}
