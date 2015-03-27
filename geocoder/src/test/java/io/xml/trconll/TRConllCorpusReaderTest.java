package io.xml.trconll;

import io.CorpusReader;
import io.xml.XMLStreamReader;
import io.xml.XMLStreamReaderFactory;
import io.xml.XMLStreamReaderType;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TRConllCorpusReaderTest {

    private static final String TRCONLL_SINGLE_ARTICLE_TEST_FILE = new File("").getAbsolutePath() + "\\geocoder\\src\\test\\resources\\xml\\lgl\\lgl-single-article-test.xml";
    private static final String TRCONLL_MULTIPLE_ARTICLES_TEST_FILE = new File("").getAbsolutePath() + "\\geocoder\\src\\test\\resources\\xml\\lgl\\lgl-multiple-articles-test.xml";

    @Test
    public void testConstructor() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(TRCONLL_SINGLE_ARTICLE_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new TRConllCorpusReader(xmlStreamReader);
    }
}
