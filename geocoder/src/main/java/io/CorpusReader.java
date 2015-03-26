package io;

import core.document.Document;

import javax.xml.stream.XMLStreamException;
import java.text.ParseException;

public interface CorpusReader {

    boolean hasNextDocument() throws ParseException;

    Document getNextDocument() throws ParseException;

}
