package io.corpus.trconll;

import core.language.document.Document;
import core.language.document.historic.HistoricDocument;
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

    private int documentCount = 0;

    private int wordIndex;

    public TRConllCorpusReader(XMLStreamReader xmlStreamReader) {
        streamReader = xmlStreamReader;
    }

    @Override
    public boolean hasNextDocument() throws ParseException {
        try {
            while (streamReader.hasNext()) {
                eventType = streamReader.next();
                if(documentCount > 4) {
                    return false;
                }
                if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(DOCUMENT_TAG)) {
                    documentCount++;
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
            return parseWords();
        } catch (XMLStreamException e) {
            throw new ParseException(e.getMessage(), e.getLocation().getCharacterOffset());
        }
    }

    private Document parseWords() throws XMLStreamException, ParseException {
        HistoricDocument document = new HistoricDocument();
        List<Word> words = new ArrayList<Word>();
        List<Toponym> toponyms = new ArrayList<Toponym>();
        wordIndex = 0;
        while(streamReader.hasNext()) {
            eventType = streamReader.next();

            if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(WORD_TAG)) {
                words.add(parseWord());
            }
            if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(TOPONYM_TAG)) {
                words.add(parseToponym());
                toponyms.add(parseCandidates());
            }
            if(eventType == XMLEvent.END_ELEMENT && streamReader.getTag().equals(DOCUMENT_TAG)) {
                document.setWords(words);
                document.setToponyms(toponyms);
                return document;
            }
        }
        throw new ParseException("No endtag for document found", 0);
    }

    private Word parseWord() {
        String tok = streamReader.getAttributeValue("tok");

        Word word = new Word(tok);
        word.setStart(wordIndex);
        word.setEnd(wordIndex + tok.length());

        if(streamReader.getAttributeValue("label").equals("in_toponym")) {
            word.setLabel(Label.IN_TOPONYM);
        }

        wordIndex+= tok.length() + " ".length();

        return word;
    }

    private Word parseToponym() {
        String term = streamReader.getAttributeValue("term");

        Word word = new Word(term);
        word.setStart(wordIndex);
        word.setEnd(wordIndex + term.length());

        if(streamReader.getAttributeValue("label").equals("in_toponym")) {
            word.setLabel(Label.IN_TOPONYM);
        }

        return word;
    }

    private Toponym parseCandidates() throws ParseException, XMLStreamException {
        String term = streamReader.getAttributeValue("term");

        Toponym toponym = new Toponym(term);
        toponym.setStart(wordIndex);
        toponym.setEnd(wordIndex + term.length());

        wordIndex+= term.length() + " ".length();

        while(streamReader.hasNext()) {
            eventType = streamReader.next();

            if(eventType == XMLEvent.START_ELEMENT && streamReader.getTag().equals(CANDIDATE_TAG)) {
                if(streamReader.getAttributeValue("selected").equals("yes")) {
                    toponym.addGeonamesId(streamReader.getAttributeValue("id"));
                }
            }
            if(eventType == XMLEvent.END_ELEMENT && streamReader.getTag().equals(TOPONYM_TAG)) {
                return toponym;
            }
        }
        throw new ParseException("No endtag for toponym found", 0);
    }

}
