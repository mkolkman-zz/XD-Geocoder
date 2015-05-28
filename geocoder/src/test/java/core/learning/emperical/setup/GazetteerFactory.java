package core.learning.emperical.setup;

import core.gazetteer.LocationGazetteer;
import core.gazetteer.geonames.GeonamesLocationGazetteer;
import core.language.dictionary.Dictionary;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GazetteerFactory {

    private String gazetteerFile;

    private LocationGazetteer gazetteer;

    public GazetteerFactory(String gazetteerFile) {
        this.gazetteerFile = gazetteerFile;
    }

    public LocationGazetteer getGazetteer() throws FileNotFoundException {
        if(this.gazetteer == null) {
            this.gazetteer = makeGazetteer();
        }
        return this.gazetteer;
    }

    public LocationGazetteer makeGazetteer() throws FileNotFoundException {
        CsvLocationParser lineParser = CsvGazetteerReader.CsvLocationParserFactory.getCsvLocationParser("Geonames");
        CsvGazetteerReader gazetteerReader = new CsvGazetteerReader(new BufferedReader(new FileReader(gazetteerFile)), lineParser);
        return new GeonamesLocationGazetteer(gazetteerReader, lineParser);
    }
}
