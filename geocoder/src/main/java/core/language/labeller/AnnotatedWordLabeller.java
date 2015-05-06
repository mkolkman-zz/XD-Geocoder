package core.language.labeller;

import core.language.word.Word;
import core.learning.label.Label;

import java.util.Iterator;

public class AnnotatedWordLabeller implements Labeller {

    private static final String BEGIN_OF_TOPONYM = "-B-TOP";
    private static final String IN_TOPONYM = "-I-TOP";

    private final Iterator<Word> words;

    public AnnotatedWordLabeller(Iterator<Word> words) {
        this.words = words;
    }

    @Override
    public boolean hasNext() {
        return words.hasNext();
    }

    @Override
    public Word next() {
        Word next = words.next();

        String word = next.getText();
        if(word.endsWith(BEGIN_OF_TOPONYM)) {
            next.setText(word.substring(0, word.indexOf(BEGIN_OF_TOPONYM)));
            next.setLabel(Label.START_OF_TOPONYM);
        } else if(word.endsWith(IN_TOPONYM)) {
            next.setText(word.substring(0, word.indexOf(IN_TOPONYM)));
            next.setLabel(Label.IN_TOPONYM);
        }

        return next;
    }

    @Override
    public void remove() {

    }

}
