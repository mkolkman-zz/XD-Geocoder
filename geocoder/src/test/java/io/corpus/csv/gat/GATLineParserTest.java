package io.corpus.csv.gat;

import core.language.word.Toponym;
import core.language.document.tweet.Tweet;
import java.text.ParseException;
import java.util.List;

import io.corpus.csv.CsvTweetParser;
import io.corpus.gat.GATTweetParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GATLineParserTest {

	@Test(expected=ParseException.class)
	public void testEmptyLineFails() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		parser.parse("");
	}

	@Test(expected=ParseException.class)
	public void testTooFewPartsFails() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		parser.parse("#Peace #Love #happynewyear  @ Green Hall http://t.co/jmmsOx07	tp{Green Hall[-19,-48]}tp	-18.92382498	-48.31919061	 - MG, Brasil	Brasilia");
	}

	@Test
	public void testMessageGetsParsedCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("#Peace #Love #happynewyear  @ Green Hall http://t.co/jmmsOx07	tp{Green Hall[-19,-48]}tp	-18.92382498	-48.31919061	 - MG, Brasil	Brasilia	Check it out!");
		assertEquals("#Peace #Love #happynewyear  @ Green Hall http://t.co/jmmsOx07", tweet.getText());
	}

	@Test
	public void testLatitudeAndLongitudeGetParsedCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("#Peace #Love #happynewyear  @ Green Hall http://t.co/jmmsOx07	tp{Green Hall[-19,-48]}tp	-18.92382498	-48.31919061	 - MG, Brasil	Brasilia	Check it out!");
		assertEquals(-18.92382498, tweet.getGeotag().getLatitude(), 0.00001);
		assertEquals(-48.31919061, tweet.getGeotag().getLongitude(), 0.00001);
	}

	@Test
	public void parseSingleToponymSetsToponymTextCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("#Peace #Love #happynewyear  @ Green Hall http://t.co/jmmsOx07	tp{Green Hall[-19,-48]}tp	-18.92382498	-48.31919061	 - MG, Brasil	Brasilia	Check it out!");
		List<Toponym> toponyms = tweet.getToponyms();
		assertEquals("Green Hall", toponyms.get(0).getText());
	}

	@Test
	public void capitalizedToponymTagsGetParsedCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("#Peace #Love #happynewyear  @ Green Hall http://t.co/jmmsOx07	TP{Green Hall[-19,-48]}TP	-18.92382498	-48.31919061	 - MG, Brasil	Brasilia	Check it out!");
		List<Toponym> toponyms = tweet.getToponyms();
		assertEquals("Green Hall", toponyms.get(0).getText());
	}

	@Test
	public void varyingToponymTagCasingGetParsedCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("#Peace #Love #happynewyear  @ Green Hall http://t.co/jmmsOx07	Tp{Green Hall[-19,-48]}tPtp{New York[-30,-18]}TP	-18.92382498	-48.31919061	 - MG, Brasil	Brasilia	Check it out!");
		List<Toponym> toponyms = tweet.getToponyms();
		assertEquals("Green Hall", toponyms.get(0).getText());
		assertEquals("New York", toponyms.get(1).getText());
	}

	@Test
	public void parseMultipleToponymSetsToponymTextsCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("#Peace #Love #happynewyear  @ Green Hall http://t.co/jmmsOx07	tp{Green Hall[-19,-48]}tptp{New York[-19,-48]}tp	-18.92382498	-48.31919061	 - MG, Brasil	Brasilia	Check it out!");
		List<Toponym> toponyms = tweet.getToponyms();
		assertEquals("Green Hall", toponyms.get(0).getText());
		assertEquals("New York", toponyms.get(1).getText());
	}

	@Test
	public void parseSingleToponymSetsToponymCoordinateCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("@Liam_Reggie forget about that! My beloved forest green are 5-0 away at Newport!	tp{Newport[52,-3]-1111111}tp	51.74845062	-2.32570438	Gloucestershire	London	Youth football coach with big ambitions");
		List<Toponym> toponyms = tweet.getToponyms();
		assertEquals(52, toponyms.get(0).getCoordinates().getLatitude(), 0.00001);
		assertEquals(-3, toponyms.get(0).getCoordinates().getLongitude(), 0.00001);
	}

	@Test
	public void parseMultipleToponymsSetsToponymCoordinatesCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();

		Tweet tweet = parser.parse("As usual very quiet environment here - (@ RBI colony, kharghar) http://t.co/HSGgKpqo	tp{Kharghar[19,73]8133395}tptp{RBI colony[19,73]}tp	19.03318263	73.07512896	Navi Mumbai , India	Mumbai	I only use *great* phones					null");

		List<Toponym> toponyms = tweet.getToponyms();
		assertEquals(19, toponyms.get(0).getCoordinates().getLatitude(), 0.00001);
		assertEquals(73, toponyms.get(0).getCoordinates().getLongitude(), 0.00001);
		assertEquals(19, toponyms.get(1).getCoordinates().getLatitude(), 0.00001);
		assertEquals(73, toponyms.get(1).getCoordinates().getLongitude(), 0.00001);
	}

	@Test
	public void parseSingleGeonamesAnnotationGetsParsedCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("@Liam_Reggie forget about that! My beloved forest green are 5-0 away at Newport!	tp{Newport[52,-3]1111111}tp	51.74845062	-2.32570438	Gloucestershire	London	Youth football coach with big ambitions");
		List<Toponym> toponyms = tweet.getToponyms();
		assertEquals("1111111", toponyms.get(0).getGeonamesIds().get(0));
	}

	@Test
	public void parseMultipleGeonamesAnnotationGetsParsedCorrectly() throws ParseException {
		CsvTweetParser parser = new GATTweetParser();
		Tweet tweet = parser.parse("@Liam_Reggie forget about that! My beloved forest green are 5-0 away at Newport!	tp{Newport[52,-3]1111111,1345}tp	51.74845062	-2.32570438	Gloucestershire	London	Youth football coach with big ambitions");
		List<Toponym> toponyms = tweet.getToponyms();
		assertEquals("1111111", toponyms.get(0).getGeonamesIds().get(0));
		assertEquals("1345", toponyms.get(0).getGeonamesIds().get(1));
	}
}
