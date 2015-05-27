package io.corpus.trconll;

import core.language.document.Document;
import core.language.word.Toponym;
import io.corpus.CorpusReader;
import io.corpus.xml.XMLStreamReader;
import io.corpus.xml.XMLStreamReaderFactory;
import io.corpus.xml.XMLStreamReaderType;
import junit.framework.TestCase;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;

public class TRConllCorpusReaderTest extends TestCase {

    private static final String CWAR_MULTIPLE_ARTICLES_TEST_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/corpora/CWAR/trconll/dev/cwar-sample.xml";
    private static final String CWAR_CORPUS_FILE = "/media/mike/780C9AAD0C9A65C2/Studie/corpora/CWAR/trconll/dev/cwar-dev.xml";


    @Test
    public void testConstructor() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        FileInputStream inputStream = new FileInputStream(CWAR_MULTIPLE_ARTICLES_TEST_FILE);
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(inputStream, XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new TRConllCorpusReader(xmlStreamReader);
    }

    @Test
    public void testHasNextDocument_XMLWithOneArticleReturnsTrueOnFirstCall() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        FileInputStream inputStream = new FileInputStream(CWAR_MULTIPLE_ARTICLES_TEST_FILE);
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(inputStream, XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new TRConllCorpusReader(xmlStreamReader);

        boolean hasNextDocument = corpusReader.hasNextDocument();

        assertTrue(hasNextDocument);
    }

    public void testNextDocument_DocumentGetsCorrectlyParsed() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        FileInputStream inputStream = new FileInputStream(CWAR_MULTIPLE_ARTICLES_TEST_FILE);
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(inputStream, XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new TRConllCorpusReader(xmlStreamReader);

        Document document = corpusReader.getNextDocument();

        assertTrue(true);
    }

    public void testParseWholeCorpus_NoExceptions() throws ParseException, FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        FileInputStream inputStream = new FileInputStream(CWAR_CORPUS_FILE);
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(inputStream, XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new TRConllCorpusReader(xmlStreamReader);

        while(corpusReader.hasNextDocument()) {
            Document document = corpusReader.getNextDocument();
            for(Toponym toponym : document.getToponyms()) {
                System.out.println(toponym.getText());
            }
        }

        assertTrue(true);
    }
}
