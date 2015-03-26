package io.csv.gtt;

import java.text.ParseException;

import io.csv.gtt.GTTLineParser;
import io.csv.CsvLineParser;
import static org.junit.Assert.*;

import org.junit.*;

import core.document.tweet.Tweet;

public class GTTLineParserTest {

	@Test(expected=ParseException.class)
	public void testEmptyLineFails() throws ParseException {
		CsvLineParser parser = new GTTLineParser();
		parser.parse("");				
	}

	@Test(expected=ParseException.class) 
	public void testTooFewPartsFails() throws ParseException {
		CsvLineParser parser = new GTTLineParser();
		parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916");		
	}
	
	@Test
	public void testUserGetsCorrectlyParsed() throws ParseException {
		CsvLineParser parser = new GTTLineParser();
		Tweet tweet = parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916	message");
		assertEquals("USER_79321756", tweet.getUser());
	}
	
	@Test
	public void testDateGetsCorrectlyParsed() throws ParseException {
		CsvLineParser parser = new GTTLineParser();		
		Tweet tweet = parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916	message");
		assertEquals(Long.parseLong("1267586126000"), tweet.getDate());
	}

	@Test
	public void testLatitudeAndLongitudeGetCorrectlyParsed() throws ParseException {
		CsvLineParser parser = new GTTLineParser();
		Tweet tweet = parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916	message");
		assertEquals(47.528139, tweet.getGeotag().getLatitude(), 0.00001);
		assertEquals(-122.197916, tweet.getGeotag().getLongitude(), 0.00001);
	}
	
	@Test 
	public void testMessageGetsCorrectlyParsed() throws ParseException {
		CsvLineParser parser = new GTTLineParser();
		Tweet tweet = parser.parse("USER_79321756	2010-03-03T04:15:26	�T: 47.528139,-122.197916	47.528139	-122.197916	message");
		assertEquals("message", tweet.getText());
	}
}
