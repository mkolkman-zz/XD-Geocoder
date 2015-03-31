package core.geo;

import java.util.List;

public interface LocationGazetteer {

    void loadLocations();

    boolean contains(String toponym);

    List<Location> getLocations(String toponym);

    void add(Location location);

}
