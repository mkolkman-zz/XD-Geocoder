package core.language.pos.stanford;

import core.language.pos.PosTagger;
import core.language.word.Word;
import core.language.word.stanford.StanfordWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.common.Tagger;
import stanford.transformers.StanfordTransformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StanfordPosTagger implements PosTagger {

    private Iterator<Word> inputSentence;
    private Iterator<Word> outputSentence;
    private Tagger tagger;
    private StanfordTransformer transformer;

    public StanfordPosTagger(Iterator<Word> inputSentence, Tagger tagger, StanfordTransformer transformer) {
        this.inputSentence = inputSentence;
        this.tagger = tagger;
        this.transformer = transformer;
        tagWords();
    }

    private void tagWords() {
        List<Word> sentence = new ArrayList<Word>();
        while(inputSentence.hasNext()) {
            sentence.add(inputSentence.next());
        }
        sentence = tagWordList(sentence);
        this.outputSentence = sentence.iterator();
    }

    public List<Word> tagWordList(List<Word> sentence) {
        List<StanfordWord> stanfordSentence = transformer.toStanfordSentence(sentence);
        List<TaggedWord> taggedStanfordSentence = tagger.apply(stanfordSentence);
        return transformer.fromTaggedSentence(taggedStanfordSentence);
    }

    @Override
    public boolean hasNext() {
        return outputSentence.hasNext();
    }

    @Override
    public Word next() {
        return outputSentence.next();
    }
}
