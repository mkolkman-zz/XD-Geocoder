package core.gazetteer;

import java.util.List;

public interface LocationGazetteer {

    boolean contains(String toponym);

    boolean containsPartial(String text);

    boolean containsInitial(String text);

    List<Location> getLocations(String toponym) throws Exception;

    void add(Location location);
}
