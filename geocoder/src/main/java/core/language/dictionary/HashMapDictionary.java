package core.language.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapDictionary implements Dictionary {

    private List<String> words = new ArrayList<String>();
    private Map<String, IncrementableInteger> wordCounts = new HashMap<String, IncrementableInteger>();

    @Override
    public boolean contains(String word) {
        return wordCounts.containsKey(word);
    }

    @Override
    public void registerMention(String word) {
        if( ! wordCounts.containsKey(word)) {
            wordCounts.put(word, new IncrementableInteger());
            words.add(word);
        }
        wordCounts.get(word).increment();
    }

    @Override
    public int getMentionCount(String word) {
        return wordCounts.get(word).getValue();
    }

    @Override
    public int getWordCount() {
        return words.size();
    }

    @Override
    public boolean isIthWord(int i, String word) {
        return getIthWord(i).equals(word);
    }

    @Override
    public String getIthWord(int i) {
        int index = i-1;
        return words.get(index);
    }

    @Override
    public int getRank(String word) {
        return 0;
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
