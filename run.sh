mvn clean install
mvn compile
mvn exec:java -Dexec.mainClass="com.mycompany.luceneassessment1.MainFile" 
cd trec_eval
echo "############## Whitespace Analyzer ##############"
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsVSM_Witespace.txt
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsBMS25_Whitespace.txt
echo "############## Simple Analyzer ##############"
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsVSM_Simple.txt
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsBMS25_Simple.txt
echo "############## Standard Analyzer ##############"
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsVSM_Standard.txt
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsBMS25_Standard.txt
echo "############## English Analyzer ##############"
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsVSM_English.txt
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsBMS25_English.txt
echo "############## Karina's custom Analyzer ##############"
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsVSM_Karina.txt
./trec_eval -m runid -m map -m gm_map -m num_q -m num_ret -m num_rel -m num_rel_ret -m Rprec -m P.5,10 ../src/cran/QRelsCorrectedforTRECeval ../results/resultsBMS25_Karina.txt
