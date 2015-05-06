package core.learning.evaluator;

import java.util.List;

public abstract class Evaluator {

    public abstract List<Metric> getPerformanceMetrics();

    protected double computeFscore(double precision, double recall) {
        return (2 * precision * recall) / (precision + recall);
    }

}