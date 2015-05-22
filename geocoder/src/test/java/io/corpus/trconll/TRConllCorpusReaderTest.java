package io.corpus.trconll;

import core.language.document.Document;
import io.corpus.CorpusReader;
import io.corpus.lgl.LGLCorpusReader;
import io.corpus.xml.XMLStreamReader;
import io.corpus.xml.XMLStreamReaderFactory;
import io.corpus.xml.XMLStreamReaderType;
import junit.framework.TestCase;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;

public class TRConllCorpusReaderTest extends TestCase {

    private static final String TRCONLL_MULTIPLE_ARTICLES_TEST_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/corpora/CWAR/trconll/dev/cwar-sample.xml";

    @Test
    public void testConstructor() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        FileInputStream inputStream = new FileInputStream(TRCONLL_MULTIPLE_ARTICLES_TEST_FILE);
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(inputStream, XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new TRConllCorpusReader(xmlStreamReader);
    }

    @Test
    public void testHasNextDocument_XMLWithOneArticleReturnsTrueOnFirstCall() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        FileInputStream inputStream = new FileInputStream(TRCONLL_MULTIPLE_ARTICLES_TEST_FILE);
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(inputStream, XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new TRConllCorpusReader(xmlStreamReader);

        boolean hasNextDocument = corpusReader.hasNextDocument();

        assertTrue(hasNextDocument);
    }

    public void testNextDocument_DocumentGetsCorrectlyParsed() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        FileInputStream inputStream = new FileInputStream(TRCONLL_MULTIPLE_ARTICLES_TEST_FILE);
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(inputStream, XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new TRConllCorpusReader(xmlStreamReader);

        Document document = corpusReader.getNextDocument();

        assertTrue(true);
    }
}
