package core.gazetteer.geonames;

import core.gazetteer.Location;
import core.gazetteer.LocationGazetteer;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class GeonamesLocationGazetteerTest {

    private static final String INPUT_FILE = "D:\\Studie\\gazetteers\\geonames\\allCountries.txt";

    private LocationGazetteer locations;

    @Test
    public void testLoading() throws Exception {
        CsvLocationParser lineParser = CsvGazetteerReader.CsvLocationParserFactory.getCsvLocationParser("Geonames");
        locations = new GeonamesLocationGazetteer(new CsvGazetteerReader(new BufferedReader(new FileReader(INPUT_FILE)), lineParser), lineParser);

        List<Location> result = locations.getLocations("Enschede");

        for(Location location : result) {
            System.out.println(location.getGeonameId());
        }
    }
}
