package core.language.word.stanford;

import edu.stanford.nlp.ling.HasWord;

public class StanfordWord implements HasWord {

    private String word;

    public StanfordWord(String word) {
        this.word = word;
    }

    @Override
    public String word() {
        return word;
    }

    @Override
    public void setWord(String word) {
        this.word = word;
    }
}
