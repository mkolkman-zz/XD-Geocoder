package core.geo.geonames;

import core.geo.LocationGazetteer;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GeonamesLocationGazetteerTest {

    private static final String INPUT_FILE = "D:\\Users\\s0201154\\gazetteers\\Geonames\\allCountries.txt";

    @Test
    public void testLoading() throws FileNotFoundException {
        CsvLocationParser lineParser = CsvGazetteerReader.CsvLocationParserFactory.getCsvLocationParser("Geonames");
        LocationGazetteer locations = new GeonamesLocationGazetteer(new CsvGazetteerReader(new BufferedReader(new FileReader(INPUT_FILE)), lineParser));
        locations.loadLocations();
    }
}
