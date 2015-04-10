package core.language.tokenizer;

import core.language.tokenizer.stanford.StanfordWordTokenizer;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import org.junit.Test;

import java.io.StringReader;

public class StanfordWordTokenizerTest {

    @Test
    public void testConstructor() {
        WordTokenizer tokenizer = new StanfordWordTokenizer(new PTBTokenizer(new StringReader("This is my example sentence."), new WordTokenFactory(), ""));

        while(tokenizer.hasNext()) {
            System.out.println(tokenizer.next().getText());
        }
    }
}
