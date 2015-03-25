package io;

import core.document.Document;

import java.text.ParseException;

public interface CorpusReader {

    boolean hasNextDocument();

    Document getNextDocument() throws ParseException;

}
