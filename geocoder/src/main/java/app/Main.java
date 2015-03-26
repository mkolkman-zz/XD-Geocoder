package app;

import core.document.DocumentRepository;
import core.document.tweet.TweetRepository;
import io.csv.CsvCorpusReader;
import io.csv.CsvLineParser;

import java.io.*;
import java.util.Arrays;

public class Main {

    /**
     * Reads and parses a GATCorpus and echos some info
     * @param args
     */
    public static void main(String[] args) {
        String[] argOptions = {"GAT", "GTT"};
        String corpusType = args[0];
        if(args.length != 3 || !Arrays.asList(argOptions).contains(corpusType))
            printUsageInstructions();
        else {
            CsvLineParser lineParser = CsvCorpusReader.CsvLineParserFactory.getCsvLineParser(corpusType);
            String inputFile = args[1];
            String outputFile = args[2];
            try {
                DocumentRepository documents = new TweetRepository(new CsvCorpusReader(new BufferedReader(new FileReader(inputFile)), lineParser));
                documents.loadDocuments();
                printStatistics(documents);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private static void printUsageInstructions() {
        System.out.println("USAGE:");
        System.out.println("\tCsvCorpusReader <lineparser> <filepath>");
        System.out.println("");
        System.out.println("lineparser\tThe parser used to parse each line. Either 'GAT' or 'GTT'.");
        System.out.println("inputFile\tFull path to the GATCorpus csv file");
        System.out.println("outputFile\tFull path where the tgn corpus gets written");
    }

    private static void printStatistics(DocumentRepository documents) {
        System.out.println("Tweet count: " + documents.getDocumentCount());
        System.out.println("Toponym count: " + documents.getToponymCount());
        System.out.println("GeoNames id count: " + documents.getGeonamesIdCount());
    }
}
