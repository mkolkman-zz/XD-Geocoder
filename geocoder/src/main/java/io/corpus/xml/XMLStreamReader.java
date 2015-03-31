package io.corpus.xml;

import javax.xml.stream.XMLStreamException;

public interface XMLStreamReader {

    int next() throws XMLStreamException;

    boolean hasNext() throws XMLStreamException;

    String getText();

    String getTag();

    String getAttributeValue(String attribute);

    int getCharacterLocation();
}
