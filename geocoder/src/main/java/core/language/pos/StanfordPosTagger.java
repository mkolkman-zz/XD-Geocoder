package core.language.pos;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class StanfordPosTagger implements PosTagger {

    MaxentTagger tagger;

    public StanfordPosTagger(MaxentTagger tagger) {
        this.tagger = tagger;
    }

    @Override
    public String tagString(String sentence) {
        return tagger.tagString(sentence);
    }

    public String tagTokenizedString(String tokenizedString) {
        return tagger.tagTokenizedString(tokenizedString);
    }
}
