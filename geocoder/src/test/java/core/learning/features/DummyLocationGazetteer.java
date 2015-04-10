package core.learning.features;

import core.geo.Location;
import core.geo.LocationGazetteer;

import java.util.List;

public class DummyLocationGazetteer implements LocationGazetteer {

    @Override
    public void loadLocations() {
        return;
    }

    @Override
    public boolean contains(String toponym) {
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
