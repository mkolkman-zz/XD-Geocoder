package core.learning.emperical;

import core.language.document.news.Article;
import core.language.labeller.lgl.BinaryLglLabeller;
import core.language.labeller.lgl.LglLabeller;
import core.language.pos.stanford.StanfordPosTagger;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import core.learning.emperical.setup.ExperimentSetup;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import transformers.word.StanfordWordTransformer;

import java.io.StringReader;
import java.util.Iterator;

public class BinarySvmExperimentSetup extends ExperimentSetup {

    @Override
    protected Iterator<Word> makeWordIterator(Article article) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(article.getText()), new WordTokenFactory(), ""));

        return new LglLabeller(tokenizedWords, article.getToponyms());
    }

    @Override
    protected Iterator<Word> makeWordIterator(Article article, MaxentTagger tagger) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(article.getText()), new WordTokenFactory(), ""));

        Iterator<Word> labelledWords = new BinaryLglLabeller(tokenizedWords, article.getToponyms());

        return new StanfordPosTagger(labelledWords, tagger, new StanfordWordTransformer());
    }
}
