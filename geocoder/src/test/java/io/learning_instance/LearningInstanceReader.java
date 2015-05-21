package io.learning_instance;

import core.learning.learning_instance.LearningInstance;

import java.io.*;
import java.util.List;

public class LearningInstanceReader {

    private String file;

    public LearningInstanceReader(String file) throws FileNotFoundException {
        this.file = file;
    }

    public List<LearningInstance> read() throws IOException, ClassNotFoundException {
        if(!fileExists()) {
            throw new FileNotFoundException();
        }
        List<LearningInstance> learningInstances;
        FileInputStream fileInput = new FileInputStream(file);
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
        learningInstances = (List<LearningInstance>) objectInput.readObject();
        return learningInstances;
    }


    private boolean fileExists() {
        File instancesFile = new File(file);
        return instancesFile.exists();
    }

}
