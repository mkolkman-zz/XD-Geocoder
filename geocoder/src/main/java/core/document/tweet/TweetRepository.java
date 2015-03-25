package core.document.tweet;

import core.document.DocumentRepository;
import core.toponym.Toponym;
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
        while(corpusReader.hasNextDocument()){
            try {
                tweets.add((Tweet) corpusReader.getNextDocument());
            } catch (ParseException e) {
                System.err.println("Incorrect input format detected. Please check your input file.");
            }
        }
    }

    public int getDocumentCount() {
        return tweets.size();
    }

    public int getToponymCount() {
        int count = 0;
        for(Tweet t : tweets) {
            count += t.getToponyms().size();
        }
        return count;
    }

    public int getGeonamesIdCount() {
        int count = 0;
        for(Tweet t : tweets) {
            List<Toponym> toponyms = t.getToponyms();
            for(Toponym top : toponyms) {
                if(top.getGeonamesIds() != null)
                    count += top.getGeonamesIds().size();
            }
        }
        return count;
    }

}
