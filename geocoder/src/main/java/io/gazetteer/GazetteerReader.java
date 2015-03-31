package io.gazetteer;

import core.geo.Location;

import java.text.ParseException;

public interface GazetteerReader {

    boolean hasNextLocation() throws ParseException;

    Location getNextLocation() throws ParseException;
}
