package com.ctk150230;

/**
 * @author Charles Krol
 * @version 1.0
 * @since 2017-09-21
 */
public class Main {

    public static void main(String[] args) {
        if(args[0].equals("--help") || args.length != 2) {
            usage();
            return;
        }

        String trainingFile = args[0];
        String testingFile = args[1];

    }

    private static void usage(){
        System.out.println("Bayesian Learning Program");
        System.out.println("Author: Charles Krol\n");
        System.out.println("Usage: java BayesianLearning.class <TrainingFile> <TestingFile>");
    }
}
