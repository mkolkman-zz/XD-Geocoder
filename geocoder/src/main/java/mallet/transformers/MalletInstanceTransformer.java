package mallet.transformers;

import cc.mallet.types.FeatureVector;
import cc.mallet.types.Instance;
import cc.mallet.types.Label;
import core.language.word.Word;
import core.learning.LearningInstance;

public class MalletInstanceTransformer {

    private MalletFeatureVectorTransformer featureVectorTransformer;

    public MalletInstanceTransformer(MalletFeatureVectorTransformer featureVectorTransformer) {
        this.featureVectorTransformer = featureVectorTransformer;
    }

    public Instance toMalletInstance(LearningInstance input) {
        FeatureVector featureVector = featureVectorTransformer.toMalletFeatureVector(input.getFeatures());
        Label label = MalletLabelAlphabet.getMalletLabel(input.getLabel().toString());
        Word source = input.getWord();
        String name = source.getText();
        return new Instance(featureVector, label, name, source);
    }

}
