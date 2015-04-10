package core.learning.features;

import core.geo.LocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.tokenizer.WordTokenizer;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.learning.LearningInstance;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.List;

public class FeatureExtractorTest {

    private static final String GAZETTEER_INPUT_FILE = "D:\\Studie\\gazetteers\\geonames\\allCountries.txt";

    @Test
    public void testWithEmptyDictionary() throws FileNotFoundException {
        WordTokenizer tokenizer = new StanfordWordTokenizer(new PTBTokenizer(new StringReader("This is my example sentence."), new WordTokenFactory(), ""));
        Dictionary dictionary = new HashMapDictionary();
        CsvLocationParser lineParser = CsvGazetteerReader.CsvLocationParserFactory.getCsvLocationParser("Geonames");
        LocationGazetteer gazetteer = new DummyLocationGazetteer();

        FeatureExtractor featureExtractor = new FeatureExtractor(tokenizer, dictionary, gazetteer);

        List<LearningInstance> learningInstances = featureExtractor.getLearningInstances();

        for(LearningInstance instance : learningInstances) {
            System.out.println(instance.toString());
        }
    }


}
