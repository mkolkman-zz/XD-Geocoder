package core.learning.emperical;

import ca.uwo.csd.ai.nlp.kernel.CustomKernel;
import ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import ca.uwo.csd.ai.nlp.kernel.RBFKernel;
import ca.uwo.csd.ai.nlp.libsvm.svm_parameter;
import core.learning.classifier.Classifier;
import core.learning.classifier.svm.MalletSvmClassifierTrainer;
import core.learning.emperical.setup.*;
import core.learning.evaluator.Evaluator;
import core.learning.evaluator.Metric;
import core.learning.evaluator.lgl.LglEvaluator;
import core.learning.learning_instance.LearningInstance;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import io.corpus.xml.XMLStreamReaderFactory;
import io.learning_instance.LearningInstanceReader;
import io.learning_instance.LearningInstanceWriter;
import org.junit.Test;
import transformers.feature_vector.MalletFeatureVectorTransformer;
import transformers.learning_instance.MalletInstanceTransformer;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpiricalCrossDomainSamClassifierTest extends EmpericalClassifierTest {

    MalletSvmClassifierTrainer trainer;
    private Classifier classifier;

    @Override
    @Test
    public void testOnLglCorpus() throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {

        ExperimentSetup experimentSetup;
        Map<String, List<LearningInstance>> learningInstances = getCorpora();

        PrintStream out = new PrintStream(new FileOutputStream(R.EXPERIMENT_RESULTS_FILE));
        System.setOut(out);

        String[] trainingList = new String[]{"CWAR", "GAT", "LGL"};
        String[] testList = new String[]{"CWAR", "GAT", "LGL"};


        for (String training : trainingList) {
                for (String test : testList) {
                    List<List<Metric>> metricSums = new ArrayList<List<Metric>>();
                    for(int iter = 0; iter < 5; iter++) {
                        super.populateTrainingInstanceList(learningInstances.get(training));
                        train();

                        super.populateTestInstanceList(learningInstances.get(test));
                        metricSums.add(doExperiment());
                    }

                    List<Metric> metrics = calculateAverageMetrics(metricSums);

                    System.out.print(training + ", " + test + ", ");
                    super.printPerformanceMetricsWithoutHeader(metrics);
                }
            }
        }

    private Map<String, List<LearningInstance>> getCorpora() throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        ExperimentSetup experimentSetup;
        Map<String, List<LearningInstance>> learningInstances = new HashMap<String, List<LearningInstance>>();

        experimentSetup = new CwarExperimentSetup(
                new GazetteerFactory(R.GEONAMES_GAZETTEER_FILE),
                new MaxentTagger(getClass().getResource(R.ENGLISH_TAGGER_MODEL).toString()),
                new LearningInstanceReader(R.CWAR_FEATURE_FILE),
                new LearningInstanceWriter(R.CWAR_FEATURE_FILE)
        );
        System.out.println("Importing Cwar");
        learningInstances.put("CWAR", experimentSetup.getLearningInstances(R.CWAR_CORPUS_FILE));
        experimentSetup.cleanup();
        System.out.println("CWar imported");

        experimentSetup = new GatExperimentSetup(
                new GazetteerFactory(R.GEONAMES_GAZETTEER_FILE),
                new MaxentTagger(getClass().getResource(R.ENGLISH_TAGGER_MODEL).toString()),
                new LearningInstanceReader(R.GAT_FEATURE_FILE),
                new LearningInstanceWriter(R.GAT_FEATURE_FILE)
        );
        System.out.println("Importing GAT");
        learningInstances.put("GAT", experimentSetup.getLearningInstances(R.GAT_CORPUS_FILE));
        experimentSetup.cleanup();
        System.out.println("GAT imported");

        experimentSetup = new LglExperimentSetup(
                new GazetteerFactory(R.GEONAMES_GAZETTEER_FILE),
                new MaxentTagger(getClass().getResource(R.ENGLISH_TAGGER_MODEL).toString()),
                new LearningInstanceReader(R.LGL_FEATURE_FILE),
                new LearningInstanceWriter(R.LGL_FEATURE_FILE)
        );
        System.out.println("Importing LGL");
        learningInstances.put("LGL", experimentSetup.getLearningInstances(R.LGL_CORPUS_FILE));
        experimentSetup.cleanup();
        System.out.println("LGL imported");
        return learningInstances;
    }

    private void train() {
        train(0.75, 1, 50, 160, 1);
    }

    private void train(double gamma, double cost, double weight_b, double weight_i, double weight_o) {
        trainer = makeClassifierTrainer(makeSvmKernel(gamma, cost));

        svm_parameter params = makeTrainingParams(weight_b, weight_i, weight_o);

        classifier = trainer.train(trainingInstances, params);
    }

    public List<Metric> doExperiment() {
        return doExperiment(0.75, 1, 50, 160, 1);
    }

    @Override
    public List<Metric> doExperiment(double gamma, double cost, double weight_b, double weight_i, double weight_o) {
        Evaluator evaluator = new LglEvaluator(classifier.trial(testInstances));

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

    private List<Metric> calculateAverageMetrics(List<List<Metric>> metricSums) {
        Map<String, Double> metricTotals = new HashMap<String, Double>();
        for(List<Metric> metricList : metricSums) {
            for(Metric metric : metricList) {
                if( ! metricTotals.containsKey(metric.name)) {
                    metricTotals.put(metric.name, 0.0);
                }
                metricTotals.put(metric.name, metricTotals.get(metric.name) + metric.value);
            }
        }

        List<Metric> result = new ArrayList<Metric>();
        for(Map.Entry<String, Double> entry : metricTotals.entrySet()) {
            result.add(new Metric(entry.getKey(), (entry.getValue() / metricTotals.size())));
        }
        return result;
    }

}
