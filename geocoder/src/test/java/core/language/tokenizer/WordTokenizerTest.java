package core.language.tokenizer;

import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import org.junit.Test;

import java.io.StringReader;

public class WordTokenizerTest {

    @Test
    public void testConstructor() {
        WordTokenizer tokenizer = new WordTokenizer(new PTBTokenizer(new StringReader("This is my example sentence."), new WordTokenFactory(), ""));

        while(tokenizer.hasNext()) {
            System.out.println(tokenizer.next().getText());
        }
    }
}
