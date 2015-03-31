package io.gazetteer.csv.geonames;

import core.geo.Location;
import io.gazetteer.csv.CsvLocationParser;
import java.text.ParseException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeonamesLocationParserTest {

    @Test(expected=ParseException.class)
    public void testEmptyLineFails() throws ParseException {
        CsvLocationParser parser = new GeonamesLocationParser();
        parser.parse("");
    }

    @Test(expected=ParseException.class)
    public void testTooFewPartsFails() throws ParseException {
        CsvLocationParser parser = new GeonamesLocationParser();
        parser.parse("geonameId\tname\tasciiName\talternateNames\tlatitude\tlongitude\tfeatureClass\tfeatureCode\tcountryCode\tcc2\tadmin1Code\tadmin2Code\tadmin3Code\tadmin4Code\tpopulation\televation\tdem\ttimezone");
    }

    @Test
    public void testNameGetsParsedCorrectly() throws ParseException {
        CsvLocationParser parser = new GeonamesLocationParser();
        Location location = parser.parse("123\ttoponym\tasciiName\talternateNames\t20.023\t-15.456\tH\tabcd\tNL\tNL\tadmin1Code\tadmin2Code\tadmin3Code\tadmin4Code\t243158463215482\t20.45\t6482\ttimezone\tmodificationDate");
        assertEquals("toponym", location.getName());
    }

    @Test
    public void testAlternateNamesGetParsedCorrectly() throws ParseException {
        CsvLocationParser parser = new GeonamesLocationParser();
        Location location = parser.parse("123\ttoponym\tasciiName\talternate,names\t20.023\t-15.456\tH\tabcd\tNL\tNL\tadmin1Code\tadmin2Code\tadmin3Code\tadmin4Code\t243158463215482\t20.45\t6482\ttimezone\tmodificationDate");
        assertEquals("alternate", location.getAlternateNames().get(0));
        assertEquals("names", location.getAlternateNames().get(1));
    }

    @Test
    public void testCoordinateGetsParsedCorrectly() throws ParseException {
        CsvLocationParser parser = new GeonamesLocationParser();
        Location location = parser.parse("123\ttoponym\tasciiName\talternate,names\t20.023\t-15.456\tH\tabcd\tNL\tNL\tadmin1Code\tadmin2Code\tadmin3Code\tadmin4Code\t243158463215482\t20.45\t6482\ttimezone\tmodificationDate");
        assertEquals(20.023, location.getCoordinate().getLatitude(), 0.001);
        assertEquals(-15.456, location.getCoordinate().getLongitude(), 0.001);
    }

    @Test
    public void testPopulationGetsParsedCorrectly() throws ParseException {
        CsvLocationParser parser = new GeonamesLocationParser();
        Location location = parser.parse("123\ttoponym\tasciiName\talternate,names\t20.023\t-15.456\tH\tabcd\tNL\tNL\tadmin1Code\tadmin2Code\tadmin3Code\tadmin4Code\t243158463215482\t20.45\t6482\ttimezone\tmodificationDate");
        assertEquals(Long.parseLong("243158463215482"), location.getPopulation());
    }

}
