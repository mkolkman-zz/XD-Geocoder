package core.language.pos;

import core.language.tokenizer.WordTokenizer;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.junit.Test;
import core.language.pos.stanford.StanfordPosTagger;
import transformers.word.StanfordWordTransformer;

import java.io.StringReader;

public class StanfordPosTaggerTest {

    private static final String ENGLISH_TAGGER_MODEL = "/core/language/pos/english-left3words-distsim.tagger";

    @Test
    public void testTagWordListzOnSimpleSentence() {
        WordTokenizer tokenizer = new StanfordWordTokenizer(new PTBTokenizer(new StringReader("This is a very simple example sentence."), new WordTokenFactory(), ""));
        String modelFile = getClass().getResource(ENGLISH_TAGGER_MODEL).toString();
        MaxentTagger maxentTagger = new MaxentTagger(modelFile);
        PosTagger tagger = new StanfordPosTagger(tokenizer, maxentTagger, new StanfordWordTransformer());

        while(tagger.hasNext()) {
            Word word = tagger.next();
            System.out.println(word.getText() + ": " + word.getPosTag());
        }
    }

}
