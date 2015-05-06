package io.corpus.xml.lgl;

import core.language.document.news.Article;
import core.language.word.Toponym;
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
import java.util.List;

public class LGLCorpusReaderTest extends TestCase {

    private static final String LGL_SINGLE_ARTICLE_TEST_FILE = new File("").getAbsolutePath() + "\\src\\test\\resources\\xml\\lgl\\lgl-single-article-test.xml";
    private static final String LGL_MULTIPLE_ARTICLES_TEST_FILE = new File("").getAbsolutePath() + "\\src\\test\\resources\\xml\\lgl\\lgl-multiple-articles-test.xml";

    @Test
    public void testConstructor() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_SINGLE_ARTICLE_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);
    }

    @Test
    public void testHasNextDocument_XMLWithOneArticleReturnsTrueOnFirstCall() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_SINGLE_ARTICLE_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        boolean hasNextDocument = corpusReader.hasNextDocument();

        assertTrue(hasNextDocument);
    }

    @Test
    public void testHasNextDocument_XMLWithOneArticleReturnFalseOnSecondCall() throws ParseException, FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_SINGLE_ARTICLE_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        boolean hasOneDocument = corpusReader.hasNextDocument();
        boolean hasTwoDocuments = corpusReader.hasNextDocument();

        assertFalse(hasTwoDocuments);
    }

    @Test
    public void testHasNextDocument_XMLWithMultipleArticlesReturnsTrueOnFirstCall() throws ParseException, FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_MULTIPLE_ARTICLES_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        boolean hasNextDocument = corpusReader.hasNextDocument();

        assertTrue(hasNextDocument);
    }

    @Test
    public void testHasNextDocument_XMLWithMultipleArticlesReturnsTrueOnSecondCall() throws ParseException, FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_MULTIPLE_ARTICLES_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        boolean hasOneDocument = corpusReader.hasNextDocument();
        boolean hasTwoDocuments = corpusReader.hasNextDocument();

        assertTrue(hasTwoDocuments);
    }

    @Test
    public void testHasNextDocument_XMLWithMultipleArticlesReturnsFalseOnThirdCall() throws ParseException, FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_MULTIPLE_ARTICLES_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        boolean hasOneDocument = corpusReader.hasNextDocument();
        boolean hasTwoDocuments = corpusReader.hasNextDocument();
        boolean hasThreeDocuments = corpusReader.hasNextDocument();

        assertFalse(hasThreeDocuments);
    }

    @Test
    public void testNextDocument_SingleArticlePropertiesGetParsedCorrectly() throws ParseException, FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_SINGLE_ARTICLE_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        Article article = (Article) corpusReader.getNextDocument();

        assertEquals(138, article.getFeedId());
        assertEquals("Alexandria woman charged in connection with Kelleyland fire", article.getTitle());
        assertEquals("thetowntalk.com", article.getSourceDomain());
        assertEquals("http://www.thetowntalk.com/article/20090320/NEWS01/90320003/-1/rss", article.getSourceUrl());
        String expectedText = "Alexandria woman charged in connection with Kelleyland fire. Chiquita Raquel Henry, 19, of 1935 Orchard St., Alexandria, was arrested and charged with aggravated arson and unauthorized entry of an inhabited dwelling. Both the Sheriff's Office and Rapides Parish Fire District 2 investigated the March 7 fire at 6016 Dublin Road that led to Henry's arrest. According to the Sheriff's Office, the man who lives in the mobile home at that address was inside the home when Henry came in and set the bed and couch on fire with a lighter. The man only knew the woman by a nickname but investigation led detectives to Henry.";
        assertEquals(expectedText, article.getText());
    }

    @Test
    public void testNextDocument_SingleArticle_CorrectAmountOfToponymsGetParsed() throws ParseException, FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_SINGLE_ARTICLE_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        Article article = (Article) corpusReader.getNextDocument();

        assertEquals(3, article.getToponymCount());
    }

    @Test
    public void testNextDocument_SingleArticle_ToponymPropertiesGetParsedCorrectly() throws FileNotFoundException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(LGL_SINGLE_ARTICLE_TEST_FILE), XMLStreamReaderType.WOODSTOX);
        CorpusReader corpusReader = new LGLCorpusReader(xmlStreamReader);

        Article article = (Article) corpusReader.getNextDocument();

        List<Toponym> toponyms = article.getToponyms();
        assertEquals(0, toponyms.get(0).getStart());
        assertEquals(10, toponyms.get(0).getEnd());
        assertEquals("Alexandria", toponyms.get(0).getText());
        assertEquals("4314550", toponyms.get(0).getGeonamesIds().get(0));
        assertEquals("31.3113,-92.4451", toponyms.get(0).getCoordinates().toString());

    }
}
