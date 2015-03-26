package io.xml;

import org.codehaus.stax2.XMLInputFactory2;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class XMLStreamReaderFactory {

    public static XMLStreamReader makeXMLStreamReader(InputStream inputStream, XMLStreamReaderType type) throws XMLStreamException, FileNotFoundException, UnsupportedStreamReaderTypeException {
        switch(type) {
            case WOODSTOX:
                XMLInputFactory2 inputFactory = (XMLInputFactory2) XMLInputFactory2.newInstance();
                return new WoodstoxXMLStreamReader(inputFactory.createXMLStreamReader(inputStream));
            default:
                throw new UnsupportedStreamReaderTypeException();
        }
    }

    public static class UnsupportedStreamReaderTypeException extends Throwable {
    }
}
