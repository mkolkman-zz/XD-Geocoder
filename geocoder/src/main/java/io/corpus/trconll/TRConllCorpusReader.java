package io.corpus.trconll;

import core.language.document.Document;
import core.language.document.historic.HistoricDocument;
import core.language.document.news.Article;
import core.language.word.Toponym;
import core.language.word.Word;
import core.learning.label.Label;
import io.corpus.CorpusReader;
import io.corpus.xml.XMLStreamReader;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TRConllCorpusReader implements CorpusReader {

    public static final String CORPUS_TAG = "corpus";
    private static final String DOCUMENT_TAG = "doc";
    private static final String SENTENCE_TAG = "s";
    private static final String WORD_TAG = "w";
    private static final String TOPONYM_TAG = "toponym";
    private static final String CANDIDATE_TAG = "cand";

    private XMLStreamReader streamReader;

    private int eventType = 0;

    private HistoricDocument document;
    private String currentElement;
    private boolean seenEndTag;
    private Word word;

    public TRConllCorpusReader(XMLStreamReader xmlStreamReader) {
        streamReader = xmlStreamReader;
    }

    @Override
    public boolean hasNextDocument() throws ParseException {
        try {
            while (streamReader.hasNext()) {
                eventType = streamReader.next();

                if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(DOCUMENT_TAG)) {
                    return true;
                }
                if(eventType == XMLEvent.END_ELEMENT && streamReader.getTag().equals(CORPUS_TAG)) {
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
            return parseDocument();
        } catch (XMLStreamException e) {
            throw new ParseException(e.getMessage(), e.getLocation().getCharacterOffset());
        }
    }

    private Document parseDocument() throws XMLStreamException, ParseException {
        document = new HistoricDocument();
        while(streamReader.hasNext()) {
            eventType = streamReader.next();
            switch(eventType) {
                case XMLEvent.START_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = false;
                    if(currentElement.equals(DOCUMENT_TAG)) {
                        parseSentence();
                    }
                    break;
                case XMLEvent.END_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = true;
                    if(currentElement.equals(DOCUMENT_TAG)){
                        return document;
                    }
                    break;
            }
        }
        throw new ParseException("Reached end without seeing expected " + DOCUMENT_TAG + " tag", streamReader.getCharacterLocation());
    }

    private void parseSentence() throws XMLStreamException, ParseException {
        List<Word> words = new ArrayList<Word>();
        while(hasNextWord()) {
            words.add(getNextWord());
        }
        document.setWords(words);
    }

    private boolean hasNextWord() throws XMLStreamException {
        while (streamReader.hasNext()) {
            eventType = streamReader.next();
            if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(WORD_TAG)) {
                return true;
            }
            if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(TOPONYM_TAG)) {
                return true;
            }
            if(eventType == XMLEvent.END_ELEMENT && streamReader.getTag().equals(DOCUMENT_TAG)) {
                return false;
            }
        }
        return false;
    }

    public Word getNextWord() throws ParseException {
        try {
            if(currentElement.equals(WORD_TAG)) {
                return parseWord();
            } else{
                return parseToponym();
            }
        } catch (XMLStreamException e) {
            throw new ParseException(e.getMessage(), e.getLocation().getCharacterOffset());
        }
    }

    private Word parseWord() throws XMLStreamException, ParseException {
        word = new Word();

        while(streamReader.hasNext()) {
            eventType = streamReader.next();
            switch(eventType) {
                case XMLEvent.START_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = false;

                    String text = streamReader.getAttributeValue("tok");
                    word.setText(text);
                    break;
                case XMLEvent.END_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = true;
                    if(currentElement.equals(WORD_TAG)){
                        return word;
                    }
                    break;
            }
        }
        throw new ParseException("MISTAKE", 0);
    }

    private Word parseToponym() throws XMLStreamException, ParseException {
        word = new Word();

        while(streamReader.hasNext()) {
            eventType = streamReader.next();
            switch(eventType) {
                case XMLEvent.START_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = false;

                    String text = streamReader.getAttributeValue("term");
                    word.setText(text);

                    parseCandidates();
                    break;
                case XMLEvent.END_ELEMENT:
                    currentElement = streamReader.getTag();
                    seenEndTag = true;
                    if(currentElement.equals(TOPONYM_TAG)){
                        return word;
                    }
                    break;
            }
        }
        throw new ParseException("MISTAKE", 0);
    }

    private void parseCandidates() throws XMLStreamException, ParseException {
        while(streamReader.hasNext()) {
            eventType = streamReader.next();
            if(eventType == XMLEvent.START_ELEMENT) {
                currentElement = streamReader.getTag();
                seenEndTag = false;

                if(currentElement.equals(CANDIDATE_TAG)) {
                    if(streamReader.getAttributeValue("selected").equals("yes")) {
                        word.setLabel(Label.START_OF_TOPONYM);
                    }
                }
            }
            if(eventType == XMLEvent.END_ELEMENT) {
                currentElement = streamReader.getTag();
                seenEndTag = true;

                if(currentElement.equals(TOPONYM_TAG)) {
                    break;
                }
            }
        }
        throw new ParseException("MISTAKE", 0);
    }
}
