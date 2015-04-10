package core.learning.features;

abstract public class Feature {

    protected float value;

    public abstract String getName();

    public float getFloatValue() {
        return value;
    }

    public boolean getBooleanValue() {
        return value > 0.5;
    }

    public String toString() {
        return getName() + ":" + value + "";
    }
}
