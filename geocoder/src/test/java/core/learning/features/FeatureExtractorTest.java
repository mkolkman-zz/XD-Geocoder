package core.learning.features;

import core.geo.LocationGazetteer;
import core.geo.geonames.GeonamesLocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.tokenizer.WordTokenizer;
import core.learning.LearningInstance;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

public class FeatureExtractorTest {

    private static final String GAZETTEER_INPUT_FILE = "D:\\Studie\\gazetteers\\geonames\\allCountries.txt";

    @Test
    public void testWithEmptyDictionary() throws FileNotFoundException {
        WordTokenizer tokenizer = new WordTokenizer(new PTBTokenizer(new StringReader("This is my example sentence."), new WordTokenFactory(), ""));
        Dictionary dictionary = new HashMapDictionary();
        CsvLocationParser lineParser = CsvGazetteerReader.CsvLocationParserFactory.getCsvLocationParser("Geonames");
        LocationGazetteer gazetteer = new GeonamesLocationGazetteer(new CsvGazetteerReader(new BufferedReader(new FileReader(GAZETTEER_INPUT_FILE)), lineParser), lineParser);
        gazetteer.loadLocations();

        FeatureExtractor featureExtractor = new FeatureExtractor(tokenizer, dictionary, gazetteer);

        List<LearningInstance> learningInstances = featureExtractor.getLearningInstances();

        for(LearningInstance instance : learningInstances) {
            System.out.println(instance.toString());
        }
    }
}
