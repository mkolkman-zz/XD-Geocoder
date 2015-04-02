package core.learning.features.language;

import core.learning.features.Feature;
import core.language.word.Word;
import junit.framework.TestCase;

public class HasSuffixTest extends TestCase{

    public void testWithEmptySuffix() {
        Feature feature = new HasSuffix(new Word(0, 9, "testwoord"), "");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithSingleCharacterSuffix() {
        Feature feature = new HasSuffix(new Word(0, 9, "testwoord"), "d");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithMultipleCharacterSuffix() {
        Feature feature = new HasSuffix(new Word(0, 9, "testwoord"), "ord");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithFalseSingleCharacterSuffix() {
        Feature feature = new HasSuffix(new Word(0, 9, "testwoord"), "r");
        assertFalse(feature.getBooleanValue());
    }

    public void testWithFalseMultipleCharacterSuffix() {
        Feature feature = new HasSuffix(new Word(0, 9, "testwoord"), "ort");
        assertFalse(feature.getBooleanValue());
    }

    public void testWithFullWord() {
        Feature feature = new HasSuffix(new Word(0, 9, "testwoord"), "testwoord");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithLongerThanWord() {
        Feature feature = new HasSuffix(new Word(0, 9, "testwoord"), "hettestwoord");
        assertFalse(feature.getBooleanValue());
    }

}
