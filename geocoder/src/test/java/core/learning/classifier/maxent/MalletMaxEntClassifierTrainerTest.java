package core.learning.classifier.maxent;

import cc.mallet.classify.Trial;
import core.geo.LocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.document.news.Article;
import core.language.labeller.AnnotatedWordLabeller;
import core.language.labeller.LglLabeller;
import core.language.pos.stanford.StanfordPosTagger;
import core.language.tokenizer.WordTokenizer;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import core.learning.Label;
import core.learning.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import core.learning.features.DummyLocationGazetteer;
import core.learning.features.FeatureExtractor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import io.corpus.CorpusReader;
import io.corpus.xml.XMLStreamReader;
import io.corpus.xml.XMLStreamReaderFactory;
import io.corpus.xml.XMLStreamReaderType;
import io.corpus.xml.lgl.LGLCorpusReader;
import mallet.transformers.MalletFeatureVectorTransformer;
import mallet.transformers.MalletInstanceListTransformer;
import mallet.transformers.MalletInstanceTransformer;
import org.junit.Test;
import stanford.transformers.StanfordTransformer;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MalletMaxEntClassifierTrainerTest {

    private static final String LGL_MULTIPLE_ARTICLES_TEST_FILE = new File("").getAbsolutePath() + "\\src\\test\\resources\\xml\\lgl\\lgl-multiple-articles-test.xml";
    private static final String LGL_ARTICLES_FILE = "D:\\Studie\\corpora\\LGL\\articles.xml";

    public static final String ENGLISH_TAGGER_MODEL = "/core/language/pos/english-left3words-distsim.tagger";

    @Test
    public void testTraining_shortInstanceList() throws FileNotFoundException {
        WordTokenizer tokenizer = new StanfordWordTokenizer(new PTBTokenizer(new StringReader("The flight from Amsterdam-B-TOP to Washington-B-TOP D.C.-I-TOP took seven hours."), new WordTokenFactory(), ""));
        AnnotatedWordLabeller labeller = new AnnotatedWordLabeller(tokenizer);
        Dictionary dictionary = new HashMapDictionary();
        LocationGazetteer gazetteer = new DummyLocationGazetteer();

        FeatureExtractor featureExtractor = new FeatureExtractor(labeller, dictionary, gazetteer);

        List<LearningInstance> learningInstances = featureExtractor.getLearningInstances();

        ClassifierTrainer trainer = new MalletMaxEntClassifierTrainer(new MalletInstanceListTransformer(new MalletInstanceTransformer(new MalletFeatureVectorTransformer())));
        Classifier classifier = trainer.train(learningInstances);

    }

    @Test
    public void testTraining_LGLCorpusSample() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        Dictionary dictionary = new HashMapDictionary();
        LocationGazetteer gazetteer = new DummyLocationGazetteer();

        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_MULTIPLE_ARTICLES_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        List<LearningInstance> learningInstances = extractLearningInstances(dictionary, gazetteer, corpusReader);

        ClassifierTrainer trainer = new MalletMaxEntClassifierTrainer(new MalletInstanceListTransformer(new MalletInstanceTransformer(new MalletFeatureVectorTransformer())));
        Classifier classifier = trainer.train(learningInstances);

        Trial trial = classifier.trial(learningInstances);

        System.out.println("Accuracy: " + trial.getAccuracy());
    }

    @Test
    public void testTraining_LGLCorpus() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_ARTICLES_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);
        Dictionary dictionary = new HashMapDictionary();
//        dictionary.load(corpusReader);

        LocationGazetteer gazetteer = new DummyLocationGazetteer();

        xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_ARTICLES_FILE), XMLStreamReaderType.WOODSTOX);
        corpusReader = new LGLCorpusReader(xmlStreamReader);
        List<LearningInstance> learningInstances = extractLearningInstances(dictionary, gazetteer, corpusReader);

        int splitIndex = learningInstances.size() / 10 * 8;

        List<LearningInstance> trainingInstances = new ArrayList<LearningInstance>(learningInstances.subList(0, splitIndex));
        List<LearningInstance> testInstances = new ArrayList<LearningInstance>(learningInstances.subList(splitIndex + 1, learningInstances.size() - 1));

        ClassifierTrainer trainer = new MalletMaxEntClassifierTrainer(new MalletInstanceListTransformer(new MalletInstanceTransformer(new MalletFeatureVectorTransformer())));
        Classifier classifier = trainer.train(trainingInstances);

        Trial trial = classifier.trial(trainingInstances);
        printTrial(trial);
    }

    private void printLearningInstances(List<LearningInstance> learningInstances) {
        for (LearningInstance learningInstance : learningInstances) {
            System.out.println(learningInstance.toString());
        }
    }

    private List<LearningInstance> extractLearningInstances(Dictionary dictionary, LocationGazetteer gazetteer, CorpusReader corpusReader) throws ParseException {
        List<LearningInstance> learningInstances = new ArrayList<LearningInstance>();
        while(corpusReader.hasNextDocument()) {
            Article article = (Article) corpusReader.getNextDocument();
            Iterator<Word> words = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(article.getText()), new WordTokenFactory(), ""));
            words = new LglLabeller(words, article.getToponyms());
            MaxentTagger tagger = new MaxentTagger(getClass().getResource(ENGLISH_TAGGER_MODEL).toString());
            words = new StanfordPosTagger(words, tagger, new StanfordTransformer());


            FeatureExtractor featureExtractor = new FeatureExtractor(words, dictionary, gazetteer);
            learningInstances.addAll(featureExtractor.getLearningInstances());
        }
        return learningInstances;
    }

    private void printTrial(Trial trial) {
        System.out.println("Accuracy: " + trial.getAccuracy());

        System.out.println("Precision for 'START': " +  trial.getPrecision(Label.START_OF_TOPONYM.toString()));
        System.out.println("Precision for 'IN': " + trial.getPrecision(Label.IN_TOPONYM.toString()));
        System.out.println("Precision for 'NO': " + trial.getPrecision(Label.NO_TOPONYM.toString()));

        System.out.println("Recall for 'START': " +  trial.getRecall(Label.START_OF_TOPONYM.toString()));
        System.out.println("Recall for 'IN': " + trial.getRecall(Label.IN_TOPONYM.toString()));
        System.out.println("Recall for 'NO': " + trial.getRecall(Label.NO_TOPONYM.toString()));

        System.out.println("F1 for 'START': " +  trial.getF1(Label.START_OF_TOPONYM.toString()));
        System.out.println("F1 for 'IN': " + trial.getF1(Label.IN_TOPONYM.toString()));
        System.out.println("F1 for 'NO': " + trial.getF1(Label.NO_TOPONYM.toString()));
    }
}
