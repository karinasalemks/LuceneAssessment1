mvn clean install
mvn compile
mvn exec:java -Dexec.mainClass="com.mycompany.luceneassessment1.MainFile" 
# cd trec_eval
./trec_eval -m runid -m map -m gm_map -m P.5 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsVSM_SA.txt
./trec_eval -m runid -m map -m gm_map -m P.5 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsBMS25_SA.txt
echo "English Analyzer"
./trec_eval -m runid -m map -m gm_map -m P.5 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsVSM_EA.txt
./trec_eval -m runid -m map -m gm_map -m P.5 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsBMS25_EA.txt

