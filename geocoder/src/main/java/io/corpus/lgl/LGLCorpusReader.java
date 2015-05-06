package io.corpus.lgl;

import core.language.document.Document;
import core.language.document.news.Article;
import core.gazetteer.Coordinate;
import core.language.word.Toponym;
import io.corpus.CorpusReader;
import io.corpus.xml.XMLStreamReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LGLCorpusReader implements CorpusReader {

    public static final String ARTICLES_TAG = "articles";
    private static final String ARTICLE_TAG = "article";
    private static final String FEED_ID_TAG = "feedid";
    public static final String TITLE_TAG = "title";
    public static final String DOMAIN_TAG = "domain";
    public static final String URL_TAG = "url";
    public static final String DOWNLOAD_TIME_TAG = "dltime";
    public static final String TEXT_TAG = "text";
    public static final String TOPONYMS_TAG = "toponyms";
    private static final String TOPONYM_TAG = "toponym";
    public static final String START_TAG = "start";
    public static final String END_TAG = "end";
    public static final String PHRASE_TAG = "phrase";
    public static final String GAZTAG_TAG = "gaztag";
    public static final String GEONAME_ID_ATTRIBUTE = "geonameid";
    public static final String LATITUDE_TAG = "lat";
    public static final String LONGITUDE_TAG = "lon";

    private XMLStreamReader streamReader;

    private int eventType = 0;
    private String currentElement = "";

    private Article article;
    private Toponym toponym;
    private String latitude;
    private String longitude;
    private boolean seenEndTag;

    public LGLCorpusReader(XMLStreamReader streamReader) {
        this.streamReader = streamReader;
    }

    @Override
    public boolean hasNextDocument() throws ParseException {
        try {
            while (streamReader.hasNext()) {
                eventType = streamReader.next();

                if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(ARTICLE_TAG)) {
                    return true;
                }
                if(eventType == XMLEvent.END_ELEMENT && streamReader.getTag().equals(ARTICLES_TAG)) {
                    return false;
                }
            }
        } catch (XMLStreamException e) {
            throw new ParseException(e.getMessage(), streamReader.getCharacterLocation());
        }
        return false;
    }

    @Override
    public Document getNextDocument() throws ParseException {
        try {
            return parseArticle();
        } catch (XMLStreamException e) {
            throw new ParseException(e.getMessage(), e.getLocation().getCharacterOffset());
        }
    }

    private Article parseArticle() throws XMLStreamException, ParseException {
        article = new Article();
        while(streamReader.hasNext()) {
            eventType = streamReader.next();
            switch(eventType) {
                case XMLEvent.START_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = false;
                    if(currentElement.equals(TOPONYMS_TAG)) {
                        parseToponyms();
                    }
                    break;
                case XMLEvent.CHARACTERS:
                case XMLEvent.CDATA:
                    parseArticleProperty();
                    break;
                case XMLEvent.END_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = true;
                    if(currentElement.equals(ARTICLE_TAG)){
                        return article;
                    }
                    break;
            }
        }
        throw new ParseException("Reached end without seeing expected " + ARTICLE_TAG + " tag", streamReader.getCharacterLocation());
    }

    private void parseArticleProperty() {
        String content = streamReader.getText();
        if (currentElement.equals(FEED_ID_TAG) && !seenEndTag) {
            article.setFeedId(Integer.parseInt(content));
        } else if (currentElement.equals(TITLE_TAG) && !seenEndTag) {
            article.setTitle(content);
        } else if (currentElement.equals(DOMAIN_TAG) && !seenEndTag) {
            article.setSourceDomain(content);
        } else if (currentElement.equals(URL_TAG) && !seenEndTag) {
            article.setSourceUrl(content);
        } else if (currentElement.equals(DOWNLOAD_TIME_TAG) && !seenEndTag) {
//            article.setDownloadTime(Date.valueOf(content));
        } else if (currentElement.equals(TEXT_TAG) && !seenEndTag) {
            article.setText(content);
        }
    }

    private void parseToponyms() throws XMLStreamException, ParseException {
        List<Toponym> toponyms = new ArrayList<Toponym>();
        while(hasNextToponym()) {
            toponyms.add(getNextToponym());
        }
        article.setToponyms(toponyms);
    }

    private boolean hasNextToponym() throws XMLStreamException {
        boolean result = false;
        while (streamReader.hasNext()) {
            eventType = streamReader.next();

            if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(TOPONYM_TAG)) {
                result = true;
                break;
            }
            if(eventType == XMLEvent.END_ELEMENT && streamReader.getTag().equals(TOPONYMS_TAG)) {
                break;
            }
        }
        return result;
    }

    private Toponym getNextToponym() throws ParseException {
        try {
            return parseToponym();
        } catch (XMLStreamException e) {
            throw new ParseException(e.getMessage(), e.getLocation().getCharacterOffset());
        }
    }

    private Toponym parseToponym() throws XMLStreamException, ParseException {
        toponym = new Toponym();
        latitude = null;
        longitude = null;

        while(streamReader.hasNext()) {
            eventType = streamReader.next();
            switch(eventType) {
                case XMLEvent.START_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = false;
                    parseToponymAttribute();
                    break;
                case XMLEvent.CHARACTERS:
                    parseToponymProperty();
                    break;
                case XMLEvent.END_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = true;
                    if(currentElement.equals(TOPONYM_TAG)){
                        return toponym;
                    }
                    break;
            }
        }
        throw new ParseException("Reached end without seeing expected " + TOPONYM_TAG + " tag", streamReader.getCharacterLocation());
    }

    private void parseToponymAttribute() {
        if(currentElement.equals(GAZTAG_TAG)) {
            String geonameId = streamReader.getAttributeValue(GEONAME_ID_ATTRIBUTE);
            toponym.addGeonamesId(geonameId);
        }
    }

    private void parseToponymProperty() {
        String content = streamReader.getText();
        if (currentElement.equals(START_TAG) && !seenEndTag) {
            toponym.setStart(Integer.parseInt(content));
        }else if(currentElement.equals(END_TAG) && !seenEndTag) {
            toponym.setEnd(Integer.parseInt(content));
        }else if(currentElement.equals(PHRASE_TAG) && !seenEndTag) {
            toponym.setText(content);
        }else if(currentElement.equals(LATITUDE_TAG) && !seenEndTag) {
            latitude = content;
        }else if(currentElement.equals(LONGITUDE_TAG) && !seenEndTag) {
            longitude = content;
            toponym.setCoordinates(new Coordinate(latitude, longitude));
        }
    }

}
