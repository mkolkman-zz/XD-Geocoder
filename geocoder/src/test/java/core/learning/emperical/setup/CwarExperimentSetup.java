package core.learning.emperical.setup;

import core.gazetteer.LocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.document.Document;
import core.language.labeller.cwar.CwarLabeller;
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

public class CwarExperimentSetup extends ExperimentSetup {

    private GazetteerFactory gazetteerFactory;

    private Tagger tagger;

    public CwarExperimentSetup(GazetteerFactory gazetteerFactory, Tagger tagger, LearningInstanceReader reader, LearningInstanceWriter writer) {
        this.gazetteerFactory = gazetteerFactory;
        this.tagger = tagger;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    List<LearningInstance> extractLearningInstances(String corpusFile) throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {

        Runtime runtime = Runtime.getRuntime();
        int mb = 1024 * 1024;

        System.out.println("Before dictionary: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " MB");
        Dictionary dictionary = makeDictionary(makeCorpusReader(corpusFile));
        System.out.println("Distinct word count: " + dictionary.getWordCount());
        System.out.println("Distinct toponym count: " + dictionary.getToponymCount());
        System.out.println("After dictionary: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " MB");

        LocationGazetteer gazetteer = gazetteerFactory.getGazetteer();
        System.out.println("After gazetteer: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " MB");

        FeatureExtractor featureExtractor = new FeatureExtractor(dictionary, gazetteer);
        LearningInstanceExtractor learningInstanceExtractor = new LearningInstanceExtractor(featureExtractor);

        CorpusReader corpusReader = makeCorpusReader(corpusFile);
        for(int i = 0; corpusReader.hasNextDocument(); i++) {

            Document document = corpusReader.getNextDocument();
            Iterator<Word> wordIterator = makeWordIterator(document, tagger);

            System.out.println("Extracting features for document " + i);
            learningInstanceExtractor.extractLearningInstances(wordIterator);
            System.out.println("Total word count: " + learningInstanceExtractor.getLearningInstanceCount());
            System.out.println("Used memory: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " MB");
        }

        return learningInstanceExtractor.getLearningInstances();
    }

    @Override
    Iterator<Word> makeWordIterator(Document document) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(document.getText()), new WordTokenFactory(), ""));

        return new CwarLabeller(tokenizedWords, document.getToponyms());
    }

    @Override
    Iterator<Word> makeWordIterator(Document document, Tagger tagger) {
        Iterator<Word> tokenizedWords = new StanfordWordTokenizer(new PTBTokenizer(new StringReader(document.getText()), new WordTokenFactory(), ""));

        Iterator<Word> labelledWords = new CwarLabeller(tokenizedWords, document.getToponyms());

        return new StanfordPosTagger(labelledWords, tagger, new StanfordWordTransformer());
    }

    @Override
    public void cleanup() {
        super.cleanup();
        gazetteerFactory = null;
        tagger = null;
    }
}
