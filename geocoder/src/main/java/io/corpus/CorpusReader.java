package io.corpus;

import core.language.document.Document;

import java.text.ParseException;

public interface CorpusReader {

    boolean hasNextDocument() throws ParseException;

    Document getNextDocument() throws ParseException;

}
