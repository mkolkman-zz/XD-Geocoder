package io.XML;

import core.document.Document;
import io.CorpusReader;

import java.text.ParseException;

public class XMLCorpusReader implements CorpusReader {

    @Override
    public boolean hasNextDocument() {
        return false;
    }

    @Override
    public Document getNextDocument() throws ParseException {
        return null;
    }

}
