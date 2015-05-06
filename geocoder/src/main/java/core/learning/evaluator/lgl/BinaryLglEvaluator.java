package core.learning.evaluator.lgl;

import cc.mallet.classify.Trial;
import core.learning.label.Label;
import core.learning.evaluator.Metric;
import core.learning.evaluator.Evaluator;

import java.util.ArrayList;
import java.util.List;

public class BinaryLglEvaluator extends Evaluator {

    private final Trial trial;

    public BinaryLglEvaluator(Trial trial) {
        this.trial = trial;
    }

    @Override
    public List<Metric> getPerformanceMetrics() {
        List<Metric> metrics = new ArrayList<Metric>();

        double p_p = trial.getPrecision(Label.PART_OF_TOPONYM.toString());
        double p_o = trial.getPrecision(Label.NO_TOPONYM.toString());
        metrics.add(new Metric("p_p", p_p));
        metrics.add(new Metric("p_o", p_o));

        double r_p = trial.getRecall(Label.PART_OF_TOPONYM.toString());
        double r_o = trial.getRecall(Label.NO_TOPONYM.toString());
        metrics.add(new Metric("r_p", r_p));
        metrics.add(new Metric("r_o", r_o));

        metrics.add(new Metric("f_p", trial.getF1(Label.PART_OF_TOPONYM.toString())));
        metrics.add(new Metric("f_o", trial.getF1(Label.NO_TOPONYM.toString())));

        return metrics;
    }

}
