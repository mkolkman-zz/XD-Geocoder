package io.corpus.csv;

import java.text.ParseException;

import core.language.document.tweet.Tweet;

public interface CsvTweetParser {

	Tweet parse(String input) throws ParseException;

}
