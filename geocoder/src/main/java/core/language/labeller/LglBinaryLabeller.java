package core.language.labeller;

import core.language.word.Toponym;
import core.language.word.Word;
import core.learning.Label;

import java.util.Iterator;
import java.util.List;

public class LglBinaryLabeller implements Labeller {

    private Iterator<Word> words;
    private List<Toponym> toponyms;

    public LglBinaryLabeller(Iterator<Word> words, List<Toponym> toponyms) {
        this.words = words;
        this.toponyms = toponyms;
    }

    @Override
    public boolean hasNext() {
        return words.hasNext();
    }

    @Override
    public Word next() {
        Word word = words.next();
        for(Toponym toponym : toponyms) {
            if(isBeginOfToponym(word, toponym) || isInToponym(word, toponym)) {
                word.setLabel(Label.PART_OF_TOPONYM);
                return word;
            }
        }
        word.setLabel(Label.NO_TOPONYM);
        return word;
    }

    private boolean isBeginOfToponym(Word word, Toponym toponym) {
        return toponym.getStart() == word.getStart() && toponym.getText().startsWith(word.getText());
    }

    private boolean isInToponym(Word word, Toponym toponym) {
        return toponym.getStart() < word.getStart() && toponym.getEnd() >= word.getEnd() && toponym.getText().contains(word.getText());
    }

    @Override
    public void remove() {

    }

}
