package core.learning.classifier.svm;

import ca.uwo.csd.ai.nlp.kernel.CustomKernel;
import ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import ca.uwo.csd.ai.nlp.kernel.RBFKernel;
import ca.uwo.csd.ai.nlp.libsvm.svm_parameter;
import cc.mallet.classify.Trial;
import core.geo.LocationGazetteer;
import core.geo.geonames.GeonamesLocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.document.news.Article;
import core.language.labeller.LglLabeller;
import core.language.pos.stanford.StanfordPosTagger;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import core.learning.Label;
import core.learning.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.features.FeatureExtractor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import io.corpus.CorpusReader;
import io.corpus.xml.XMLStreamReader;
import io.corpus.xml.XMLStreamReaderFactory;
import io.corpus.xml.XMLStreamReaderType;
import io.corpus.xml.lgl.LGLCorpusReader;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;
import mallet.transformers.MalletFeatureVectorTransformer;
import mallet.transformers.MalletInstanceListTransformer;
import mallet.transformers.MalletInstanceTransformer;
import org.junit.Test;
import stanford.transformers.StanfordTransformer;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.text.ParseException;
import java.util.*;

public class MalletSvmClassifierTrainerTest {

    private static final String LGL_MULTIPLE_ARTICLES_TEST_FILE = "/core/lgl-multiple-articles-test.xml";
    private static final String LGL_ARTICLES_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/corpora/LGL/articles.xml";

    private static final String GEONAMES_GAZETTEER_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/gazetteers/geonames/allCountries.txt";

    private static final String ENGLISH_TAGGER_MODEL = "/core/language/pos/english-left3words-distsim.tagger";

    public static final String FEATURE_FILE = "/home/mike/data/xd-features.ser";
    public static final String EXPERIMENT_RESULTS_FILE = "/home/mike/data/xd-results.csv";

    public double WEIGHT_B = 65.0;
    public double WEIGHT_I = 165.0;
    public double WEIGHT_O = 1.0;
    public double SVM_KERNEL_GAMMA = 0.5;
    public double SVM_KERNEL_COST = 0.75;

    private List<LearningInstance> trainingInstances;
    private List<LearningInstance> testInstances;

    @Test
    public void testTraining_LGLCorpusSample() throws IOException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException, ClassNotFoundException {

        List<LearningInstance> learningInstances = getLearningInstances();

        populateTrainingAndTestInstanceLists(learningInstances);

        PrintStream out = new PrintStream(new FileOutputStream(EXPERIMENT_RESULTS_FILE));
        System.setOut(out);

        double[] gamma_list = new double[]{0.25, 0.5, 0.75};
        double[] cost_list = new double[]{0.5, 0.75, 1.0};
        double[] weight_b_list = new double[]{50, 65, 80};
        double[] weight_i_list = new double[]{150, 165, 180};
        for (double gamma : gamma_list) {
            SVM_KERNEL_GAMMA = gamma;
            for (double cost : cost_list) {
                SVM_KERNEL_COST = cost;
                for (double weight_b : weight_b_list) {
                    WEIGHT_B = weight_b;
                    for(double weight_i : weight_i_list) {
                        WEIGHT_I = weight_i;
                        printPerformanceMetricsWithoutHeader(doExperiment(learningInstances));
                    }
                }
            }
        }
    }

    private List<Metric> doExperiment(List<LearningInstance> learningInstances) {
        MalletSvmClassifierTrainer trainer = getClassifierTrainer();

        svm_parameter params = getTrainingParams();

        Classifier classifier = trainer.train(trainingInstances, params);

        return computePerformanceMetrics(classifier.trial(testInstances));
    }

