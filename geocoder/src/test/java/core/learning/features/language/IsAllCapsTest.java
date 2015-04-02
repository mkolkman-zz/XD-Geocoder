package core.learning.features.language;

import core.learning.features.Feature;
import core.language.word.Word;
import junit.framework.TestCase;

public class IsAllCapsTest extends TestCase {

    public void testHasInitialCap() {
        Feature feature = new IsAllCaps(new Word(0, 8, "Enschede"));

        assertFalse(feature.getBooleanValue());
    }

    public void testAllLowerCase() {
        Feature feature = new IsAllCaps(new Word(0, 8, "enschede"));

        assertFalse(feature.getBooleanValue());
    }

    public void testAllUpperCase() {
        Feature feature = new IsAllCaps(new Word(0, 8, "ENSCHEDE"));

        assertTrue(feature.getBooleanValue());
    }

    public void testUpperCaseInMiddleOfWord() {
        Feature feature = new IsAllCaps(new Word(0, 8, "enschEDE"));

        assertFalse(feature.getBooleanValue());
    }
}
