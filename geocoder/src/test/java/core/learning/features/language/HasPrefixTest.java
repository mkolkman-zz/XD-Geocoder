package core.learning.features.language;

import core.learning.features.Feature;
import core.language.word.Word;
import junit.framework.TestCase;

public class HasPrefixTest extends TestCase{

    public void testWithEmptyPrefix() {
        Feature feature = new HasPrefix(new Word(0, 9, "testwoord"), "");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithSingleCharacterPrefix() {
        Feature feature = new HasPrefix(new Word(0, 9, "testwoord"), "t");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithMultipleCharacterPrefix() {
        Feature feature = new HasPrefix(new Word(0, 9, "testwoord"), "tes");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithFalseSingleCharacterPrefix() {
        Feature feature = new HasPrefix(new Word(0, 9, "testwoord"), "e");
        assertFalse(feature.getBooleanValue());
    }

    public void testWithFalseMultipleCharacterPrefix() {
        Feature feature = new HasPrefix(new Word(0, 9, "testwoord"), "tet");
        assertFalse(feature.getBooleanValue());
    }

    public void testWithFullWord() {
        Feature feature = new HasPrefix(new Word(0, 9, "testwoord"), "testwoord");
        assertTrue(feature.getBooleanValue());
    }

    public void testWithLongerThanWord() {
        Feature feature = new HasPrefix(new Word(0, 9, "testwoord"), "testwoorden");
        assertFalse(feature.getBooleanValue());
    }

}
