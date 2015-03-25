package io.CSV;

import java.text.ParseException;

import core.document.tweet.Tweet;

public interface CsvLineParser {

	Tweet parse(String input) throws ParseException;

}
