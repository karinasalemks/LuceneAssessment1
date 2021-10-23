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

    public void searcher(List<Map<String, String>> queries, int type) throws IOException {
        // Create directory if it does not exist
        File outputDir = new File("results");
        if (!outputDir.exists())
            outputDir.mkdirs();

        Analyzer analyzer;
        if (type == 1) {
            System.out.println("English Analyzer");
            analyzer = new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        } else {
            System.out.println("Standard Analyzer");
            analyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        }

        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

        // create objects to read and search across the index
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        String runIdentifier = "";
        String fileName = "";
        for (int i = 0; i < 2; i++) {
            ArrayList<String> resultsList = new ArrayList<String>();
            if (i == 0) {
                indexSearcher.setSimilarity(new BM25Similarity());
                System.out.println("Inquiring with BM25 Scoring");
                runIdentifier = "simpleQueryBM25Scoring";
                fileName = type == 1 ? "resultsBMS25_EA.txt" : "resultsBMS25_SA.txt";
            } else {
                indexSearcher.setSimilarity(new ClassicSimilarity());
                System.out.println("Inquiring with VSM Scoring");
                runIdentifier = "simpleQueryVSMScoring";
                fileName = type == 1 ? "resultsVSM_EA.txt" : "resultsVSM_SA.txt";
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

                // Searching For Query
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
