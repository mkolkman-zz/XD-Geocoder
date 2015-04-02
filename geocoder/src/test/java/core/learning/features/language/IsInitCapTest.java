package core.learning.features.language;

import core.learning.features.Feature;
import core.language.word.Word;
import junit.framework.TestCase;

public class IsInitCapTest extends TestCase {

    public void testHasInitialCap() {
        Feature feature = new IsInitCap(new Word(0, 8, "Enschede"));

        assertEquals((float) 1.0, feature.getFloatValue());
    }

    public void testAllLowerCase() {
        Feature feature = new IsInitCap(new Word(0, 8, "enschede"));

        assertEquals((float) 0.0, feature.getFloatValue());
    }

    public void testAllUpperCase() {
        Feature feature = new IsInitCap(new Word(0, 8, "ENSCHEDE"));

        assertEquals((float) 1.0, feature.getFloatValue());
    }

    public void testUpperCaseInMiddleOfWord() {
        Feature feature = new IsInitCap(new Word(0, 8, "enschEDE"));

        assertEquals((float) 0.0, feature.getFloatValue());
    }
}
