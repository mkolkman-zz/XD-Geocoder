package core.learning.emperical.setup;

import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.document.Document;
import core.language.document.news.Article;
import core.language.word.Word;
import core.learning.learning_instance.LearningInstance;
import edu.stanford.nlp.tagger.common.Tagger;
import io.corpus.CorpusReader;
import io.corpus.csv.CsvCorpusReader;
import io.corpus.gat.GATTweetParser;
import io.corpus.lgl.LGLCorpusReader;
import io.corpus.xml.XMLStreamReader;
import io.corpus.xml.XMLStreamReaderFactory;
import io.corpus.xml.XMLStreamReaderType;
import io.learning_instance.LearningInstanceReader;
import io.learning_instance.LearningInstanceWriter;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

public abstract class ExperimentSetup {

    protected LearningInstanceReader reader;

    protected LearningInstanceWriter writer;

    public List<LearningInstance> getLearningInstances(String corpusFile) throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        List<LearningInstance> learningInstances;
        try{
            learningInstances = reader.read();
        }catch(FileNotFoundException e) {
            learningInstances = extractLearningInstances(corpusFile);
            writer.write(learningInstances);
        }
        return learningInstances;
    }

    abstract List<LearningInstance> extractLearningInstances(String corpusFile) throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException;

    abstract Iterator<Word> makeWordIterator(Document document);

    abstract Iterator<Word> makeWordIterator(Document document, Tagger tagger);

    CorpusReader makeCorpusReader(String corpusFile) throws Exception, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        if(corpusFile.equals(R.LGL_CORPUS_FILE)) {
            XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(corpusFile), XMLStreamReaderType.WOODSTOX);
            return new LGLCorpusReader(xmlStreamReader);
        }
        if(corpusFile.equals(R.GAT_CORPUS_FILE)) {
            return new CsvCorpusReader(new BufferedReader(new FileReader(corpusFile)), new GATTweetParser());
        }
        throw new Exception("Corpus file not supported");
    }

    Dictionary makeDictionary(CorpusReader corpusReader) throws XMLStreamException, FileNotFoundException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        Dictionary dictionary = new HashMapDictionary();
        while(corpusReader.hasNextDocument()) {
            Document document = corpusReader.getNextDocument();

            dictionary.load(makeWordIterator(document));
        }
        return dictionary;
    }

}
