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

    static final String LGL_MULTIPLE_ARTICLES_TEST_FILE = "/core/lgl-multiple-articles-test.xml";
    static final String LGL_ARTICLES_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/corpora/LGL/articles.xml";

    static final String ENGLISH_TAGGER_MODEL = "/core/language/pos/english-left3words-distsim.tagger";

//    public static final String LGL_CORPUS_FILE = "E:\\Development\\java\\XD-Geocoder\\geocoder\\src\\test\\resources\\core\\lgl-multiple-articles-test.xml";
//    private static final String GEONAMES_GAZETTEER_FILE = "E:\\Development\\gazetteers\\geonames\\allCountries.txt";
//    public static final String FEATURE_FILE = "E:\\Development\\features\\xd-features.ser";
//    public static final String EXPERIMENT_RESULTS_FILE = "E:\\Development\\results\\xd-results.csv";

    static final String FEATURE_FILE = "/home/mike/data/xd-features.ser";
    static final String LGL_CORPUS_FILE = "/home/mike/dev/MasterThesis/XD-Geocoder/geocoder/src/test/resources/core/lgl-multiple-articles-test.xml";
    static final String GEONAMES_GAZETTEER_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/gazetteers/geonames/allCountries.txt";
    static final String EXPERIMENT_RESULTS_FILE = "/home/mike/data/xd-svm-results.csv";

    protected List<LearningInstance> trainingInstances;
    protected List<LearningInstance> testInstances;

    public abstract void testOnLglCorpus() throws IOException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException, ClassNotFoundException;

    public abstract List<Metric> doExperiment(double gamma, double cost, double weight_b, double weight_i, double weight_o);

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
