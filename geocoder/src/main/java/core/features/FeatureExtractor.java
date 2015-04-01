package core.features;

import core.features.language.*;
import core.language.dictionary.Dictionary;
import core.language.tokenizer.WordTokenizer;
import core.language.word.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureExtractor {

    private WordTokenizer tokenizer;
    private Dictionary dictionary;

    public FeatureExtractor(WordTokenizer tokenizer, Dictionary dictionary) {
        this.tokenizer = tokenizer;
        this.dictionary = dictionary;
    }

    public Map<String, List<Feature>> extractFeatures(String sentence) {
        Map<String, List<Feature>> words = new HashMap<String, List<Feature>>();

        while(tokenizer.hasNext()) {
            Word word = tokenizer.next();

            List<Feature> features = new ArrayList<Feature>();

            features.add(new IsInitCap(word));
            features.add(new IsAllCaps(word));
            features.add(new HasPrefix(word, "ast"));
            features.add(new HasSuffix(word, "mouth"));
            features.add(new HasSuffix(word, "minster"));
            features.add(new HasSuffix(word, "ness"));

            for (int i = 1; i < dictionary.getWordCount(); i++) {
                features.add(new IsIthWordInDictionary(i, word, dictionary));
            }

            words.put(word.getText(), features);
        }

        return words;
    }
}
