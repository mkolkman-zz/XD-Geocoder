package core.learning.features;

import java.util.ArrayList;
import java.util.List;

public class FeatureVector {

    private List<Feature> features;

    public FeatureVector() {
        features = new ArrayList<Feature>();
    }

    public void add(Feature feature) {
        features.add(feature);
    }

    public Feature get(int index) {
        return features.get(index);
    }

    public List<Feature> getFeatureList() {
        return features;
    }

    public String[] getFeatureNames() {
        String[] result = new String[features.size()];
        for (int i = 0; i < features.size(); i++) {
            result[i] = features.get(i).getName();
        }
        return result;
    }

    public double[] getFeatureValues() {
        double[] result = new double[features.size()];
        for (int i = 0; i < features.size(); i++) {
            result[i] = features.get(i).getFloatValue();
        }
        return result;
    }
}
