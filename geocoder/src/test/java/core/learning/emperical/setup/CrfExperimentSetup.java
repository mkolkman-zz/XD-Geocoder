package core.learning.emperical.setup;

import core.language.document.Document;
import core.language.document.news.Article;
import core.language.labeller.lgl.LglLabeller;
import core.language.pos.stanford.StanfordPosTagger;
import core.language.tokenizer.stanford.StanfordWordTokenizer;
import core.language.word.Word;
import core.learning.features.FeatureExtractor;
import core.learning.learning_instance.LearningInstance;
import core.learning.learning_instance.extractor.LearningInstanceExtractor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordTokenFactory;
import edu.stanford.nlp.tagger.common.Tagger;
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
    List<LearningInstance> extractLearningInstances(String corpusFile) throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        CorpusReader corpusReader = makeCorpusReader(corpusFile);

        MaxentTagger tagger = new MaxentTagger();

        FeatureExtractor featureExtractor = new FeatureExtractor(makeDictionary(makeCorpusReader(corpusFile)), null);
        LearningInstanceExtractor learningInstanceExtractor = new LearningInstanceExtractor(featureExtractor);

        while(corpusReader.hasNextDocument()) {

            Article article = (Article) corpusReader.getNextDocument();
            Iterator<Word> wordIterator = makeWordIterator(article, tagger);

            learningInstanceExtractor.extractLearningInstances(wordIterator);
        }

        return learningInstanceExtractor.getLearningInstances();
    }

    @Override
    protected Iterator<Word> makeWordIterator(Document document) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(document.getText()), new WordTokenFactory(), ""));

        return new LglLabeller(tokenizedWords, document.getToponyms());
    }

    @Override
    protected Iterator<Word> makeWordIterator(Document document, Tagger tagger) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(document.getText()), new WordTokenFactory(), ""));

        Iterator<Word> labelledWords = new LglLabeller(tokenizedWords, document.getToponyms());

        return new StanfordPosTagger(labelledWords, tagger, new StanfordWordTransformer());
    }

}
