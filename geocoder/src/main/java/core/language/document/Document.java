package core.language.document;

import core.language.word.Toponym;

import java.util.ArrayList;
import java.util.List;

abstract public class Document {

    protected String text;

    protected List<Toponym> toponyms = new ArrayList<Toponym>();

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setToponyms(List<Toponym> toponyms) {
        this.toponyms = toponyms;
    }

    public List<Toponym> getToponyms() {
        return toponyms;
    }

    public int getToponymCount() {
        return toponyms.size();
    }

    public int getGeonamesIdCount() {
        int count = 0;
        for (Toponym toponym : toponyms) {
            count+= toponym.getGeonamesIdCount();
        }
        return count;
    }

}
