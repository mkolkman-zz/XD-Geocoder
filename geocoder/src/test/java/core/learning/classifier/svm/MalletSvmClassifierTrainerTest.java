package core.learning.classifier.svm;

import ca.uwo.csd.ai.nlp.kernel.CustomKernel;
import ca.uwo.csd.ai.nlp.kernel.LinearKernel;
import ca.uwo.csd.ai.nlp.kernel.RBFKernel;
import ca.uwo.csd.ai.nlp.libsvm.svm_parameter;
import core.geo.LocationGazetteer;
import core.geo.geonames.GeonamesLocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.document.news.Article;
import core.language.labeller.LglBinaryLabeller;
import core.language.labeller.LglLabeller;
import core.language.pos.stanford.StanfordPosTagger;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import core.learning.*;
import core.learning.classifier.Classifier;
import core.learning.evaluator.Evaluator;
import core.learning.evaluator.lgl.LglBinaryEvaluator;
import core.learning.evaluator.lgl.LglEvaluator;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MalletSvmClassifierTrainerTest {

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
    public static final String EXPERIMENT_RESULTS_FILE = "/home/mike/data/xd-results.csv";

    private List<LearningInstance> trainingInstances;
    private List<LearningInstance> testInstances;

    @Test
    public void testTraining_LGLCorpusSample() throws IOException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException, ClassNotFoundException {

        List<LearningInstance> learningInstances = getLearningInstances();

        populateTrainingAndTestInstanceLists(learningInstances);

        PrintStream out = new PrintStream(new FileOutputStream(EXPERIMENT_RESULTS_FILE));
        System.setOut(out);

        double[] gamma_list = new double[]{0.6, 0.75, 0.9};
        double[] cost_list = new double[]{0.6, 0.75, 0.9};
        double[] weight_b_list = new double[]{50, 60, 70};
        double[] weight_i_list = new double[]{165};
        for (double gamma : gamma_list) {
            for (double cost : cost_list) {
                for (double weight_b : weight_b_list) {
                    for(double weight_i : weight_i_list) {
                        System.out.print(gamma + ", ");
                        System.out.print(cost + ", ");
                        System.out.print(weight_b + ", ");
                        System.out.print(weight_i + ", ");
                        System.out.print(1.0 + ", ");
                        printPerformanceMetricsWithoutHeader(doExperiment(gamma, cost, weight_b, weight_i, 1.0));
                    }
                }
            }
        }
    }

    private List<LearningInstance> getLearningInstances() throws IOException, ClassNotFoundException, ParseException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        List<LearningInstance> learningInstances;
        if(fileExists(FEATURE_FILE)) {
             learningInstances = readLearningInstances(FEATURE_FILE);
        } else {
            learningInstances = extractLearningInstances();
            writeLearningInstances(learningInstances, FEATURE_FILE);
        }
        return learningInstances;
    }

    private boolean fileExists(String file) {
        File instancesFile = new File(file);
        return instancesFile.exists();
    }


    private List<LearningInstance> readLearningInstances(String featureFile) throws IOException, ClassNotFoundException {
        List<LearningInstance> learningInstances;
        FileInputStream fileInput = new FileInputStream(featureFile);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        learningInstances = (List<LearningInstance>) objectInput.readObject();
        return learningInstances;
    }

    private void writeLearningInstances(List<LearningInstance> learningInstances, String featureFile) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(featureFile);
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(learningInstances);
        objectOutput.close();
        fileOutput.close();
    }

    private List<LearningInstance> extractLearningInstances() throws ParseException, IOException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ClassNotFoundException {
        CorpusReader corpusReader = getCorpusReader(LGL_CORPUS_FILE);

        MaxentTagger tagger = new MaxentTagger(getClass().getResource(ENGLISH_TAGGER_MODEL).toString());

        FeatureExtractor featureExtractor = new FeatureExtractor(getDictionary(LGL_CORPUS_FILE), getGazetteer(GEONAMES_GAZETTEER_FILE));
        LearningInstanceExtractor learningInstanceExtractor = new LearningInstanceExtractor(featureExtractor);

        while(corpusReader.hasNextDocument()) {

            Article article = (Article) corpusReader.getNextDocument();
            Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(article.getText()), new WordTokenFactory(), ""));

