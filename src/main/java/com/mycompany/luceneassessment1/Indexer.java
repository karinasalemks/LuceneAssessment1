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
            // analyzer = new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet());
            analyzer = new EnglishAnalyzer();
        } else {
            // analyzer = new StandardAnalyzer(EnglishAnalyzer.getDefaultStopSet());
            analyzer = new StandardAnalyzer();
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
