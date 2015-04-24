package core.language.dictionary;

import core.language.document.Document;
import core.language.tokenizer.WordTokenizer;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import io.corpus.CorpusReader;

import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapDictionary implements Dictionary {

    private List<String> words = new ArrayList<String>();
    private Map<String, IncrementableInteger> wordCounts = new HashMap<String, IncrementableInteger>();

    @Override
    public void load(CorpusReader corpusReader) throws ParseException {
        System.out.print("Loading dictionary... ");
        while(corpusReader.hasNextDocument()) {
            Document document = corpusReader.getNextDocument();
            WordTokenizer tokenizer = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(document.getText()), new WordTokenFactory(), ""));
            while(tokenizer.hasNext()) {
                Word word = tokenizer.next();
                registerMention(word.getText());
            }
        }
        System.out.println("Done!");
    }

    @Override
    public void registerMention(String word) {
        word = preprocess(word);
        if( ! wordCounts.containsKey(word)) {
            wordCounts.put(word, new IncrementableInteger());
            words.add(word);
        }
        wordCounts.get(word).increment();
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
        return wordCounts.get(word).getValue();
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
