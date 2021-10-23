/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.luceneassessment1;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Karina
 */
public class Indexer {
    private static String INDEX_DIR = "src/index";

    void indexDoc(ArrayList<Document> documents, int type) throws IOException {
        Analyzer analyzer;
        if (type == 1) {
            System.out.println("English Analyzer");
            analyzer = new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        } else if (type == 2) {
            System.out.println("Simple Analyzer");
            analyzer = new SimpleAnalyzer();
        } else if (type == 3) {
            System.out.println("Keyword Analyzer");
            analyzer = new KeywordAnalyzer();
        } else if (type == 4) {
            System.out.println("Whitespace Analyzer");
            analyzer = new WhitespaceAnalyzer();
        } else {
            System.out.println("Standard Analyzer");
            analyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
        }
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
        IndexWriterConfig indexConfig = new IndexWriterConfig(analyzer);
        indexConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(directory, indexConfig);
        try {
            if (documents.size() > 0) {
                indexWriter.addDocuments(documents);
            }
            indexWriter.close();
            directory.close();
        } catch (IOException e) {
            System.out.println("IOException: " + String.valueOf(e));
            e.printStackTrace();
        }
    }
}