//            Iterator<Word> labelledWords = new LglLabeller(tokenizedWords, article.getToponyms());
            Iterator<Word> labelledWords = new LglBinaryLabeller(tokenizedWords, article.getToponyms());
            Iterator<Word> posTaggedWords = new StanfordPosTagger(labelledWords, tagger, new StanfordTransformer());

            learningInstanceExtractor.extractLearningInstances(posTaggedWords);
        }

        return learningInstanceExtractor.getLearningInstances();
    }

    private LGLCorpusReader getCorpusReader(String corpusFile) throws XMLStreamException, FileNotFoundException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(corpusFile), XMLStreamReaderType.WOODSTOX);
        return new LGLCorpusReader(xmlStreamReader);
    }

    private Dictionary getDictionary(String corpusFile) throws XMLStreamException, FileNotFoundException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        CorpusReader corpusReader = getCorpusReader(corpusFile);
        Dictionary dictionary = new HashMapDictionary();
        while(corpusReader.hasNextDocument()) {
            Article article = (Article) corpusReader.getNextDocument();
            Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(article.getText()), new WordTokenFactory(), ""));
            Iterator<Word> labelledWords = new LglLabeller(tokenizedWords, article.getToponyms());
            dictionary.load(labelledWords);
        }
        return dictionary;
    }

    private GeonamesLocationGazetteer getGazetteer(String gazetteerFile) throws FileNotFoundException {
        CsvLocationParser lineParser = CsvGazetteerReader.CsvLocationParserFactory.getCsvLocationParser("Geonames");
        CsvGazetteerReader gazetteerReader = new CsvGazetteerReader(new BufferedReader(new FileReader(gazetteerFile)), lineParser);
        return new GeonamesLocationGazetteer(gazetteerReader, lineParser);
    }

    private void populateTrainingAndTestInstanceLists(List<LearningInstance> learningInstances) {
        int splitIndex = learningInstances.size() / 10 * 8;
        Collections.shuffle(learningInstances);
        trainingInstances = new ArrayList<LearningInstance>(learningInstances.subList(0, splitIndex));
        testInstances = new ArrayList<LearningInstance>(learningInstances.subList(splitIndex + 1, learningInstances.size() - 1));
    }

    private List<Metric> doExperiment(double gamma, double cost, double weight_b, double weight_i, double weight_o) {
        MalletSvmClassifierTrainer trainer = getClassifierTrainer(gamma, cost);

        svm_parameter params = getTrainingParams(weight_b, weight_i, weight_o);

        Classifier classifier = trainer.train(trainingInstances, params);

//        Evaluator evaluator = new LglEvaluator(classifier.trial(testInstances));
        Evaluator evaluator = new LglBinaryEvaluator(classifier.trial(testInstances));

        return evaluator.getPerformanceMetrics();
    }

    private MalletSvmClassifierTrainer getClassifierTrainer(double gamma, double cost) {
        CustomKernel kernel = getSvmKernel(gamma, cost);
        MalletInstanceListTransformer instanceListTransformer = new MalletInstanceListTransformer(new MalletInstanceTransformer(new MalletFeatureVectorTransformer()));

        return new MalletSvmClassifierTrainer(kernel, instanceListTransformer);
    }

    private CustomKernel getSvmKernel(double gamma, double cost) {
        List<CustomKernel> kernels = new ArrayList<CustomKernel>();
        kernels.add(new LinearKernel());
        svm_parameter kernelParams = new svm_parameter();
        kernelParams.gamma = gamma;
        kernelParams.C = cost;
        kernels.add(new RBFKernel(kernelParams));

        return new ca.uwo.csd.ai.nlp.kernel.CompositeKernel(kernels);
    }

    private svm_parameter getTrainingParams(double weightB, double weightI, double weightO) {
        svm_parameter params = new svm_parameter();
//        params.weight_label = new int[]{1, 2, 3};
//        params.weight = new double[]{weightB, weightO, weightI};
//        params.nr_weight = 3;
        params.weight_label = new int[]{1, 2};
        params.weight = new double[]{weightB, weightO};
        params.nr_weight = 2;
        return params;
    }

    private void printPerformanceMetricsWithoutHeader(List<Metric> metrics) {
        for (Metric metric : metrics) {
            System.out.print(metric.value + ", ");
        }
        System.out.println();
    }
}