    private List<LearningInstance> getLearningInstances() throws ParseException, XMLStreamException, IOException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ClassNotFoundException {
        List<LearningInstance> learningInstances;
        if(fileExists(FEATURE_FILE)) {
            learningInstances = readLearningInstances();
        }else {
            String corpusFile = getClass().getResource(LGL_MULTIPLE_ARTICLES_TEST_FILE).toString();
            corpusFile = "/home/mike/dev/MasterThesis/XD-Geocoder/geocoder/build/resources/test/core/lgl-multiple-articles-test.xml";
            learningInstances = extractLearningInstances(getDictionary(corpusFile), getGazetteer(GEONAMES_GAZETTEER_FILE), getCorpusReader(corpusFile));
            writeLearningInstances(learningInstances);
        }
        return learningInstances;
    }

    private boolean fileExists(String file) {
        File instancesFile = new File(file);
        return instancesFile.exists();
    }

    private List<LearningInstance> readLearningInstances() throws IOException, ClassNotFoundException {
        List<LearningInstance> learningInstances;FileInputStream fileInput = new FileInputStream(FEATURE_FILE);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        learningInstances = (List<LearningInstance>) objectInput.readObject();
        return learningInstances;
    }

    private void writeLearningInstances(List<LearningInstance> learningInstances) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(FEATURE_FILE);
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(learningInstances);
        objectOutput.close();
        fileOutput.close();
    }

    private Dictionary getDictionary(String corpusFile) throws XMLStreamException, FileNotFoundException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(corpusFile), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);
        Dictionary dictionary = new HashMapDictionary();
        dictionary.load(corpusReader);
        return dictionary;
    }

    private GeonamesLocationGazetteer getGazetteer(String gazetteerFile) throws FileNotFoundException {
        CsvLocationParser lineParser = CsvGazetteerReader.CsvLocationParserFactory.getCsvLocationParser("Geonames");
        CsvGazetteerReader gazetteerReader = new CsvGazetteerReader(new BufferedReader(new FileReader(gazetteerFile)), lineParser);
        return new GeonamesLocationGazetteer(gazetteerReader, lineParser);
    }

    private LGLCorpusReader getCorpusReader(String corpusFile) throws XMLStreamException, FileNotFoundException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(corpusFile), XMLStreamReaderType.WOODSTOX);
        return new LGLCorpusReader(xmlStreamReader);
    }

    private List<LearningInstance> extractLearningInstances(Dictionary dictionary, LocationGazetteer gazetteer, CorpusReader corpusReader) throws ParseException {
        List<LearningInstance> learningInstances = new ArrayList<LearningInstance>();
        MaxentTagger tagger = new MaxentTagger(getClass().getResource(ENGLISH_TAGGER_MODEL).toString());

        while(corpusReader.hasNextDocument()) {

            Article article = (Article) corpusReader.getNextDocument();
            Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(article.getText()), new WordTokenFactory(), ""));

            Iterator<Word> labelledWords = new LglLabeller(tokenizedWords, article.getToponyms());
            Iterator<Word> posTaggedWords = new StanfordPosTagger(labelledWords, tagger, new StanfordTransformer());

            FeatureExtractor featureExtractor = new FeatureExtractor(posTaggedWords, dictionary, gazetteer);
            learningInstances.addAll(featureExtractor.getLearningInstances());
        }
        return learningInstances;
    }

    private void populateTrainingAndTestInstanceLists(List<LearningInstance> learningInstances) {
        int splitIndex = learningInstances.size() / 10 * 8;
        Collections.shuffle(learningInstances);
        trainingInstances = new ArrayList<LearningInstance>(learningInstances.subList(0, splitIndex));
        testInstances = new ArrayList<LearningInstance>(learningInstances.subList(splitIndex + 1, learningInstances.size() - 1));
    }

    private MalletSvmClassifierTrainer getClassifierTrainer() {
        CustomKernel kernel = getSvmKernel();
        MalletInstanceListTransformer instanceListTransformer = new MalletInstanceListTransformer(new MalletInstanceTransformer(new MalletFeatureVectorTransformer()));

        return new MalletSvmClassifierTrainer(kernel, instanceListTransformer);
    }

    private CustomKernel getSvmKernel() {
        List<CustomKernel> kernels = new ArrayList<CustomKernel>();
        kernels.add(new LinearKernel());
        svm_parameter kernelParams = new svm_parameter();
        kernelParams.C = SVM_KERNEL_COST;
        kernelParams.gamma = SVM_KERNEL_GAMMA;
        kernels.add(new RBFKernel(kernelParams));

        return new ca.uwo.csd.ai.nlp.kernel.CompositeKernel(kernels);
    }

    private svm_parameter getTrainingParams() {
        svm_parameter params = new svm_parameter();
        params.weight_label = new int[]{1, 2, 3};
        params.weight = new double[]{WEIGHT_B, WEIGHT_O, WEIGHT_I};
        params.nr_weight = 3;
        return params;
    }

    private void printLabelCounts(List<LearningInstance> learningInstances) {
        int noCount = 0;
        int startCount = 0;
        int inCount = 0;
        for (LearningInstance learningInstance : learningInstances) {
            if(learningInstance.getLabel() == Label.NO_TOPONYM) {
                noCount++;
            }else if(learningInstance.getLabel() == Label.START_OF_TOPONYM) {
                startCount++;
            }else if(learningInstance.getLabel() == Label.IN_TOPONYM) {
                inCount++;
            }
        }
        System.out.println("NO_TOPONYM count: " + noCount);
        System.out.println("START_TOPONYM count: " + startCount);
        System.out.println("IN_TOPONYM count: " + inCount);
    }

    private List<Metric> computePerformanceMetrics(Trial trial) {

        List<Metric> metrics = new ArrayList<Metric>();

        double p_b = trial.getPrecision(Label.START_OF_TOPONYM.toString());
        double p_i = trial.getPrecision(Label.IN_TOPONYM.toString());
        double p_o = trial.getPrecision(Label.NO_TOPONYM.toString());
        double p_m = (p_b + p_i + p_o) / 3;
        double p_m_pos = (p_b + p_i) / 2;
        metrics.add(new Metric("p_b", p_b));
        metrics.add(new Metric("p_i", p_i));
        metrics.add(new Metric("p_o", p_o));
        metrics.add(new Metric("p_m", p_m));
        metrics.add(new Metric("p_m_pos", p_m_pos));

        double r_b = trial.getRecall(Label.START_OF_TOPONYM.toString());
        double r_i = trial.getRecall(Label.IN_TOPONYM.toString());
        double r_o = trial.getRecall(Label.NO_TOPONYM.toString());
        double r_m = (r_b + r_i + r_o) / 3;
        double r_m_pos = (r_b + r_i) / 2;
        metrics.add(new Metric("r_b", r_b));
        metrics.add(new Metric("r_i", r_i));
        metrics.add(new Metric("r_o", r_o));
        metrics.add(new Metric("r_m", r_m));
        metrics.add(new Metric("r_m_pos", r_m_pos));

        metrics.add(new Metric("f_b", trial.getF1(Label.START_OF_TOPONYM.toString())));
        metrics.add(new Metric("f_i", trial.getF1(Label.IN_TOPONYM.toString())));
        metrics.add(new Metric("f_o", trial.getF1(Label.NO_TOPONYM.toString())));
        metrics.add(new Metric("f_m", computeFscore(p_m, r_m)));
        metrics.add(new Metric("f_m_pos", computeFscore(p_m_pos, r_m_pos)));

        return metrics;
    }

    private double computeFscore(double precision, double recall) {
        return (2 * precision * recall) / (precision + recall);
    }

    private void printPerformanceMetricsWithoutHeader(List<Metric> metrics) {
        System.out.print(SVM_KERNEL_GAMMA + ", ");
        System.out.print(SVM_KERNEL_COST + ", ");
        System.out.print(WEIGHT_B + ", ");
        System.out.print(WEIGHT_I + ", ");
        System.out.print(WEIGHT_O + ", ");

        for (Metric metric : metrics) {
            System.out.print(metric.value + ", ");
        }
        System.out.println();
    }

    private class Metric {

        public String name;

        public double value;

        public Metric(String name, double value) {
            this.name = name;
            this.value = value;
        }

    }
}
