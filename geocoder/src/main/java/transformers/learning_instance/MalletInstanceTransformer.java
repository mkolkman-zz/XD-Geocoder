package transformers.learning_instance;

import cc.mallet.types.FeatureVector;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Label;
import core.language.word.Word;
import core.learning.learning_instance.LearningInstance;
import core.learning.features.alphabet.MalletFeatureAlphabet;
import transformers.feature_vector.MalletFeatureVectorTransformer;
import core.learning.label.alphabet.MalletLabelAlphabet;

import java.util.List;

public class MalletInstanceTransformer {

    private MalletFeatureVectorTransformer featureVectorTransformer;

    public MalletInstanceTransformer(MalletFeatureVectorTransformer featureVectorTransformer) {
        this.featureVectorTransformer = featureVectorTransformer;
    }

    public InstanceList toMalletInstanceList(List<LearningInstance> input) {
        InstanceList output = new InstanceList(MalletFeatureAlphabet.getInstance(), MalletLabelAlphabet.getInstance());
        for(LearningInstance learningInstance : input) {
            output.add(toMalletInstance(learningInstance));
        }
        return output;
    }

    public Instance toMalletInstance(LearningInstance input) {
        FeatureVector featureVector = featureVectorTransformer.toMalletFeatureVector(input.getFeatures());
        Label label = MalletLabelAlphabet.getMalletLabel(input.getLabel().toString());
        Word source = input.getWord();
        String name = source.getText();
        return new Instance(featureVector, label, name, source);
    }

}
