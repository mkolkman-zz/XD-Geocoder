package core.learning.classifier;

import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import core.learning.LearningInstance;

import java.util.List;

public class MalletCrfClassifierTrainer {

    public MalletCrfClassifier train(List<LearningInstance> learningInstances) {
        return new MalletCrfClassifier();
    }

    private class MalletInstanceListConverter {

        public InstanceList convert(List<LearningInstance> learningInstances) {
            InstanceList instances = new InstanceList();

            for (LearningInstance learningInstance : learningInstances) {
                instances.add(new Instance());
            }

            return instances;
        }

    }
}
