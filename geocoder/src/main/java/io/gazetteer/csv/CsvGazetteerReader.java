package io.gazetteer.csv;

import java.io.*;
import java.text.ParseException;

import core.gazetteer.Location;
import io.gazetteer.GazetteerReader;
import io.gazetteer.csv.geonames.GeonamesLocationParser;

public class CsvGazetteerReader implements GazetteerReader {

    private BufferedReader reader;
    private CsvLocationParser parser;
    private String currentLine;

    public CsvGazetteerReader(BufferedReader reader, CsvLocationParser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    @Override
    public boolean hasNextLocation() {
        try {
            currentLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentLine != null;
    }

    @Override
    public Location getNextLocation() throws ParseException {
        return parser.parse(currentLine);
    }

    public static class CsvLocationParserFactory {
        public static CsvLocationParser getCsvLocationParser(String type) {
            if(type.equals("Geonames"))
                return new GeonamesLocationParser();

            throw new IllegalArgumentException();
        }
    }

}
