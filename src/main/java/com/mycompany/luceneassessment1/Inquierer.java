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
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
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

    public void searchQueries(List<Map<String, String>> queries, int type) throws IOException {
        // Create directory if it does not exist
        File outputDir = new File("results");
        if (!outputDir.exists())
            outputDir.mkdirs();

        /**
         * To Query using different analyzers: 0-> standard analyzer 1-> english
         * analyzer 2->simple analyzer 3->whitespace analyzer
         */
        Analyzer analyzer;
        if (type == 1) {
            analyzer = new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        } else if (type == 2) {
            analyzer = new SimpleAnalyzer();
        } else if (type == 3) {
            analyzer = new WhitespaceAnalyzer();
        } else {
            analyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        }

        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

        // create objects to read and search across the index
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        String runIdentifier = "";
        String fileName = "";
        /**
         * For each analyzer run the index searcher with two similarities the BM25
         * Similiarity and the Classic Similiarity one
         */
        for (int i = 0; i < 2; i++) {
            ArrayList<String> resultsList = new ArrayList<String>();
            if (i == 0) {
                indexSearcher.setSimilarity(new BM25Similarity());
                System.out.println("Inquiring with BM25 Scoring");
                runIdentifier = "simpleQueryBM25Scoring";
                switch (type) {
                case 1:
                    fileName = "resultsBMS25_English.txt";
                    break;
                case 2:
                    fileName = "resultsBMS25_Simple.txt";
                    break;
                case 3:
                    fileName = "resultsBMS25_Whitespace.txt";
                    break;
                default:
                    fileName = "resultsBMS25_Standard.txt";
                    break;
                }
            } else {
                indexSearcher.setSimilarity(new ClassicSimilarity());
                System.out.println("Inquiring with VSM Scoring");
                runIdentifier = "simpleQueryVSMScoring";
                switch (type) {
                case 1:
                    fileName = "resultsVSM_English.txt";
                    break;
                case 2:
                    fileName = "resultsVSM_Simple.txt";
                    break;
                case 3:
                    fileName = "resultsVSM_Witespace.txt";
                    break;
                default:
                    fileName = "resultsVSM_Standard.txt";
                    break;
                }
            }

            parseSearch(queries, analyzer, indexSearcher, resultsList, runIdentifier);

            Files.write(Paths.get("results/" + fileName), resultsList, Charset.forName("UTF-8"));
        }
    }

    private static void parseSearch(List<Map<String, String>> queryList, Analyzer analyzer, IndexSearcher indexSearcher,
            List<String> resultsList, String runIdentifier) {
        try {
            for (int i = 0; i < queryList.size(); i++) {

                Map<String, String> Query = queryList.get(i);
                MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
                        new String[] { "Title", "Authors", "Bibliography", "Content" }, analyzer);

                Query query = queryParser.parse(Query.get("Query"));

                // Searching for the top hits for the query
                TopDocs topDocs = indexSearcher.search(query, 1400);
                ScoreDoc[] scores = topDocs.scoreDocs;

                for (int j = 0; j < scores.length; j++) {
                    int docId = scores[j].doc;
                    Document doc = indexSearcher.doc(docId);
                    resultsList.add(
                            Query.get("ID") + " 0 " + doc.get("ID") + " 0 " + scores[j].score + " " + runIdentifier);
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

}
