package core.document;

import core.toponym.Toponym;

import java.util.List;

abstract public class Document {

    protected String text;

    protected List<Toponym> toponyms;

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

}
