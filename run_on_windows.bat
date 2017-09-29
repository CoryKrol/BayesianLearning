if ("%2"=="") goto error


dir /s /B *.java > sources.txt
mkdir out
mkdir out\production
mkdir out\production\BayesianLearning
javac @sources.txt -d out\production\BayesianLearning

java -cp out\production\BayesianLearning com.ctk150230.Main %1 %2

:error
@echo Error: Not enough arguments supplied, please provide a training and testing filename
exit 1