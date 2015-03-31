package io.gazetteer.csv;

import core.geo.Location;

import java.text.ParseException;

public interface CsvLocationParser {

	Location parse(String input) throws ParseException;

}
