package core.language.document.tweet;

import core.language.document.DocumentRepository;
import io.CorpusReader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TweetRepository implements DocumentRepository {

    private List<Tweet> tweets = new ArrayList<Tweet>();
    private CorpusReader corpusReader;

    public TweetRepository(CorpusReader corpusReader) {
        this.corpusReader = corpusReader;
    }

    public void loadDocuments() {
        try {
            while (corpusReader.hasNextDocument()) {
                tweets.add((Tweet) corpusReader.getNextDocument());
            }
        } catch (ParseException e) {
            System.err.println("Incorrect input format detected. Please check your input file.");
        }
    }

    public int getDocumentCount() {
        return tweets.size();
    }

    public int getToponymCount() {
        int count = 0;
        for (Tweet t : tweets) {
            count += t.getToponymCount();
        }
        return count;
    }

    public int getGeonamesIdCount() {
        int count = 0;
        for (Tweet t : tweets) {
            count += t.getGeonamesIdCount();
        }
        return count;
    }

}
