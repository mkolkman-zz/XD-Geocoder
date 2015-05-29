package transformers.learning_instance;

import cc.mallet.types.*;
import core.language.word.Word;
import core.learning.features.alphabet.MalletFeatureAlphabet;
import core.learning.label.alphabet.MalletLabelAlphabet;
import core.learning.learning_instance.LearningInstance;
import transformers.feature_vector.MalletFeatureVectorTransformer;

import java.util.ArrayList;
import java.util.List;

public class MalletInstanceTransformer {

    public static final int SENTENCE_LENGTH = 10;
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

    public InstanceList toMalletSequenceInstanceList(List<LearningInstance> input) {
        InstanceList output = new InstanceList(MalletFeatureAlphabet.getInstance(), MalletLabelAlphabet.getInstance());

        List<FeatureVector> featureVectorList = new ArrayList<FeatureVector>();
        List<Label> labelList = new ArrayList<Label>();
        for(int i = 0; i < input.size(); i++) {

            FeatureVector featureVector = featureVectorTransformer.toMalletFeatureVector(input.get(i).getFeatures());
            featureVectorList.add(featureVector);
            Label label = MalletLabelAlphabet.getMalletLabel(input.get(i).getLabel().toString());
            labelList.add(label);

            if(i % SENTENCE_LENGTH == 0 && featureVectorList.size() > 0) {
                FeatureVector[] featureVectorArray = featureVectorList.toArray(new FeatureVector[featureVectorList.size()]);
                FeatureVectorSequence featureVectorSequence = new FeatureVectorSequence(featureVectorArray);

                Label[] labelArray = labelList.toArray(new Label[labelList.size()]);
                LabelSequence labelSequence = new LabelSequence(labelArray);

                output.add(new Instance(featureVectorSequence, labelSequence, "" + i, "" + i));
            }
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
