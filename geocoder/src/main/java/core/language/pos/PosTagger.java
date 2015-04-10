package core.language.pos;

import core.language.word.Word;

import java.util.Iterator;
import java.util.List;

public interface PosTagger extends Iterator<Word> {

    List<Word> tagWordList(List<Word> sentence);
}
