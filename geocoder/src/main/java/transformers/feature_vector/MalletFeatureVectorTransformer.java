package transformers.feature_vector;

import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureVector;
import core.learning.features.alphabet.MalletFeatureAlphabet;

public class MalletFeatureVectorTransformer {

    public FeatureVector toMalletFeatureVector(core.learning.features.FeatureVector input) {
        Alphabet dataAlphabet = MalletFeatureAlphabet.getInstance();
        int[] featureIndices = dataAlphabet.lookupIndices(input.getFeatureNames(), true);
        double[] featureValues = input.getFeatureValues();
        return new FeatureVector(dataAlphabet, featureIndices, featureValues);
    }

}
