package com.mycompany.luceneassessment1;

import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        Indexer.indexDoc();
        Inquierer.inquireVSM();
        Inquierer.searchBM25();
    }
}
