package io.CSV;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import core.document.Document;
import core.toponym.Toponym;
import core.document.tweet.Tweet;
import io.CSV.GAT.GATLineParser;
import io.CSV.GTT.GTTLineParser;
import io.CorpusReader;

public class CsvCorpusReader implements CorpusReader {

	private BufferedReader reader;
	private CsvLineParser parser;
	private String currentLine;

	public CsvCorpusReader(BufferedReader reader, CsvLineParser parser) {
		this.reader = reader;
		this.parser = parser;
	}

	@Override
	public boolean hasNextDocument() {
		try {
			currentLine = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return currentLine != null;
	}

	@Override
	public Document getNextDocument() throws ParseException {
		return parser.parse(currentLine);
	}

	public static class CsvLineParserFactory {
		public static CsvLineParser getCsvLineParser(String type) {
			if(type.equals("GAT"))
				return new GATLineParser();
			if(type.equals("GTT"))
				return new GTTLineParser();
			
			throw new IllegalArgumentException();
		}
	}
	
}
