package core.language.tokenizer;

import core.language.word.Word;
import edu.stanford.nlp.process.PTBTokenizer;

public class WordTokenizer {

    private PTBTokenizer tokenizer;

    public WordTokenizer(PTBTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public boolean hasNext() {
        return tokenizer.hasNext();
    }

    public Word next() {
        edu.stanford.nlp.ling.Word next = (edu.stanford.nlp.ling.Word) tokenizer.next();
        return new Word(next.beginPosition(), next.endPosition(), next.word());
    }

}
