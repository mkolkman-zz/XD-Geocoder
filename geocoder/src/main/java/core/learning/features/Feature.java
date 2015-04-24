package core.learning.features;

import java.io.Serializable;

abstract public class Feature implements Serializable {

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
