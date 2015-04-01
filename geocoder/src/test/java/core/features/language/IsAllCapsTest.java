package core.features.language;

import core.features.Feature;
import junit.framework.TestCase;

public class IsAllCapsTest extends TestCase {

    public void testHasInitialCap() {
        Feature feature = new IsAllCaps("Enschede");

        assertFalse(feature.getBooleanValue());
    }

    public void testAllLowerCase() {
        Feature feature = new IsAllCaps("enschede");

        assertFalse(feature.getBooleanValue());
    }

    public void testAllUpperCase() {
        Feature feature = new IsAllCaps("ENSCHEDE");

        assertTrue(feature.getBooleanValue());
    }

    public void testUpperCaseInMiddleOfWord() {
        Feature feature = new IsAllCaps("enschEDE");

        assertFalse(feature.getBooleanValue());
    }
}
