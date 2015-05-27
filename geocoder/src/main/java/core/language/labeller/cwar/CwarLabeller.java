package core.language.labeller.cwar;

import core.language.labeller.Labeller;
import core.language.word.Toponym;
import core.language.word.Word;
import core.learning.label.Label;

import java.util.Iterator;
import java.util.List;

public class CwarLabeller implements Labeller {

    private Iterator<Word> words;
    private List<Toponym> toponyms;

    public CwarLabeller(Iterator<Word> words, List<Toponym> toponyms) {
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
            if(isBeginOfToponym(word, toponym)) {
                word.setLabel(Label.START_OF_TOPONYM);
                return word;
            }else if (isInToponym(word, toponym)) {
                word.setLabel(Label.IN_TOPONYM);
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
