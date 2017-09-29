#! /bin/bash

if [ $# -eq 2 ]
  then
    mkdir -p out/production/BayesianLearning
    find ./src/com/ctk150230/ -name "*.java" > sources.txt
    javac @sources.txt -d ./out/production/BayesianLearning/

    java -cp ./out/production/BayesianLearning com.ctk150230.Main $1 $2
else
    echo "Error: Not enough arguments supplied, please provide a training and testing filename"
fi
