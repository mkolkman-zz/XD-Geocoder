package core.learning.features;

import cc.mallet.types.Alphabet;
import core.language.labeller.Labeller;
import core.language.pos.PosTagger;
import core.learning.LearningInstance;
import core.learning.features.gazetteer.IsInGazetteer;
import core.learning.features.gazetteer.IsPartialInGazetteer;
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

    private String[] prefixes = new String[]{"aber", "ast", "auch", "auchter", "bal", "brad", "bre", "caer", "car", "cul", "cum", "dal", "din", "dol", "drum", "dun", "dum", "don", "doune", "fin", "inver", "inner", "kil", "kin", "kyle", "lang", "mynydd", "nan", "nans", "nant", "nor", "pen", "pit", "pol", "pont", "porth", "shep", "ship", "stan", "strath", "sud", "sut", "tre", "tilly", "tullie", "tulloch", "win", "lan", "lhan", "llan", "beck", "ac", "acc", "ock", "new", "saint", "fort", "grand", "south", "north", "san", "santa"};
    private String[] suffixes = new String[]{"bost", "carden", "caster", "chester", "cester", "ceter", "cot", "cott", "dale", "dean", "den", "don", "field", "firth", "firth", "ham", "ing", "keth", "cheth", "mouth", "ness", "pool", "port", "stead", "ster", "thwaite", "twatt", "wick", "wich", "wych", "wyke", "wick", "ay", "ey", "dubh", "dow", "dhu", "duff", "craig", "crag", "creag", "lin", "llyn", "toft", "worth", "worthy", "wardine", "by", "beck", "avon", "berg", "berry", "bourne", "burn", "burgh", "ville", "polis", "springs", "beach", "land", "ton", "ville", "spring", "city", "bay", "valley", "lake"};

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
        for (String prefix : prefixes) {
            featureVector.add(new HasPrefix(word, prefix));
        }
        for (String suffix : suffixes) {
            featureVector.add(new HasSuffix(word, suffix));
        }

        //POS features
        featureVector.add(new PartOfSpeechTag(word));

        //Dictionary features
        featureVector.add(new WordIndex(word, dictionary));
        featureVector.add(new WordFrequency(word, dictionary));
        featureVector.add(new ToponymFrequency(word, dictionary));
        featureVector.add(new ToponymFraction(word, dictionary));
        featureVector.add(new UppercaseFrequency(word, dictionary));
        featureVector.add(new UppercaseFraction(word, dictionary));

        //Gazetteer features
        featureVector.add(new IsInGazetteer(word, gazetteer));
        featureVector.add(new IsPartialInGazetteer(word, gazetteer));

        return featureVector;
    }

}
