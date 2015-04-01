package core.geo.geonames;

import core.geo.Location;
import core.geo.LocationGazetteer;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;
import io.gazetteer.csv.geonames.GeonamesLocationParser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeonamesLocationGazetteer implements LocationGazetteer {

    private final CsvGazetteerReader gazetteerReader;
    private CsvLocationParser locationParser;
    private Map<String, List<String>> candidates = new HashMap<String, List<String>>();

    public GeonamesLocationGazetteer(CsvGazetteerReader gazetteerReader, CsvLocationParser locationParser) {
        this.gazetteerReader = gazetteerReader;
        this.locationParser = locationParser;
    }

    @Override
    public void loadLocations() {
        System.out.println("Loading geonames gazetteer...");
        try {
            int count = 0;
            while(gazetteerReader.hasNextLocation()) {
                if(count % 100000 == 0) {
                    System.out.println("Loaded " + count + " candidates from Geonames. Used " + Runtime.getRuntime().totalMemory() / (1024 * 1024) + " MB of memory.");
                }
                add(gazetteerReader.getNextLocation());
                count++;
            }
        } catch (ParseException e) {
            System.err.println("Incorrect input format detected. Please check your input file.");
        }
        System.out.println("Finished loading geonames gazetteer!");
    }

    @Override
    public boolean contains(String toponym) {
        return candidates.containsKey(toponym);
    }

    @Override
    public List<Location> getLocations(String toponym) {
        List<String> raws = candidates.get(toponym);
        List<Location> result = new ArrayList<Location>();
        for (String raw : raws) {
            try {
                result.add(locationParser.parse(raw));
            } catch (ParseException e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public void add(Location location) {
        String toponym = location.getName();
        if( ! candidates.containsKey(toponym)) {
            candidates.put(toponym, new ArrayList<String>());
        }
        candidates.get(toponym).add(location.getRaw());
    }

}
