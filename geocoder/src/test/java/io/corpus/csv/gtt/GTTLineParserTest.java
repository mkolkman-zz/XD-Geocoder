package io.corpus.csv.gtt;

import java.text.ParseException;

import io.corpus.csv.CsvTweetParser;
import static org.junit.Assert.*;

import org.junit.*;

import core.language.document.tweet.Tweet;

public class GTTLineParserTest {

	@Test(expected=ParseException.class)
	public void testEmptyLineFails() throws ParseException {
		CsvTweetParser parser = new GTTTweetParser();
		parser.parse("");				
	}

	@Test(expected=ParseException.class) 
	public void testTooFewPartsFails() throws ParseException {
		CsvTweetParser parser = new GTTTweetParser();
		parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916");		
	}
	
	@Test
	public void testUserGetsCorrectlyParsed() throws ParseException {
		CsvTweetParser parser = new GTTTweetParser();
		Tweet tweet = parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916	message");
		assertEquals("USER_79321756", tweet.getUser());
	}
	
	@Test
	public void testDateGetsCorrectlyParsed() throws ParseException {
		CsvTweetParser parser = new GTTTweetParser();
		Tweet tweet = parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916	message");
		assertEquals(Long.parseLong("1267586126000"), tweet.getDate());
	}

	@Test
	public void testLatitudeAndLongitudeGetCorrectlyParsed() throws ParseException {
		CsvTweetParser parser = new GTTTweetParser();
		Tweet tweet = parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916	message");
		assertEquals(47.528139, tweet.getGeotag().getLatitude(), 0.00001);
		assertEquals(-122.197916, tweet.getGeotag().getLongitude(), 0.00001);
	}
	
	@Test 
	public void testMessageGetsCorrectlyParsed() throws ParseException {
		CsvTweetParser parser = new GTTTweetParser();
		Tweet tweet = parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916	message");
		assertEquals("message", tweet.getText());
	}
}
