package core.geo.geonames;

import core.geo.Location;
import core.geo.LocationGazetteer;
import io.gazetteer.csv.CsvGazetteerReader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeonamesLocationGazetteer implements LocationGazetteer {

    private final CsvGazetteerReader gazetteerReader;
    private Map<String, List<Location>> locations = new HashMap<String, List<Location>>();

    public GeonamesLocationGazetteer(CsvGazetteerReader gazetteerReader) {
        this.gazetteerReader = gazetteerReader;
    }

    @Override
    public void loadLocations() {
        System.out.println("Loading geonames gazetteer...");
        try {
            int count = 0;
            while(gazetteerReader.hasNextLocation()) {
                if(count % 100000 == 0) {
                    System.out.println("Loaded " + count + " locations from Geonames. Used " + Runtime.getRuntime().totalMemory() / (1024 * 1024) + " MB of memory.");
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
        return locations.containsKey(toponym);
    }

    @Override
    public List<Location> getLocations(String toponym) {
        return locations.get(toponym);
    }

    @Override
    public void add(Location location) {
        String toponym = location.getName();
        if( ! locations.containsKey(toponym)) {
            locations.put(toponym, new ArrayList<Location>());
        }
        locations.get(toponym).add(location);
    }

}
