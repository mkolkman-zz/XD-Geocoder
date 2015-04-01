package core.features.language;

import core.features.Feature;
import junit.framework.TestCase;

public class HasSuffixTest extends TestCase{

    public void testWithEmptySuffix() {
        Feature feature = new HasSuffix("testwoord", "");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithSingleCharacterSuffix() {
        Feature feature = new HasSuffix("testwoord", "d");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithMultipleCharacterSuffix() {
        Feature feature = new HasSuffix("testwoord", "ord");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithFalseSingleCharacterSuffix() {
        Feature feature = new HasSuffix("testwoord", "r");
        assertFalse(feature.getBooleanValue());
    }

    public void testWithFalseMultipleCharacterSuffix() {
        Feature feature = new HasSuffix("testwoord", "ort");
        assertFalse(feature.getBooleanValue());
    }

    public void testWithFullWord() {
        Feature feature = new HasSuffix("testwoord", "testwoord");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithLongerThanWord() {
        Feature feature = new HasSuffix("testwoord", "hettestwoord");
        assertFalse(feature.getBooleanValue());
    }

}
