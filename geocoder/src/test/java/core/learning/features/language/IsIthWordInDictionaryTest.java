package core.learning.features.language;

import core.learning.features.Feature;
import core.language.dictionary.Dictionary;
import core.language.dictionary.HashMapDictionary;
import core.language.word.Word;
import junit.framework.TestCase;

public class IsIthWordInDictionaryTest extends TestCase {

//    public void testIsFirstWordInDictionary() {
//        Dictionary dictionary = new HashMapDictionary();
//        dictionary.registerMention("testwoord", false);
//        dictionary.registerMention("yada", false);
//        dictionary.registerMention("hallooooo", false);
//        Feature feature = new IsIthWordInDictionary(1, new Word(0, 9, "testwoord"), dictionary);
//        assertTrue(feature.getBooleanValue());
//    }
//
//    public void testIsSomeWordInDictionary() {
//        Dictionary dictionary = new HashMapDictionary();
//        dictionary.registerMention("yada", false);
//        dictionary.registerMention("testwoord", false);
//        dictionary.registerMention("bladibla", false);
//        Feature feature = new IsIthWordInDictionary(2, new Word(0, 9, "testwoord"), dictionary);
//        assertTrue(feature.getBooleanValue());
//    }
//
//    public void testIsLastWordInDictionary() {
//        Dictionary dictionary = new HashMapDictionary();
//        dictionary.registerMention("yada", false);
//        dictionary.registerMention("windhoos", false);
//        dictionary.registerMention("testwoord", false);
//
//        Feature feature = new IsIthWordInDictionary(3, new Word(0, 9, "testwoord"), dictionary);
//        assertTrue(feature.getBooleanValue());
//    }
//
//    public void testIsNotInDictionary() {
//        Dictionary dictionary = new HashMapDictionary();
//        dictionary.registerMention("yada", false);
//        dictionary.registerMention("windhoos", false);
//        dictionary.registerMention("hoi", false);
//
//        Feature feature = new IsIthWordInDictionary(1, new Word(0, 9, "testwoord"), dictionary);
//        assertFalse(feature.getBooleanValue());
//        feature = new IsIthWordInDictionary(2, new Word(0, 9, "testwoord"), dictionary);
//        assertFalse(feature.getBooleanValue());
//        feature = new IsIthWordInDictionary(3, new Word(0, 9, "testwoord"), dictionary);
//        assertFalse(feature.getBooleanValue());
//    }
//
//    public void testIsNotIthWordInDictionary() {
//        Dictionary dictionary = new HashMapDictionary();
//        dictionary.registerMention("yada", false);
//        dictionary.registerMention("windhoos", false);
//        dictionary.registerMention("testwoord", false);
//        dictionary.registerMention("hiephoi", false);
//
//        Feature feature = new IsIthWordInDictionary(2, new Word(0, 9, "testwoord"), dictionary);
//        assertFalse(feature.getBooleanValue());
//    }
}
