package core.learning.emperical;

import cc.mallet.fst.PerClassAccuracyEvaluator;
import core.learning.classifier.crf.MalletCrfClassifierTrainer;
import core.learning.emperical.setup.CrfExperimentSetup;
import core.learning.emperical.setup.ExperimentSetup;
import core.learning.emperical.setup.R;
import core.learning.evaluator.Metric;
import core.learning.learning_instance.LearningInstance;
import io.corpus.xml.XMLStreamReaderFactory;
import org.junit.Test;
import transformers.feature_vector.MalletFeatureVectorTransformer;
import transformers.learning_instance.MalletInstanceTransformer;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmpericalCrfClassifierTest extends EmpericalClassifierTest {

    @Test
    @Override
    public void testOnLglCorpus() throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        ExperimentSetup experimentSetup = new CrfExperimentSetup();
        List<LearningInstance> learningInstances = experimentSetup.getLearningInstances(R.LGL_CORPUS_FILE);

        super.populateTrainingAndTestInstanceLists(learningInstances);

        List<Metric> metrics = doExperiment(0.75, 0.75, 60, 160, 1.0);

//        super.printPerformanceMetricsWithoutHeader(metrics);
    }

    protected void populateTrainingAndTestInstanceLists(List<LearningInstance> learningInstances) {
        int splitIndex = learningInstances.size() / 10 * 3;
        int endIndex = learningInstances.size() / 10 * 5;
        Collections.shuffle(learningInstances);
        trainingInstances = new ArrayList<LearningInstance>(learningInstances.subList(0, splitIndex));
        testInstances = new ArrayList<LearningInstance>(learningInstances.subList(splitIndex + 1, endIndex));
    }

    @Override
    public List<Metric> doExperiment(double gamma, double cost, double weight_b, double weight_i, double weight_o) {
        MalletInstanceTransformer instanceTransformer = new MalletInstanceTransformer(new MalletFeatureVectorTransformer());
        MalletCrfClassifierTrainer trainer = new MalletCrfClassifierTrainer(instanceTransformer);

        trainer.setEvaluator(new PerClassAccuracyEvaluator(instanceTransformer.toMalletSequenceInstanceList(testInstances), "testing"));

        trainer.train(trainingInstances);

        return null;
    }
}
