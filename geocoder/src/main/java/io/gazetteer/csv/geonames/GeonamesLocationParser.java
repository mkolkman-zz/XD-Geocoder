package io.gazetteer.csv.geonames;

import core.geo.Coordinate;
import core.geo.Location;
import io.gazetteer.csv.CsvLocationParser;

import java.text.ParseException;
import java.util.Arrays;

public class GeonamesLocationParser implements CsvLocationParser {

    private static final int TABS_PER_LINE = 18;

    @Override
    public Location parse(String input) throws ParseException {
        if(input.equals(""))
            throw new ParseException("Empty line.", 0);

        String[] parts = input.split("\t");
        int tabCount = parts.length - 1;
        if(tabCount < TABS_PER_LINE)
            throw new ParseException("Tabs per line. Expected: " + TABS_PER_LINE + ". Actual: " + tabCount, 0);

        Location location = new Location();
        location.setGeonameId(parts[0]);
        location.setName(parts[1]);
//        location.setAsciiName(parts[2]);
//        location.setAlternateNames(Arrays.asList(parts[3].split(",")));
//        location.setCoordinate(new Coordinate(parts[4], parts[5]));
//        location.setFeatureClass(parts[6]);
//        location.setFeatureCode(parts[7]);
//        location.setCountryCode(parts[8]);
//        location.setCC2(parts[9]);
//        location.setAdmin1Code(parts[10]);
//        location.setAdmin2Code(parts[11]);
//        location.setAdmin3Code(parts[12]);
//        location.setAdmin4Code(parts[13]);
//        location.setPopulation(Long.parseLong(parts[14]));
//        location.setElevation(parts[15]);
//        location.setDEM(parts[16]);
//        location.setTimezone(parts[17]);
//        location.setModificationDate(parts[18]);

        return location;
    }

}
