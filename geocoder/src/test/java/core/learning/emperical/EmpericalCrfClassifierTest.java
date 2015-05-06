package core.learning.emperical;

import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import core.learning.classifier.crf.MalletCrfClassifierTrainer;
import core.learning.emperical.setup.ExperimentSetup;
import core.learning.emperical.setup.SvmExperimentSetup;
import core.learning.evaluator.Evaluator;
import core.learning.evaluator.Metric;
import core.learning.evaluator.lgl.LglEvaluator;
import core.learning.learning_instance.LearningInstance;
import io.corpus.xml.XMLStreamReaderFactory;
import transformers.feature_vector.MalletFeatureVectorTransformer;
import transformers.learning_instance.MalletInstanceTransformer;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class EmpericalCrfClassifierTest extends EmpericalClassifierTest {

    @Override
    public void testOnLglCorpus() throws IOException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException, ClassNotFoundException {
        ExperimentSetup experimentSetup = new SvmExperimentSetup();
        List<LearningInstance> learningInstances = experimentSetup.getLearningInstances(
                LGL_CORPUS_FILE,
                GEONAMES_GAZETTEER_FILE,
                getClass().getResource(ENGLISH_TAGGER_MODEL).toString(),
                FEATURE_FILE
        );

        super.populateTrainingAndTestInstanceLists(learningInstances);

        List<Metric> metrics = doExperiment(0.75, 0.75, 60, 160, 1.0);

        super.printPerformanceMetricsWithoutHeader(metrics);
    }

    @Override
    public List<Metric> doExperiment(double gamma, double cost, double weight_b, double weight_i, double weight_o) {
        ClassifierTrainer trainer = new MalletCrfClassifierTrainer(new MalletInstanceTransformer(new MalletFeatureVectorTransformer()));
        Classifier classifier = trainer.train(trainingInstances);

        Evaluator evaluator = new LglEvaluator(classifier.trial(testInstances));

        return evaluator.getPerformanceMetrics();
    }
}
