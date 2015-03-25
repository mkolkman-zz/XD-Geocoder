package io;

import java.text.ParseException;

import core.Tweet;

public interface CsvLineParser {

	public Tweet parse(String input) throws ParseException;

}
