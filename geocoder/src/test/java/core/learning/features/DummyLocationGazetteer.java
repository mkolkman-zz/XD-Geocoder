package core.learning.features;

import core.gazetteer.Location;
import core.gazetteer.LocationGazetteer;

import java.util.List;

public class DummyLocationGazetteer implements LocationGazetteer {

    @Override
    public boolean contains(String toponym) {
        return false;
    }

    @Override
    public boolean containsPartial(String text) {
        return false;
    }

    @Override
    public boolean containsInitial(String text) {
        return false;
    }

    @Override
    public List<Location> getLocations(String toponym) {
        return null;
    }

    @Override
    public void add(Location location) {

    }
}
