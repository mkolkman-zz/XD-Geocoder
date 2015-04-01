package core.features.language;

import core.features.Feature;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import junit.framework.TestCase;

public class IsIthWordInDictionaryTest extends TestCase {

    public void testIsFirstWordInDictionary() {
        Dictionary dictionary = new HashMapDictionary();
        dictionary.registerMention("testwoord");
        dictionary.registerMention("yada");
        dictionary.registerMention("hallooooo");
        Feature feature = new IsIthWordInDictionary(1, "testwoord", dictionary);
        assertTrue(feature.getBooleanValue());
    }

    public void testIsSomeWordInDictionary() {
        Dictionary dictionary = new HashMapDictionary();
        dictionary.registerMention("yada");
        dictionary.registerMention("testwoord");
        dictionary.registerMention("bladibla");
        Feature feature = new IsIthWordInDictionary(2, "testwoord", dictionary);
        assertTrue(feature.getBooleanValue());
    }

    public void testIsLastWordInDictionary() {
        Dictionary dictionary = new HashMapDictionary();
        dictionary.registerMention("yada");
        dictionary.registerMention("windhoos");
        dictionary.registerMention("testwoord");

        Feature feature = new IsIthWordInDictionary(3, "testwoord", dictionary);
        assertTrue(feature.getBooleanValue());
    }

    public void testIsNotInDictionary() {
        Dictionary dictionary = new HashMapDictionary();
        dictionary.registerMention("yada");
        dictionary.registerMention("windhoos");
        dictionary.registerMention("hoi");

        Feature feature = new IsIthWordInDictionary(1, "testwoord", dictionary);
        assertFalse(feature.getBooleanValue());
        feature = new IsIthWordInDictionary(2, "testwoord", dictionary);
        assertFalse(feature.getBooleanValue());
        feature = new IsIthWordInDictionary(3, "testwoord", dictionary);
        assertFalse(feature.getBooleanValue());
    }

    public void testIsNotIthWordInDictionary() {
        Dictionary dictionary = new HashMapDictionary();
        dictionary.registerMention("yada");
        dictionary.registerMention("windhoos");
        dictionary.registerMention("testwoord");
        dictionary.registerMention("hiephoi");

        Feature feature = new IsIthWordInDictionary(2, "testwoord", dictionary);
        assertFalse(feature.getBooleanValue());
    }
}
