package core.language.pos;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.junit.Test;

import java.net.URISyntaxException;

public class StanfordPosTaggerTest {

    public static final String ENGLISH_TAGGER_MODEL = "/core/language/pos/english-left3words-distsim.tagger";

    @Test
    public void testTagStringOnSimpleSentence() throws URISyntaxException {

        PosTagger tagger = new StanfordPosTagger(new MaxentTagger(getClass().getResource(ENGLISH_TAGGER_MODEL).toString()));

        String sentence = "This is a test sentence.";

        String tagged = tagger.tagString(sentence);

        System.out.println(tagged);
    }

    @Test
    public void testTagTokenizedStringOnSimpleSentence() {
        PosTagger tagger = new StanfordPosTagger(new MaxentTagger(getClass().getResource(ENGLISH_TAGGER_MODEL).toString()));

        String sentence = "This is a test sentence.";

        String tagged = tagger.tagTokenizedString(sentence);

        System.out.println(tagged);
    }

}
