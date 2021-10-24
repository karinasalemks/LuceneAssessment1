package com.mycompany.luceneassessment1;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;

public class CustomAnalyzerK {
    public Analyzer createCustomAnalyzer() throws IOException {
        Analyzer analyzer = CustomAnalyzer.builder()
          .withTokenizer("standard")
          .addTokenFilter("lowercase")
          .addTokenFilter("stop")
          .addTokenFilter("kStem")
          .build();
        return analyzer;
    }
}