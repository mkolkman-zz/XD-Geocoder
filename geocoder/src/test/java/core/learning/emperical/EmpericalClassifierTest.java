package core.learning.emperical;

import core.learning.learning_instance.LearningInstance;
import core.learning.evaluator.Metric;
import io.corpus.xml.XMLStreamReaderFactory;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class EmpericalClassifierTest {

    protected List<LearningInstance> trainingInstances;
    protected List<LearningInstance> testInstances;

    public abstract void testOnLglCorpus() throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException;

    public abstract List<Metric> doExperiment(double gamma, double cost, double weight_b, double weight_i, double weight_o);

    protected void populateTrainingInstanceList(List<LearningInstance> learningInstances) {
        int splitIndex = learningInstances.size() / 10 * 8;
        Collections.shuffle(learningInstances);
        trainingInstances = new ArrayList<LearningInstance>(learningInstances.subList(0, splitIndex));
    }

    protected void populateTestInstanceList(List<LearningInstance> learningInstances) {
        int splitIndex = learningInstances.size() / 10 * 8;
        Collections.shuffle(learningInstances);
        testInstances = new ArrayList<LearningInstance>(learningInstances.subList(splitIndex + 1, learningInstances.size() - 1));
    }

    protected void populateTrainingAndTestInstanceLists(List<LearningInstance> learningInstances) {
        int splitIndex = learningInstances.size() / 10 * 8;
        Collections.shuffle(learningInstances);
        trainingInstances = new ArrayList<LearningInstance>(learningInstances.subList(0, splitIndex));
        testInstances = new ArrayList<LearningInstance>(learningInstances.subList(splitIndex + 1, learningInstances.size() - 1));
    }

    protected void printPerformanceMetricsWithoutHeader(List<Metric> metrics) {
        for (Metric metric : metrics) {
            System.out.print(metric.value + ", ");
        }
        System.out.println();
    }
}
