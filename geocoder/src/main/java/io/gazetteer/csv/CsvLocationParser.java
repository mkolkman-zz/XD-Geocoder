package io.gazetteer.csv;

import core.gazetteer.Location;

import java.text.ParseException;

public interface CsvLocationParser {

	Location parse(String input) throws ParseException;

}
