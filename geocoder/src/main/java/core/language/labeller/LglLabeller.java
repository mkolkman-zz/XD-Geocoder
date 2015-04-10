package core.language.labeller;

import core.language.word.Toponym;
import core.language.word.Word;
import core.learning.Label;

import java.util.Iterator;
import java.util.List;

public class LglLabeller implements Labeller {

    private Iterator<Word> words;
    private List<Toponym> toponyms;

    public LglLabeller(Iterator<Word> words, List<Toponym> toponyms) {
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
            if(toponym.getStart() == word.getStart() && toponym.getText().startsWith(word.getText())) {
                word.setLabel(Label.START_OF_TOPONYM);
                return word;
            }else if (toponym.getStart() < word.getStart() && toponym.getEnd() >= word.getEnd() && toponym.getText().contains(word.getText())) {
                word.setLabel(Label.IN_TOPONYM);
                return word;
            }
        }
        word.setLabel(Label.NO_TOPONYM);
        return word;
    }

}
