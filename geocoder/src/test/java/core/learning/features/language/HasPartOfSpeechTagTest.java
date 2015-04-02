package core.learning.features.language;

import core.learning.features.Feature;
import core.language.pos.PosTag;
import core.language.word.Word;
import junit.framework.TestCase;
import org.junit.Test;

public class HasPartOfSpeechTagTest extends TestCase{

    @Test
    public void testEqualPosTag() {
        Feature feature = new HasPartOfSpeechTag(new Word(0, 8, "testwoord"), PosTag.ADJECTIVE, PosTag.ADJECTIVE);

        assertEquals((float) 1.0, feature.getFloatValue());
    }

    @Test
    public void testNonEqualPosTag() {
        Feature feature = new HasPartOfSpeechTag(new Word(0, 9, "testwoord"), PosTag.ADJECTIVE, PosTag.ARTICLE);

        assertEquals((float) 0.0, feature.getFloatValue());
    }
}
