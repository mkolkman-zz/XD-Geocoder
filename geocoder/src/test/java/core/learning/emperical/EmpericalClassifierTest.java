package core.learning.emperical;

import core.learning.label.Label;
import core.learning.learning_instance.LearningInstance;
import core.learning.evaluator.Metric;
import io.corpus.xml.XMLStreamReaderFactory;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

        LearningInstance learningInstance = new LearningInstance(learningInstances.get(0).getWord(), learningInstances.get(0).getFeatures(), Label.IN_TOPONYM);
        trainingInstances.add(learningInstance);
    }

    protected void populateTestInstanceList(List<LearningInstance> learningInstances) {
        int splitIndex = learningInstances.size() / 10 * 8;
        Collections.shuffle(learningInstances);
        testInstances = new ArrayList<LearningInstance>(learningInstances.subList(splitIndex, learningInstances.size()));

        LearningInstance learningInstance = new LearningInstance(learningInstances.get(0).getWord(), learningInstances.get(0).getFeatures(), Label.IN_TOPONYM);
        testInstances.add(learningInstance);
    }

    protected void populateTrainingAndTestInstanceLists(List<LearningInstance> learningInstances) {
        int splitIndex = learningInstances.size() / 10 * 8;
        Collections.shuffle(learningInstances);
        trainingInstances = new ArrayList<LearningInstance>(learningInstances.subList(0, splitIndex));
        testInstances = new ArrayList<LearningInstance>(learningInstances.subList(splitIndex, learningInstances.size()));

        LearningInstance learningInstance = new LearningInstance(learningInstances.get(0).getWord(), learningInstances.get(0).getFeatures(), Label.IN_TOPONYM);
        trainingInstances.add(learningInstance);
        testInstances.add(learningInstance);
    }

    protected void printPerformanceMetricsWithoutHeader(List<Metric> metrics) {
        for (Metric metric : metrics) {
            BigDecimal bd = new BigDecimal(metric.value).setScale(3, RoundingMode.HALF_EVEN);
            System.out.print(bd.doubleValue() + ", ");
        }
        System.out.println();
    }
}
