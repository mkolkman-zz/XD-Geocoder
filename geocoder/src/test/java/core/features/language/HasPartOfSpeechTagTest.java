package core.features.language;

import core.features.Feature;
import core.language.pos.PosTag;
import junit.framework.TestCase;
import org.junit.Test;

public class HasPartOfSpeechTagTest extends TestCase{

    @Test
    public void testEqualPosTag() {
        Feature feature = new HasPartOfSpeechTag("enschede", PosTag.ADJECTIVE, PosTag.ADJECTIVE);

        assertEquals((float) 1.0, feature.getFloatValue());
    }

    @Test
    public void testNonEqualPosTag() {
        Feature feature = new HasPartOfSpeechTag("enschede", PosTag.ADJECTIVE, PosTag.ARTICLE);

        assertEquals((float) 0.0, feature.getFloatValue());
    }
}
