package com.mycompany.luceneassessment1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;

public class MainFile {
    public static void main(String[] args) throws IOException {
        DocumentParser documentParser = new DocumentParser();
        ArrayList<Document> documents = new ArrayList<Document>();
        List<Map<String, String>> queries = documentParser.getQueries();
        documents = documentParser.getParsedFile();
        Indexer indexer = new Indexer();
        Inquierer inquierer = new Inquierer();
        /**
         * To Index using different analyzers: 0-> standard analyzer 1-> english
         * analyzer 2->simple analyzer 3->whitespace analyzer
         * As well as search the queries using these analyzers
         */
        indexer.indexDoc(documents, 0);
        inquierer.searchQueries(queries, 0);
        indexer.indexDoc(documents, 1);
        inquierer.searchQueries(queries, 1);
        indexer.indexDoc(documents, 2);
        inquierer.searchQueries(queries, 2);
        indexer.indexDoc(documents, 3);
        inquierer.searchQueries(queries, 3);
    }
}
