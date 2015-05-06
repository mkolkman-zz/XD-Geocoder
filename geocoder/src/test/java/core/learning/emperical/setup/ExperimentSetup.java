package core.learning.emperical.setup;

import core.gazetteer.geonames.GeonamesLocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.document.news.Article;
import core.language.word.Word;
import core.learning.learning_instance.LearningInstance;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import io.corpus.CorpusReader;
import io.corpus.xml.XMLStreamReader;
import io.corpus.xml.XMLStreamReaderFactory;
import io.corpus.xml.XMLStreamReaderType;
import io.corpus.lgl.LGLCorpusReader;
import io.gazetteer.csv.CsvGazetteerReader;
import io.gazetteer.csv.CsvLocationParser;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

public abstract class ExperimentSetup {

    public List<LearningInstance> getLearningInstances(String corpusFile, String gazetteerFile, String taggerFile, String featureFile) throws IOException, ClassNotFoundException, ParseException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        List<LearningInstance> learningInstances;
        if(fileExists(featureFile)) {
            learningInstances = readLearningInstances(featureFile);
        } else {
            learningInstances = extractLearningInstances(corpusFile, gazetteerFile, taggerFile);
            writeLearningInstances(learningInstances, featureFile);
        }
        return learningInstances;
    }

    private boolean fileExists(String file) {
        File instancesFile = new File(file);
        return instancesFile.exists();
    }


    private List<LearningInstance> readLearningInstances(String featureFile) throws IOException, ClassNotFoundException {
        List<LearningInstance> learningInstances;
        FileInputStream fileInput = new FileInputStream(featureFile);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        learningInstances = (List<LearningInstance>) objectInput.readObject();
        return learningInstances;
    }

    private void writeLearningInstances(List<LearningInstance> learningInstances, String featureFile) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(featureFile);
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(learningInstances);
        objectOutput.close();
        fileOutput.close();
    }

    abstract List<LearningInstance> extractLearningInstances(String corpusFile, String gazetteerFile, String taggerFile) throws ParseException, IOException, XMLStreamException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ClassNotFoundException;

    abstract Iterator<Word> makeWordIterator(Article article);

    abstract Iterator<Word> makeWordIterator(Article article, MaxentTagger tagger);

    LGLCorpusReader makeCorpusReader(String corpusFile) throws XMLStreamException, FileNotFoundException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException {
        XMLStreamReader xmlStreamReader = XMLStreamReaderFactory.makeXMLStreamReader(new FileInputStream(corpusFile), XMLStreamReaderType.WOODSTOX);
        return new LGLCorpusReader(xmlStreamReader);
    }

    Dictionary makeDictionary(CorpusReader corpusReader) throws XMLStreamException, FileNotFoundException, XMLStreamReaderFactory.UnsupportedStreamReaderTypeException, ParseException {
        Dictionary dictionary = new HashMapDictionary();
        while(corpusReader.hasNextDocument()) {
            Article article = (Article) corpusReader.getNextDocument();

            dictionary.load(makeWordIterator(article));
        }
        return dictionary;
    }

    GeonamesLocationGazetteer makeGazetteer(String gazetteerFile) throws FileNotFoundException {
        CsvLocationParser lineParser = CsvGazetteerReader.CsvLocationParserFactory.getCsvLocationParser("Geonames");
        CsvGazetteerReader gazetteerReader = new CsvGazetteerReader(new BufferedReader(new FileReader(gazetteerFile)), lineParser);
        return new GeonamesLocationGazetteer(gazetteerReader, lineParser);
    }
}
