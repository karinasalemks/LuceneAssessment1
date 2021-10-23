package com.mycompany.luceneassessment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;

public class DocumentParser {
    private static String CORPUS = "src/cran/cran.all.1400";
    private static String QUERIES = "src/cran/cran.qry";

    ArrayList<Document> getParsedFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(CORPUS)));
        // ArrayList of documents
        ArrayList<Document> documents = new ArrayList<Document>();
        // currently read line
        String line;
        // create custom field type
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        fieldType.setStored(true);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);
        /**
         * the code below creates a document with 5 fields: ID, Title, Author,
         * Bibliography, and Content
         */
        line = bufferedReader.readLine();
        while (line != null) {
            if (line.startsWith(".I")) {
                Document document = new Document();
                String[] id = line.split("\\s+");
                String type = "";
                String data = "";
                document.add(new TextField("ID", id[1], Field.Store.YES));
                line = bufferedReader.readLine();
                while (line != null) {
                    if (!line.startsWith(".I")) {
                        if (line.startsWith(".T")) {
                            // the following three lines are used when the previous field is completed
                            if (!type.equals("") && !data.equals("")) {
                                document.add(new Field(type, data, fieldType));
                            }
                            type = "Title";
                            data = bufferedReader.readLine();
                        } else if (line.startsWith(".A")) {
                            if (!type.equals("") && !data.equals("")) {
                                document.add(new Field(type, data, fieldType));
                            }
                            type = "Authors";
                            data = bufferedReader.readLine();
                        } else if (line.startsWith(".B")) {
                            if (!type.equals("") && !data.equals("")) {
                                document.add(new Field(type, data, fieldType));
                            }
                            type = "Bibliography";
                            data = bufferedReader.readLine();
                        } else if (line.startsWith(".W")) {
                            if (!type.equals("") && !data.equals("")) {
                                document.add(new Field(type, data, fieldType));
                            }
                            type = "Content";
                            data = bufferedReader.readLine();
                        } else {
                            // This section is for when the field has more than one line of code
                            data += " " + line;
                        }
                    } else {
                        /**
                         * This section is for when the last line of content has been added and we add
                         * the new field and add the complete document to the array of type Document
                         */
                        if (!type.equals("") && !data.equals("")) {
                            document.add(new Field(type, data, fieldType));
                            documents.add(document);
                        }
                        break;
                    }
                    line = bufferedReader.readLine();
                    if(line == null){
                        if (!type.equals("") && !data.equals("")) {
                            document.add(new Field(type, data, fieldType));
                            documents.add(document);
                        }
                    }
                }
     
            }
        }
        bufferedReader.close();
        return documents;
    }

    List<Map<String, String>> getQueries() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(QUERIES)));
        // List of queries of type HashMap
        List<Map<String, String>> queries = new ArrayList<Map<String, String>>();
        // currently read line
        String line;
        /**
         * the code below creates a HashMap with 2 fields: ID, and Content
         */
        line = bufferedReader.readLine();
        int i = 1;
        while (line != null) {
            if (line.startsWith(".I")) {
                Map<String, String> queryMap = new HashMap<String, String>();
                String type = "";
                String data = "";
                queryMap.put("ID", String.valueOf(i));
                line = bufferedReader.readLine();
                while (line != null) {
                    line = line.replace("?", "");
                    if (!line.startsWith(".I")) {
                        if (line.startsWith(".W")) {
                            if (!type.equals("") && !data.equals("")) {
                                queryMap.put(type, data);
                            }
                            type = "Query";
                            data = bufferedReader.readLine();
                        } else {
                            // This section is for when the field has more than one line of code
                            data += " " + line;
                        }
                    } else {
                        /**
                         * This section is for when the last line of content has been added and we add
                         * the new field and add the complete Query
                         */
                        if (!type.equals("") && !data.equals("")) {
                            queryMap.put(type, data);
                            queries.add(queryMap);
                        }
                        i++;
                        break;
                    }
                    line = bufferedReader.readLine();
                    if(line ==null){
                        if (!type.equals("") && !data.equals("")) {
                            queryMap.put(type, data);
                            queries.add(queryMap);
                        }
                    }
                }
            }
        }
        bufferedReader.close();
        return queries;
    }
}
