package core.language.dictionary;

import core.language.document.Document;
import core.language.tokenizer.WordTokenizer;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import core.learning.Label;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import io.corpus.CorpusReader;

import java.io.StringReader;
import java.text.ParseException;
import java.util.*;

public class HashMapDictionary implements Dictionary {

    private List<String> words = new ArrayList<String>();
    private Map<String, IncrementableInteger> wordCounts = new HashMap<String, IncrementableInteger>();
    private Map<String, IncrementableInteger> beginOfToponymCounts = new HashMap<String, IncrementableInteger>();
    private Map<String, IncrementableInteger> inToponymCounts = new HashMap<String, IncrementableInteger>();
    private Map<String, IncrementableInteger> uppercaseCounts = new HashMap<String, IncrementableInteger>();

    @Override
    public void load(Iterator<Word> wordIterator) throws ParseException {
        while(wordIterator.hasNext()) {
            Word word = wordIterator.next();
            registerMention(word);
        }
    }

    @Override
    public void registerMention(Word word) {
        String token = word.getText();
        boolean isUppercase = Character.isUpperCase(token.charAt(0));
        token = preprocess(token);

        registerMention(token);
        if(word.getLabel() == Label.START_OF_TOPONYM) {
            registerBeginOfToponym(token);
        }else if(word.getLabel() == Label.IN_TOPONYM) {
            registerInToponym(token);
        }
        if(isUppercase) {
            registerUppercase(token);
        }
    }

    public void registerMention(String word) {
        word = preprocess(word);
        if( ! wordCounts.containsKey(word)) {
            wordCounts.put(word, new IncrementableInteger());
            words.add(word);
        }
        wordCounts.get(word).increment();
    }

    private void registerBeginOfToponym(String token) {
        if( ! beginOfToponymCounts.containsKey(token)) {
            beginOfToponymCounts.put(token, new IncrementableInteger());
        }
        beginOfToponymCounts.get(token).increment();
    }

    private void registerInToponym(String token) {
        if( ! inToponymCounts.containsKey(token)) {
            inToponymCounts.put(token, new IncrementableInteger());
        }
        inToponymCounts.get(token).increment();
    }

    private void registerUppercase(String word) {
        if( ! uppercaseCounts.containsKey(word)) {
            uppercaseCounts.put(word, new IncrementableInteger());
        }
        uppercaseCounts.get(word).increment();
    }

    private String preprocess(String word) {
        word = word.toLowerCase();
        return word;
    }

    @Override
    public boolean contains(String word) {
        word = preprocess(word);
        return wordCounts.containsKey(word);
    }

    @Override
    public int getMentionCount(String word) {
        word = preprocess(word);
        return wordCounts.containsKey(word) ? wordCounts.get(word).getValue() : 0;
    }

    @Override
    public int getToponymCount(String word) {
        return 0;
    }

    @Override
    public int getBeginOfToponymCount(String word) {
        word = preprocess(word);
        return beginOfToponymCounts.containsKey(word) ? beginOfToponymCounts.get(word).getValue() : 0;
    }

    @Override
    public int getInToponymCount(String word) {
        word = preprocess(word);
        return inToponymCounts.containsKey(word) ? inToponymCounts.get(word).getValue() : 0;
    }

    public int getUppercaseCount(String word) {
        word = preprocess(word);
        return uppercaseCounts.containsKey(word) ? uppercaseCounts.get(word).getValue() : 0;
    }

    @Override
    public int getWordCount() {
        return words.size();
    }

    @Override
    public boolean isIthWord(int i, String word) {
        word = preprocess(word);
        return getIthWord(i).equals(word);
    }

    @Override
    public String getIthWord(int i) {
        int index = i-1;
        return words.get(index);
    }

    @Override
    public int getRank(String word) {
        return words.indexOf(word);
    }

    private class IncrementableInteger {

        private int mValue = 0;

        public void increment() {
            mValue++;
        }

        public int getValue() {
            return mValue;
        }

    }
}
