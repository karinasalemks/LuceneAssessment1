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
        indexer.indexDoc(documents, 0);
        inquierer.searcher(queries, 0);
        indexer.indexDoc(documents, 1);
        inquierer.searcher(queries, 1);
    }
}
