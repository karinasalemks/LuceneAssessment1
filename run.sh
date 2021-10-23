mvn clean install
mvn compile
mvn exec:java -Dexec.mainClass="com.mycompany.luceneassessment1.MainFile"
cd trec_eval
./trec_eval -m runid -m map -m gm_map -m P.5 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsVSM.txt
./trec_eval -m runid -m map -m gm_map -m P.5 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsBM25.txt

