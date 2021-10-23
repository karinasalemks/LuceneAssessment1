package com.mycompany.luceneassessment1;

import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        Indexer indexer = new Indexer();
        Inquierer inquierer = new Inquierer();
        indexer.indexDoc();
        inquierer.searcher();
    }
}
