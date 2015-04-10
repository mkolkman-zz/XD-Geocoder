package core.language.dictionary;

import io.corpus.CorpusReader;

import java.text.ParseException;

public interface Dictionary {

    void load(CorpusReader corpusReader) throws ParseException;

    boolean contains(String word);

    void registerMention(String word);

    int getMentionCount(String word);

    int getWordCount();

    boolean isIthWord(int i, String word);

    String getIthWord(int i);

    int getRank(String word);
}
