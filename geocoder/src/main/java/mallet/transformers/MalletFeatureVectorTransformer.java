package mallet.transformers;

import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureVector;

public class MalletFeatureVectorTransformer {

    public FeatureVector toMalletFeatureVector(core.learning.features.FeatureVector input) {
        Alphabet dataAlphabet = MalletFeatureAlphabet.getInstance();
        dataAlphabet.lookupIndices(input.getFeatureNames(), true);
        return new FeatureVector(dataAlphabet, input.getFeatureValues());
    }

}
