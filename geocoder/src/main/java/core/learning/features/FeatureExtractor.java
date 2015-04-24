package core.learning.features;

import cc.mallet.types.Alphabet;
import core.language.labeller.Labeller;
import core.language.pos.PosTagger;
import core.learning.LearningInstance;
import core.learning.features.gazetteer.IsInGazetteer;
import core.learning.features.language.*;
import core.geo.LocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.word.Word;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Extracts a feature vector from each word retrieved from the WordTokenizer wordIterator.
 */
public class FeatureExtractor {

    private Iterator<Word> wordIterator;
    private Dictionary dictionary;
    private LocationGazetteer gazetteer;
    private PosTagger tagger;

    private Labeller labeller;

    protected List<LearningInstance> learningInstances;

    private static Alphabet featureAlphabet;
    private FeatureVector featureVector;

    public FeatureExtractor(Iterator<Word> wordIterator) {
        this(wordIterator, null, null);
    }

    public FeatureExtractor(Iterator<Word> wordIterator, Dictionary dictionary, LocationGazetteer gazetteer) {
        this.wordIterator = wordIterator;
        this.dictionary = dictionary;
        this.gazetteer = gazetteer;
    }

    public List<LearningInstance> getLearningInstances() {
        if(learningInstances == null) {
            extractLearningInstances();
        }
        return learningInstances;
    }

    protected List<LearningInstance> extractLearningInstances() {

        learningInstances = new ArrayList<LearningInstance>();

        while(wordIterator.hasNext()) {
            Word word = wordIterator.next();
            FeatureVector features = extractFeatures(word);

            learningInstances.add(new LearningInstance(word, features, word.getLabel()));
        }

        return learningInstances;
    }

    protected FeatureVector extractFeatures(Word word) {
        featureVector = new FeatureVector();

        //Form features
        featureVector.add(new IsInitCap(word));
        featureVector.add(new IsAllCaps(word));
//        featureVector.add(new HasPrefix(word, "ast"));
//        featureVector.add(new HasSuffix(word, "mouth"));
//        featureVector.add(new HasSuffix(word, "minster"));
//        featureVector.add(new HasSuffix(word, "ness"));
//        featureVector.add(new HasSuffix(word, "Hallo"));

        //POS features
        featureVector.add(new PartOfSpeechTag(word));

        //Dictionary features
        featureVector.add(new WordIndex(word, dictionary));
        featureVector.add(new WordFrequency(word, dictionary));

        //Gazetteer features
        featureVector.add(new IsInGazetteer(word, gazetteer));

        return featureVector;
    }

}
