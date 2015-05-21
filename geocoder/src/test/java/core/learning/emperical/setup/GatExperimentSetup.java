package core.learning.emperical.setup;

import core.language.document.Document;
import core.language.labeller.gat.GatLabeller;
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
import io.corpus.CorpusReader;
import io.corpus.xml.XMLStreamReaderFactory;
import io.learning_instance.LearningInstanceReader;
import io.learning_instance.LearningInstanceWriter;
import transformers.word.StanfordWordTransformer;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

public class GatExperimentSetup extends ExperimentSetup {

    private GazetteerFactory gazetteerFactory;

    private Tagger tagger;

    public GatExperimentSetup(GazetteerFactory gazetteerFactory, Tagger tagger, LearningInstanceReader reader, LearningInstanceWriter writer) {
        this.gazetteerFactory = gazetteerFactory;
        this.tagger = tagger;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    List<LearningInstance> extractLearningInstances(String corpusFile) throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        CorpusReader corpusReader = makeCorpusReader(corpusFile);

        FeatureExtractor featureExtractor = new FeatureExtractor(makeDictionary(makeCorpusReader(corpusFile)), gazetteerFactory.getGazetteer());
        LearningInstanceExtractor learningInstanceExtractor = new LearningInstanceExtractor(featureExtractor);

        while(corpusReader.hasNextDocument()) {

            Document document = corpusReader.getNextDocument();
            Iterator<Word> wordIterator = makeWordIterator(document, tagger);

            learningInstanceExtractor.extractLearningInstances(wordIterator);
        }

        return learningInstanceExtractor.getLearningInstances();
    }

    @Override
    Iterator<Word> makeWordIterator(Document document) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(document.getText()), new WordTokenFactory(), ""));

        return new GatLabeller(tokenizedWords, document.getToponyms());
    }

    @Override
    Iterator<Word> makeWordIterator(Document document, Tagger tagger) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(document.getText()), new WordTokenFactory(), ""));

        Iterator<Word> labelledWords = new GatLabeller(tokenizedWords, document.getToponyms());

        return new StanfordPosTagger(labelledWords, tagger, new StanfordWordTransformer());
    }
}
