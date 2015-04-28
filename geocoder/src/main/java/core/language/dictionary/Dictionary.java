package core.language.dictionary;

import core.language.word.Word;
import io.corpus.CorpusReader;

import java.text.ParseException;
import java.util.Iterator;

public interface Dictionary {

    void load(Iterator<Word> wordIterator) throws ParseException;

    boolean contains(String word);

    void registerMention(String word, boolean isToponym);

    int getMentionCount(String word);

    int getToponymCount(String word);

    int getUppercaseCount(String word);

    int getWordCount();

    boolean isIthWord(int i, String word);

    String getIthWord(int i);

    int getRank(String word);
}
