package transformers.word;

import core.language.pos.PosTag;
import core.language.word.Word;
import edu.stanford.nlp.ling.TaggedWord;
import core.language.word.stanford.StanfordWord;

import java.util.ArrayList;
import java.util.List;

public class StanfordWordTransformer {

    public List<StanfordWord> toStanfordSentence(List<Word> sentence) {
        List<StanfordWord> stanfordWords = new ArrayList<StanfordWord>();
        for (Word word : sentence) {
            stanfordWords.add(toStanfordWord(word));
        }
        return stanfordWords;
    }

    public StanfordWord toStanfordWord(Word word) {
        return new StanfordWord(word.getText());
    }

    public List<Word> fromTaggedSentence(List<TaggedWord> taggedSentence) {
        List<Word> sentence = new ArrayList<Word>();
        for (TaggedWord taggedWord : taggedSentence) {
            sentence.add(fromTaggedWord(taggedWord));
        }
        return sentence;
    }

    public Word fromTaggedWord(TaggedWord taggedWord) {
        return new Word(taggedWord.word(), fromStanfordTag(taggedWord.tag()));
    }

    public PosTag fromStanfordTag(String tag) {
        if(tag.equals("DT"))
            return PosTag.DETERMINER;
        else if(tag.equals("IN"))
            return PosTag.PREPOSITION;
        else if(tag.startsWith("JJ"))
            return PosTag.ADJECTIVE;
        else if(tag.startsWith("NN"))
            return PosTag.NOUN;
        else if(tag.startsWith("RB"))
            return PosTag.ADVERB;
        else if(tag.equals("UH"))
            return PosTag.INTERJECTION;
        else if (tag.startsWith("VB"))
            return PosTag.VERB;
        else if(tag.equals("CC"))
            return PosTag.CONJUNCTION;
        else if(tag.startsWith("PR"))
            return PosTag.PRONOUN;
        else
            return PosTag.OTHER;

    }
}
