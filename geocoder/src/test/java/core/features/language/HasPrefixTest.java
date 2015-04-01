package core.features.language;

import core.features.Feature;
import junit.framework.TestCase;

public class HasPrefixTest extends TestCase{

    public void testWithEmptyPrefix() {
        Feature feature = new HasPrefix("testwoord", "");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithSingleCharacterPrefix() {
        Feature feature = new HasPrefix("testwoord", "t");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithMultipleCharacterPrefix() {
        Feature feature = new HasPrefix("testwoord", "tes");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithFalseSingleCharacterPrefix() {
        Feature feature = new HasPrefix("testwoord", "e");
        assertFalse(feature.getBooleanValue());
    }

    public void testWithFalseMultipleCharacterPrefix() {
        Feature feature = new HasPrefix("testwoord", "tet");
        assertFalse(feature.getBooleanValue());
    }

    public void testWithFullWord() {
        Feature feature = new HasPrefix("testwoord", "testwoord");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithLongerThanWord() {
        Feature feature = new HasPrefix("testwoord", "testwoorden");
        assertFalse(feature.getBooleanValue());
    }

}
