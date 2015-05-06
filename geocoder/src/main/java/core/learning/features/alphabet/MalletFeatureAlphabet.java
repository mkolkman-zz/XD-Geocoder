package core.learning.features.alphabet;

import cc.mallet.types.Alphabet;

public class MalletFeatureAlphabet {

    private static Alphabet featureAlphabet;

    public static Alphabet getInstance() {
        if(featureAlphabet == null) {
            featureAlphabet = new Alphabet();
        }
        return featureAlphabet;
    }

}
