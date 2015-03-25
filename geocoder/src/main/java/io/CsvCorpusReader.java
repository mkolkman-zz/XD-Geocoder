package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.Toponym;
import core.Tweet;

public class CsvCorpusReader {

	private BufferedReader reader;
	private String line;
	private int lineCount = 0;
	private CsvLineParser parser;
	private List<Tweet> tweets = new ArrayList<Tweet>();

	/**
	 * Reads and parses a GATCorpus and echos some info 
	 * @param args
	 */
	public static void main(String[] args) {
		String[] argOptions = {"GAT", "GTT"};
		if(args.length != 3 || !Arrays.asList(argOptions).contains(args[0])) 
			printUsageInstructions();
		else {
			new CsvCorpusReader(CsvLineParserFactory.getCsvLineParser(args[0])).readFileAndPrintStatistics(args[1], args[2]);			
		}
	}	

	public CsvCorpusReader(CsvLineParser parser) {
		this.parser = parser;
	}
	
	private static void printUsageInstructions() {
		System.out.println("USAGE:");
		System.out.println("\tCsvCorpusReader <lineparser> <filepath>");
		System.out.println("");
		System.out.println("lineparser\tThe parser used to parse each line. Either 'GAT' or 'GTT'.");
		System.out.println("inputFile\tFull path to the GATCorpus csv file");
		System.out.println("outputFile\tFull path where the tgn corpus gets written");
	}
	
	public void readFileAndPrintStatistics(String inputFile, String outputFile) {
		readFile(inputFile);
		printStatistics();
		System.out.println("Corpus loaded.");
		try {
			System.out.println("Writing tgn corpus");
			new TgnCorpusWriter(new PrintWriter(outputFile), tweets).writeFile();
			System.out.println("Done writing tgn corpus");			
		} catch (FileNotFoundException e) {
			System.err.println("Failed writing tgn corpus to " + outputFile);
		}
	}

	public void readFile(String filepath) {
		try {
			reader = new BufferedReader(new FileReader(filepath));
			parseFileContents();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseFileContents() throws IOException {
		while((line = reader.readLine()) != null){
			lineCount++;
			parseLine();
		}
	}

	private void parseLine() {
		try {
			tweets.add(parser.parse(line));
		} catch (ParseException e) {
			System.err.println("Incorrect format detected on line " + lineCount + ". Please check your input file.");
		}
	}

	private void printStatistics() {
		System.out.println("Tweet count: " + tweets.size());
		System.out.println("Toponym count: " + getToponymCount());
		System.out.println("GeoNames id count: " + getGeonamesIdCount());
		System.out.println("Geotagged tweet count: " + getGeotaggedTweetCount());
	}
	
	private int getToponymCount() {
		int count = 0;
		for(Tweet t : tweets) {
			count += t.getToponyms().size();
		}
		return count;
	}
	
	private int getGeonamesIdCount() {
		int count = 0;
		for(Tweet t : tweets) {
			List<Toponym> toponyms = t.getToponyms();
			for(Toponym top : toponyms) {
				if(top.getGeonamesIds() != null)
					count += top.getGeonamesIds().size();
			}
		}
		return count;
	}
	
	private int getGeotaggedTweetCount() {
		int count = 0;
		for(Tweet t : tweets) {
			if(t.getGeotag() != null)
				count++;
		}
		return count;
	}
	
	private static class CsvLineParserFactory {
		public static CsvLineParser getCsvLineParser(String type) {
			if(type.equals("GAT"))
				return new GATLineParser();
			if(type.equals("GTT"))
				return new GTTLineParser();
			
			throw new IllegalArgumentException();
		}
		
	}
	
}
