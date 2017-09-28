dir /s /B *.java > sources.txt
javac @sources.txt -d out\production\BayesianLearning

java -cp out\production\BayesianLearning com.ctk150230.Main %1 %2