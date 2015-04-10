package core.language.pos;

import core.language.tokenizer.WordTokenizer;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.junit.Test;
import core.language.pos.stanford.StanfordPosTagger;
import stanford.transformers.StanfordTransformer;

import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class StanfordPosTaggerTest {

    public static final String ENGLISH_TAGGER_MODEL = "/core/language/pos/english-left3words-distsim.tagger";

    @Test
    public void testTagWordListOnSimpleSentence() {
        WordTokenizer tokenizer = new StanfordWordTokenizer(new PTBTokenizer(new StringReader("This is a very simple example sentence."), new WordTokenFactory(), ""));
        MaxentTagger maxentTagger = new MaxentTagger(getClass().getResource(ENGLISH_TAGGER_MODEL).toString());
        PosTagger tagger = new StanfordPosTagger(tokenizer, maxentTagger, new StanfordTransformer());

        while(tagger.hasNext()) {
            Word word = tagger.next();
            System.out.println(word.getText() + ": " + word.getPosTag());
        }
    }

}
