package core.language.pos;

public interface PosTagger {
    String tagString(String sentence);

    String tagTokenizedString(String sentence);
}
