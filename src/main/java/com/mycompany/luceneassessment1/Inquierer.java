package com.mycompany.luceneassessment1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Inquierer {
    private static String INDEX_DIR = "src/index";

    public static void searchBM25() throws IOException {
        Analyzer standardAnalyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

        // create objects to read and search across the index
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        indexSearcher.setSimilarity(new BM25Similarity());

        ArrayList<String> resultsList = new ArrayList<String>();
        System.out.println("Inquiring with BM25 Scoring");
        // Create directory if it does not exist
        File outputDir = new File("results");
        if (!outputDir.exists())
            outputDir.mkdirs();

        List<Map<String, String>> queries = DocumentParser.getQueries();

        parseSearch(queries, standardAnalyzer, indexSearcher, resultsList, "simpleQueryBM25Scoring");

        Files.write(Paths.get("results/resultsBM25.txt"), resultsList, Charset.forName("UTF-8"));
    }

    public static void inquireVSM() throws IOException {
        Analyzer standardAnalyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

        // create objects to read and search across the index
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        indexSearcher.setSimilarity(new ClassicSimilarity());

        ArrayList<String> resultsList = new ArrayList<String>();
        System.out.println("Inquiring with VSM Scoring");
        // Create directory if it does not exist
        File outputDir = new File("results");
        if (!outputDir.exists())
            outputDir.mkdirs();

        List<Map<String, String>> queries = DocumentParser.getQueries();

        parseSearch(queries, standardAnalyzer, indexSearcher, resultsList, "simpleQueryVSMScoring");

        Files.write(Paths.get("results/resultsVSM.txt"), resultsList, Charset.forName("UTF-8"));
    }

    private static void parseSearch(List<Map<String, String>> queryList, Analyzer analyzer, IndexSearcher indexSearcher,
            List<String> resultsList, String runIdentifier) {
        try {
            for (int i = 0; i < queryList.size(); i++) {

                Map<String, String> Query = queryList.get(i);
                MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
                        new String[] { "Title", "Authors", "Bibliography", "Content" }, analyzer);

                Query query = queryParser.parse(Query.get("Query"));

                // Searching For Query
                TopDocs topDocs = indexSearcher.search(query, 1400);
                ScoreDoc[] scores = topDocs.scoreDocs;

                for (int j = 0; j < scores.length; j++) {
                    int docId = scores[j].doc;
                    Document doc = indexSearcher.doc(docId);
                    resultsList
                            .add(Query.get("ID") + " 0 " + doc.get("ID") + " 0 " + scores[j].score + " " + runIdentifier);
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

}
