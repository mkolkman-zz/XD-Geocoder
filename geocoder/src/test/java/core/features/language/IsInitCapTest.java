package core.features.language;

import core.features.Feature;
import junit.framework.TestCase;

public class IsInitCapTest extends TestCase {

    public void testHasInitialCap() {
        Feature feature = new IsInitCap("Enschede");

        assertEquals((float) 1.0, feature.getFloatValue());
    }

    public void testAllLowerCase() {
        Feature feature = new IsInitCap("enschede");

        assertEquals((float) 0.0, feature.getFloatValue());
    }

    public void testAllUpperCase() {
        Feature feature = new IsInitCap("ENSCHEDE");

        assertEquals((float) 1.0, feature.getFloatValue());
    }

    public void testUpperCaseInMiddleOfWord() {
        Feature feature = new IsInitCap("enschEDE");

        assertEquals((float) 0.0, feature.getFloatValue());
    }
}
