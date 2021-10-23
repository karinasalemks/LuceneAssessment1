mvn clean install
mvn compile

for i in 1 2
do
mvn exec:java -Dexec.mainClass="com.mycompany.luceneassessment1.MainFile" \
  -Dexec.args="$i"
cd trec_eval
./trec_eval -m runid -m map -m gm_map -m P.5 ../src/cran/QRelsCorrectedforTRECeval ../results/results.txt
done

