package core.learning.features;

import core.learning.LearningInstance;
import core.learning.features.gazetteer.IsInGazetteer;
import core.learning.features.language.*;
import core.geo.LocationGazetteer;
import core.language.dictionary.Dictionary;
import core.language.tokenizer.WordTokenizer;
import core.language.word.Word;

import java.util.ArrayList;
import java.util.List;

public class FeatureExtractor {

    private WordTokenizer tokenizer;
    private Dictionary dictionary;
    private LocationGazetteer gazetteer;

    private List<LearningInstance> learningInstances;

    public FeatureExtractor(WordTokenizer tokenizer, Dictionary dictionary, LocationGazetteer gazetteer) {
        this.tokenizer = tokenizer;
        this.dictionary = dictionary;
        this.gazetteer = gazetteer;
    }

    public List<LearningInstance> getLearningInstances() {
        if(learningInstances == null) {
            extractLearningInstances();
        }
        return learningInstances;
    }

    private List<LearningInstance> extractLearningInstances() {
        learningInstances = new ArrayList<LearningInstance>();

        while(tokenizer.hasNext()) {
            Word word = tokenizer.next();
            List<Feature> features = extractFeatures(word);

            learningInstances.add(new LearningInstance(word, features));
        }

        return learningInstances;
    }

    private List<Feature> extractFeatures(Word word) {
        List<Feature> features = new ArrayList<Feature>();

        //Form features
        features.add(new IsInitCap(word));
        features.add(new IsAllCaps(word));
        features.add(new HasPrefix(word, "ast"));
        features.add(new HasSuffix(word, "mouth"));
        features.add(new HasSuffix(word, "minster"));
        features.add(new HasSuffix(word, "ness"));

        //Dictionary features
        for (int i = 1; i < dictionary.getWordCount(); i++) {
            features.add(new IsIthWordInDictionary(i, word, dictionary));
        }

        //Gazetteer features
        features.add(new IsInGazetteer(word, gazetteer));

        return features;
    }
}
