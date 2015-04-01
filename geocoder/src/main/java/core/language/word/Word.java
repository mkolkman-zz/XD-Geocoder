package core.language.word;

public class Word {

    private int start;
    private int end;
    private String text;

    public Word(int start, int end, String text) {
        this.start = start;
        this.end = end;
        this.text = text;
    }

    public Word() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
