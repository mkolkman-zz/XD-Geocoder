package core.learning.classifier.crf;

import core.geo.LocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.tokenizer.WordTokenizer;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.learning.LearningInstance;
import core.learning.classifier.Classifier;
import core.learning.classifier.ClassifierTrainer;
import core.learning.features.DummyLocationGazetteer;
import core.learning.features.FeatureExtractor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import mallet.transformers.MalletFeatureVectorTransformer;
import mallet.transformers.MalletInstanceListTransformer;
import mallet.transformers.MalletInstanceTransformer;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.List;

public class MalletCrfClassifierTrainerTest {

    private static final String GAZETTEER_INPUT_FILE = "D:\\Studie\\gazetteers\\geonames\\allCountries.txt";

    @Test
    public void testTraining_shortInstanceList() throws FileNotFoundException {
        WordTokenizer tokenizer = new StanfordWordTokenizer(new PTBTokenizer(new StringReader("The flight from Amsterdam/B-TOP to Washington/B-TOP D.C./I-TOP took seven hours."), new WordTokenFactory(), ""));
        Dictionary dictionary = new HashMapDictionary();
        LocationGazetteer gazetteer = new DummyLocationGazetteer();

        FeatureExtractor featureExtractor = new FeatureExtractor(tokenizer, dictionary, gazetteer);

        List<LearningInstance> learningInstances = featureExtractor.getLearningInstances();

        ClassifierTrainer trainer = new MalletCrfClassifierTrainer(new MalletInstanceListTransformer(new MalletInstanceTransformer(new MalletFeatureVectorTransformer())));
        Classifier classifier = trainer.train(learningInstances);

    }
}
