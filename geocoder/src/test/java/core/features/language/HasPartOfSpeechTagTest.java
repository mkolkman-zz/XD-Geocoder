package core.features.language;

import core.features.Feature;
import core.language.pos.POSTag;
import junit.framework.TestCase;
import org.junit.Test;

public class HasPartOfSpeechTagTest extends TestCase{

    @Test
    public void testEqualPosTag() {
        Feature feature = new HasPartOfSpeechTag("enschede", POSTag.ADJECTIVE, POSTag.ADJECTIVE);

        assertEquals((float) 1.0, feature.getValue());
    }

    @Test
    public void testNonEqualPosTag() {
        Feature feature = new HasPartOfSpeechTag("enschede", POSTag.ADJECTIVE, POSTag.ARTICLE);

        assertEquals((float) 0.0, feature.getValue());
    }
}
