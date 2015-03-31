package core.language.document.news;

import core.language.document.DocumentRepository;
import io.corpus.CorpusReader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository implements DocumentRepository {

    private final CorpusReader corpusReader;
    private List<Article> articles = new ArrayList<Article>();

    public ArticleRepository(CorpusReader corpusReader) {
        this.corpusReader = corpusReader;
    }

    @Override
    public void loadDocuments() {
        try {
            while(corpusReader.hasNextDocument()) {
                articles.add((Article) corpusReader.getNextDocument());
            }
        } catch (ParseException e) {
            System.err.println("Incorrect input format detected. Please check your input file.");
        }
    }

    @Override
    public int getDocumentCount() {
        return articles.size();
    }

    @Override
    public int getToponymCount() {
        int count = 0;
        for (Article article : articles) {
            count+= article.getToponymCount();
        }
        return count;
    }

    @Override
    public int getGeonamesIdCount() {
        int count = 0;
        for(Article article : articles) {
            article.getGeonamesIdCount();
        }
        return count;
    }
}
