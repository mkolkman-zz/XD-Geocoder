package core.learning.evaluator.lgl;

import cc.mallet.classify.Trial;
import core.learning.label.Label;
import core.learning.evaluator.Metric;
import core.learning.evaluator.Evaluator;

import java.util.ArrayList;
import java.util.List;

public class LglEvaluator extends Evaluator {

    private Trial trial;

    public LglEvaluator(Trial trial) {
        this.trial = trial;
    }

    public List<Metric> getPerformanceMetrics() {
        List<Metric> metrics = new ArrayList<Metric>();

        double p_b = getPrecision(Label.START_OF_TOPONYM.toString());
        double p_i = getPrecision(Label.IN_TOPONYM.toString());
        double p_o = getPrecision(Label.NO_TOPONYM.toString());
        double p_m = (p_b + p_i + p_o) / 3;
        double p_m_pos = (p_b + p_i) / 2;
        metrics.add(new Metric("p_b", p_b));
        metrics.add(new Metric("p_i", p_i));
        metrics.add(new Metric("p_o", p_o));
        metrics.add(new Metric("p_m", p_m));
        metrics.add(new Metric("p_m_pos", p_m_pos));

        double r_b = getRecall(Label.START_OF_TOPONYM.toString());
        double r_i = getRecall(Label.IN_TOPONYM.toString());
        double r_o = getRecall(Label.NO_TOPONYM.toString());
        double r_m = (r_b + r_i + r_o) / 3;
        double r_m_pos = (r_b + r_i) / 2;
        metrics.add(new Metric("r_b", r_b));
        metrics.add(new Metric("r_i", r_i));
        metrics.add(new Metric("r_o", r_o));
        metrics.add(new Metric("r_m", r_m));
        metrics.add(new Metric("r_m_pos", r_m_pos));

        metrics.add(new Metric("f_b", getF1(Label.START_OF_TOPONYM.toString())));
        metrics.add(new Metric("f_i", getF1(Label.IN_TOPONYM.toString())));
        metrics.add(new Metric("f_o", getF1(Label.NO_TOPONYM.toString())));
        metrics.add(new Metric("f_m", computeFscore(p_m, r_m)));
        metrics.add(new Metric("f_m_pos", computeFscore(p_m_pos, r_m_pos)));

        return metrics;
    }

    private double getF1(String label) {
        try{
            return trial.getF1(label);
        }catch(IllegalArgumentException e) {
            return 0.5;
        }
    }

    private double getPrecision(String label) {
        try{
            return trial.getPrecision(label);
        }catch(IllegalArgumentException e) {
            return 0.5;
        }
    }

    private double getRecall(String label) {
        try{
            return trial.getRecall(label);
        }catch(IllegalArgumentException e) {
            return 0.5;
        }
    }



}
