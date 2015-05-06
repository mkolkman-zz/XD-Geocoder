package io.corpus.csv;

import java.io.*;
import java.text.ParseException;

import core.language.document.Document;
import io.corpus.gat.GATTweetParser;
import io.corpus.gtt.GTTTweetParser;
import io.corpus.CorpusReader;

public class CsvCorpusReader implements CorpusReader {

	private BufferedReader reader;
	private CsvTweetParser parser;
	private String currentLine;

	public CsvCorpusReader(BufferedReader reader, CsvTweetParser parser) {
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
		public static CsvTweetParser getCsvLineParser(String type) {
			if(type.equals("GAT"))
				return new GATTweetParser();
			if(type.equals("GTT"))
				return new GTTTweetParser();

			throw new IllegalArgumentException();
		}
	}
	
}
