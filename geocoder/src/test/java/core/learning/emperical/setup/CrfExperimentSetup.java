package core.learning.emperical.setup;

import core.language.document.news.Article;
import core.language.labeller.lgl.LglLabeller;
import core.language.pos.stanford.StanfordPosTagger;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import core.learning.features.FeatureExtractor;
import core.learning.learning_instance.LearningInstance;
import core.learning.learning_instance.extractor.LearningInstanceExtractor;
import core.learning.learning_instance.extractor.SentenceLearningInstanceExtractor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import io.corpus.CorpusReader;
import io.corpus.xml.XMLStreamReaderFactory;
import transformers.word.StanfordWordTransformer;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

public class CrfExperimentSetup extends ExperimentSetup {

    @Override
    List<LearningInstance> extractLearningInstances(String corpusFile, String gazetteerFile, String taggerFile) throws ParseException, IOException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ClassNotFoundException {
        CorpusReader corpusReader = makeCorpusReader(corpusFile);

        MaxentTagger tagger = new MaxentTagger(taggerFile);

        FeatureExtractor featureExtractor = new FeatureExtractor(makeDictionary(makeCorpusReader(corpusFile)), makeGazetteer(gazetteerFile));
        LearningInstanceExtractor learningInstanceExtractor = new SentenceLearningInstanceExtractor(featureExtractor);

        while(corpusReader.hasNextDocument()) {

            Article article = (Article) corpusReader.getNextDocument();
            Iterator<Word> wordIterator = makeWordIterator(article, tagger);

            learningInstanceExtractor.extractLearningInstances(wordIterator);
        }

        return learningInstanceExtractor.getLearningInstances();
    }

    @Override
    protected Iterator<Word> makeWordIterator(Article article) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(article.getText()), new WordTokenFactory(), ""));

        return new LglLabeller(tokenizedWords, article.getToponyms());
    }

    @Override
    protected Iterator<Word> makeWordIterator(Article article, MaxentTagger tagger) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(article.getText()), new WordTokenFactory(), ""));

        Iterator<Word> labelledWords = new LglLabeller(tokenizedWords, article.getToponyms());

        return new StanfordPosTagger(labelledWords, tagger, new StanfordWordTransformer());
    }

}
