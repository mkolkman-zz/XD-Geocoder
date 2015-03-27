package io.csv;

import java.text.ParseException;

import core.language.document.tweet.Tweet;

public interface CsvLineParser {

	Tweet parse(String input) throws ParseException;

}
