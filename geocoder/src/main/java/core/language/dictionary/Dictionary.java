package core.language.dictionary;

public interface Dictionary {

    boolean contains(String word);

    void registerMention(String word);

    int getMentionCount(String word);

    boolean isIthWord(int i, String word);

    String getIthWord(int i);

    int getRank(String word);
}
