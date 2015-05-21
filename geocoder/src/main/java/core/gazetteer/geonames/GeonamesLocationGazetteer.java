package core.gazetteer.geonames;

import core.gazetteer.Location;
import core.gazetteer.LocationGazetteer;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.THashMap;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeonamesLocationGazetteer implements LocationGazetteer {

    private static final int LOCATION_TOTAL = 10000000;

    private final CsvGazetteerReader gazetteerReader;
    private CsvLocationParser locationParser;

    private List<String> locations;

    private Map<String, TIntArrayList> toponymToCandidatesIndex;
    private Map<String, TIntArrayList> partialToponymToCandidatesIndex;
    private Map<String, TIntArrayList> initialToponymToCandidatesIndex;

    public GeonamesLocationGazetteer(CsvGazetteerReader gazetteerReader, CsvLocationParser locationParser) {
        this.gazetteerReader = gazetteerReader;
        this.locationParser = locationParser;
        loadLocations();
    }

    private void loadLocations() {
        System.out.println("Loading geonames gazetteer...");

        locations = new ArrayList<String>();
        toponymToCandidatesIndex = new THashMap<String, TIntArrayList>();
        partialToponymToCandidatesIndex = new THashMap<String, TIntArrayList>();
        initialToponymToCandidatesIndex = new THashMap<String, TIntArrayList>();

        try {
            int count = 0;
            while(gazetteerReader.hasNextLocation()) {
                if(count % 1000000 == 0) {
                    double percentage = ( ( (double) count / LOCATION_TOTAL ) * 100);
                    System.out.println( percentage + "%");
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
    public boolean contains(String word) {
        return toponymToCandidatesIndex.containsKey(word);
    }

    public boolean containsPartial(String partial) {
        return partialToponymToCandidatesIndex.containsKey(partial);
    }

    @Override
    public boolean containsInitial(String text) {
        return initialToponymToCandidatesIndex.containsKey(text);
    }

    @Override
    public List<Location> getLocations(String toponym) throws Exception {
        throw new Exception("Not yet implemented");
    }

    @Override
    public void add(Location location) {
        locations.add(location.getRaw());
        Integer locationIndex = locations.size() - 1;

        String toponym = location.getName();
        if( ! toponymToCandidatesIndex.containsKey(toponym)) {
            toponymToCandidatesIndex.put(toponym, new TIntArrayList(1));
        }
        toponymToCandidatesIndex.get(toponym).add(locationIndex);

        String[] toponymParts = toponym.split(" ");

        if( ! initialToponymToCandidatesIndex.containsKey(toponymParts[0])) {
            initialToponymToCandidatesIndex.put(toponymParts[0], new TIntArrayList(1));
        }
        initialToponymToCandidatesIndex.get(toponymParts[0]).add(locationIndex);

        for (String toponymPart : toponymParts) {
            if( ! partialToponymToCandidatesIndex.containsKey(toponymPart)) {
                partialToponymToCandidatesIndex.put(toponymPart, new TIntArrayList(1));
            }
            partialToponymToCandidatesIndex.get(toponymPart).add(locationIndex);
        }
    }

}
