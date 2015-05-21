package io.learning_instance;

import core.learning.learning_instance.LearningInstance;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class LearningInstanceWriter {

    private String file;

    public LearningInstanceWriter(String file) {
        this.file = file;
    }

    public void write(List<LearningInstance> learningInstances) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream(file);
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(learningInstances);
        objectOutput.close();
        fileOutput.close();
    }

}
