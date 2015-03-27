package core.language.document;

public interface DocumentRepository {

    void loadDocuments();

    int getDocumentCount();

    int getToponymCount();

    int getGeonamesIdCount();

}
