package io.corpus.xml;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class WoodstoxXMLStreamReader implements XMLStreamReader {

    private final javax.xml.stream.XMLStreamReader reader;

    public WoodstoxXMLStreamReader(javax.xml.stream.XMLStreamReader reader) throws FileNotFoundException, XMLStreamException {
        this.reader = reader;
    }

    @Override
    public boolean hasNext() throws XMLStreamException {
        return reader.hasNext();
    }

    @Override
    public int next() throws XMLStreamException {
        return reader.next();
    }

    @Override
    public String getText() {
        return reader.getText();
    }

    @Override
    public String getTag() {
        return reader.getName().toString();
    }

    @Override
    public String getAttributeValue(String attribute) {
        int attributeCount = reader.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = reader.getAttributeName(i).toString();
            if(attributeName.equals(attribute)) {
                return reader.getAttributeValue(i);
            }
        }
        return "";
    }

    @Override
    public int getCharacterLocation() {
        return reader.getLocation().getCharacterOffset();
    }

}
