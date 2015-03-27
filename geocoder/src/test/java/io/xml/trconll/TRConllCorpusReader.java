package io.xml.trconll;

import core.language.document.Document;
import io.CorpusReader;
import io.xml.XMLStreamReader;

import java.text.ParseException;

public class TRConllCorpusReader implements CorpusReader {

    private XMLStreamReader reader;

    public TRConllCorpusReader(XMLStreamReader xmlStreamReader) {
        reader = xmlStreamReader;
    }

    @Override
    public boolean hasNextDocument() throws ParseException {
        return false;
    }

    @Override
    public Document getNextDocument() throws ParseException {
        return null;
    }
}
