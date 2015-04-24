package core.language.tokenizer.stanford;

import core.language.tokenizer.WordTokenizer;
import core.language.word.Word;
import core.learning.Label;
import edu.stanford.nlp.process.PTBTokenizer;

public class StanfordWordTokenizer implements WordTokenizer{

    private PTBTokenizer tokenizer;

    public StanfordWordTokenizer(PTBTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public boolean hasNext() {
        return tokenizer.hasNext();
    }

    public Word next() {
        edu.stanford.nlp.ling.Word next = (edu.stanford.nlp.ling.Word) tokenizer.next();
        String word = next.word();
        return new Word(next.beginPosition(), next.endPosition(), word, Label.UNKNOWN);
    }

    @Override
    public void remove() {

    }
}
