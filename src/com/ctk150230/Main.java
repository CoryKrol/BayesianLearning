package com.ctk150230;

import java.io.IOException;

/**
 * <h1>Bayesian Learning</h1>
 *
 * A program that implements the Bayesian Learning Algorithm used
 * to predict the most likely classification of data based on instances
 * of data using the values of the data's attributes.
 *
 * The training data is used to calculate the probabilities of data in
 * the data set being classified positively or negatively and calculate the
 * conditional probabilities of each attribute against whether whether the data
 * has been classified positively or negatively.
 *
 * Using this data it then tests the accuracy of the learned data against the training
 * data and testing data using Bayes Rule.
 *
 * @see DataSet
 * @see TestingSet
 * @see BayesianProb
 * @see IOException
 *
 * @author Charles Krol
 * @version 1.0
 * @since 2017-09-26
 */
public class Main {
    // TODO: implement checks for nonmatching testing and taining files
    public static void main(String[] args) throws IOException{
        if(args[0].equals("--help") || args.length != 2) {
            usage();
            return;
        }

        String trainingFile = args[0];
        String testingFile = args[1];

        BayesianProb bayProb = new BayesianProb(trainingFile);

        TestingSet trainingTest = new TestingSet(trainingFile);
        trainingTest.testData(bayProb, "Training");

        TestingSet testingTest = new TestingSet(testingFile);
        testingTest.testData(bayProb, "Testing");

    }

    private static void usage(){
        System.out.println("Bayesian Learning Program");
        System.out.println("Author: Charles Krol\n");
        System.out.println("Usage: java BayesianLearning.class <TrainingFile> <TestingFile>");
    }
}
