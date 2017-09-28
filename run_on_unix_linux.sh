#! /bin/bash

find ./src/com/ctk150230/ -name "*.java" > sources.txt
javac @sources.txt -d ./out/production/BayesianLearning/

java -cp ./out/production/BayesianLearning com.ctk150230.Main $1 $2