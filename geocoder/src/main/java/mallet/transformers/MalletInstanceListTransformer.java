package mallet.transformers;

import cc.mallet.types.InstanceList;
import core.learning.LearningInstance;

import java.util.List;

public class MalletInstanceListTransformer {

    private MalletInstanceTransformer instanceTransformer;

    public MalletInstanceListTransformer(MalletInstanceTransformer instanceTransformer) {
        this.instanceTransformer = instanceTransformer;
    }

    public MalletInstanceTransformer getInstanceTransformer() {
        return instanceTransformer;
    }

    public InstanceList toMalletInstanceList(List<LearningInstance> input) {
        InstanceList output = new InstanceList(MalletFeatureAlphabet.getInstance(), MalletLabelAlphabet.getInstance());
        for(LearningInstance learningInstance : input) {
            output.add(instanceTransformer.toMalletInstance(learningInstance));
        }
        return output;
    }
}
