package com.ctk150230;

import java.io.IOException;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>TestingSet</h1>
 *
 * A subclass of the DataSet class used to test the calculated probabilities from the
 * BayesianProb class and output the results to the console.
 *
 * <b>testData</b> contains LinkedHashMaps holding the attribute values for each data instance.
 * An entry in the List corresponds to a data instance and each contained LinkedHashMap holds
 * that instance's values on each attribute.
 *
 * @see DataSet
 * @see BayesianProb
 * @see ArrayList
 * @see LinkedHashMap
 * @see IOException
 *
 * @author Charles Krol
 * @version 1.0
 * @since 2017-09-26
 */

class TestingSet extends DataSet {

    // Used to store attribute vales for each data instance
    // ArrayList entries are data instances and the HashMaps within contain attribute values for
    // this each instance
    private List<LinkedHashMap<String, Integer>> testData = new ArrayList<>();

    /**
     * Class constructor that initializes the testData ArrayList with Linkedcd HashMaps
     * @param fn the name of the file containing the data used in testing
     * @throws IOException from the parseFile method in the super class, it is handled within the method
     */
    TestingSet(String fn) throws IOException{
        // Call super class constructor
        super(fn);

        // Add LinkedHashMaps to testData for the number of data instances we have
        for(int i = 0; i < super.getDataInstances(); i++)
            testData.add(new LinkedHashMap<>());

        // Build testData from super class data structures
        buildTestData();

    }

    /**
     * Creates testing data from the super class data structures
     *
     * Each ArrayList entry represents a data instance with a LinkedHashMap entry holding it's
     * attribute values
     */
    private void buildTestData(){

        // Used to keep track of which instance we are pulling data from with the super class
        int i = 0;

        // Iterate through testData ArrayList adding attribute values taken from super class
        for(LinkedHashMap<String, Integer> instance: testData) {
            for (Map.Entry<String, ArrayList<Integer>> entry : super.getAttributeMap().entrySet()) {
                instance.put(entry.getKey(), entry.getValue().get(i));
            }
            i++;
        }
    }

    /**
     * Iterates through each data instance and calculates the probabilities of whether the data
     * is more likely to be positive or negative, compares with the actual classification of the instance,
     * counts the number of correctly predicted classifications, it's percentage of all the data, and output
     * the results to the console
     *
     * @param trainingData contains conditional probabilities calculated from training data
     * @param testType A String used to format the correct output to identify what type of data
     *                 we are using to test the learned data
     */
    void testData(BayesianProb trainingData, String testType){

        // Keeps track of the number of correctly predicted data instances
        int correct = 0;

        // Used to iterate through data instances in the classifierList
        int i = 0;

        // Iterate through testing data and calculate probabilities of positive and negative
        // Classified data
        for(LinkedHashMap<String, Integer> instance: testData) {
            // Calculate the probabilities of whether this data instance is more likely
            // to have a positive or negative outcome
            double positiveProbability = testPositive(trainingData, instance);
            double negativeProbability = testNegative(trainingData, instance);

            // If correctly classified as positive or negative increment correct, else do nothing
            if ((positiveProbability > negativeProbability) && (super.getClassifierList().get(i) == 1))
                correct++;
            else if ((positiveProbability < negativeProbability) && (super.getClassifierList().get(i) == 0))
                correct++;
            i++;
        }

        // Calculate the percentage of correctly classified data and round it to 1 decimal place
        double percentCorrect = ((double)correct/(double)super.getDataInstances()) * 100;
        percentCorrect = round1DecimalPlace(percentCorrect);

        // Output results
        System.out.println("Accuracy on " + testType + " set (" + super.getDataInstances() + " instances): " + percentCorrect + "%\n" );
    }

    /**
     * Calculates the probability of the instance with specified attributes being classified
     * positively
     * @param trainingData contains conditional probabilities calculated from training data
     * @param instance A LinkedHashMap representing a single data instance and it's attribute values
     * @return the probability of this class being classified positively
     */
    private double testPositive(BayesianProb trainingData, LinkedHashMap<String, Integer> instance) {
        double positiveProbability = 1;

        // Iterate through attributes and grab the right conditional probability from the training data
        // for the specified attribute value
        try {
            for (Map.Entry<String, Integer> entry : instance.entrySet()) {
                if (entry.getValue() == 1)
                    positiveProbability *= trainingData.getConditionalProbabilities().get(entry.getKey()).get(1);
                else
                    positiveProbability *= trainingData.getConditionalProbabilities().get(entry.getKey()).get(0);
            }
        }catch (NullPointerException e) {
                System.out.println("Error: Mismatched Attributes, are you sure you selected the proper training/testing pair?");
                System.exit(1);
            }

        // Multiply by the probability of a classification being positive and return the value
        return positiveProbability * trainingData.getClassProbabilities().get(1);
    }

    /**
     * Calculates the probability of the instance with specified attributes being classified
     * negatively
     * @param trainingData contains conditional probabilities calculated from training data
     * @param instance A LinkedHashMap representing a single data instance and it's attribute values
     * @return the probability of this class being classified negatively
     */
    private double testNegative(BayesianProb trainingData, LinkedHashMap<String, Integer> instance) {
        double negativeProbability = 1;

        // Iterate through attributes and grab the right conditional probability from the training data
        // for the specified attribute value
        for (Map.Entry<String, Integer> entry : instance.entrySet()) {
            if (entry.getValue() == 1)
                negativeProbability *= trainingData.getNegativeConditionalProbabilities().get(entry.getKey()).get(1);
            else
                negativeProbability *= trainingData.getNegativeConditionalProbabilities().get(entry.getKey()).get(0);
        }

        // Multiply by the probability of a classification being negative and return the value
        return negativeProbability * trainingData.getClassProbabilities().get(0);
    }

    /**
     * Used to round the accuracy percentage to 1 decimal place
     * @param round the number to be rounded to 1 decimal place
     * @return the percentage rounded to 1 decimal place
     */
    private double round1DecimalPlace(double round){
        return Math.round(round * 10) / 10.0;
    }

}
