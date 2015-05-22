package core.language.document.historic;

import core.language.document.Document;
import core.language.word.Word;
import edu.stanford.nlp.util.StringUtils;

import java.util.List;

public class HistoricDocument extends Document {

    private List<Word> words;

    public void setWords(List<Word> words) {
        this.words = words;
        this.text = StringUtils.join(words, " ");
    }
}
