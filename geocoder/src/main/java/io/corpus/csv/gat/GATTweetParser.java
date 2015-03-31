package io.corpus.csv.gat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import core.language.toponym.Toponym;
import core.language.document.tweet.Tweet;
import core.geo.Coordinate;
import io.corpus.csv.CsvTweetParser;

public class GATTweetParser implements CsvTweetParser {

	private static final int TABS_PER_LINE = 6;

	@Override
	public Tweet parse(String input) throws ParseException {
		if(input.equals(""))
			throw new ParseException("Empty line.", 0);
		String[] parts = input.split("\t");
		int tabCount = parts.length - 1;
		if(tabCount < TABS_PER_LINE)
			throw new ParseException("Tabs per line. Expected: " + TABS_PER_LINE + ". Actual: " + parts.length, 0);		
		
		Tweet tweet = new Tweet();		
		tweet.setText(parts[0]);
		tweet.setToponyms(parseToponyms(parts[1]));
		tweet.setGeotag(new Coordinate(parts[2], parts[3]));
		
		return tweet;
	}
	
	/**
	 * Parses input of the form "tp{#Warren[41,-75]8299577,5106052}tptp{NJ[41,-75]5101760}tp" 
	 * @param input
	 * @return
	 */
	private List<Toponym> parseToponyms(String input) {
		String[] toponyms = input.trim().replaceAll("(?i)^tp\\{", "").replaceAll("(?i)\\}tp$", "").split("(?i)\\}tptp\\{");
		List<Toponym> toponymList = new ArrayList<Toponym>();
		for(String top : toponyms) {
			toponymList.add(parseToponym(top));
		}
		return toponymList;
	}

	/**
	 * Parses inputs of the form "Warren[41,-75]8299577,5106052"
	 * @param input
	 * @return
	 */
	private Toponym parseToponym(String input) {
		String[] parts = input.split("\\[");		
		Toponym result = new Toponym();
		result.setText(parts[0]);
		String[] geoAnnotation = parts[1].split("\\]");
		result.setCoordinates(parseGeoAnnotationCoordinates(geoAnnotation[0]));
		if(geoAnnotation.length > 1)
			result.setGeonamesIds(parseGeoAnnotationGeonamesIds(geoAnnotation[1]));			
		return result;
	}
	
	/**
	 * Parses input of the form "41,-75"
	 * @param input
	 * @return
	 */
	private Coordinate parseGeoAnnotationCoordinates(String input) {
		String[] latlong = input.split(",");
		Coordinate coordinate = new Coordinate(latlong[0], latlong[1]);
		return coordinate;
	}
	
	/**
	 * Parses input of the form "3471039,6323064"
	 * @param input
	 * @return
	 */
	private List<String> parseGeoAnnotationGeonamesIds(String input) {
		String[] ids = input.split(",");
		List<String> result = new ArrayList<String>();
		for(String id : ids) {
			result.add(id);
		}
		return result;
	}

}
