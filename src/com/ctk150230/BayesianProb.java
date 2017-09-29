package com.ctk150230;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * <h1>BayesianProb</h1>
 *
 * This class creates a DataSet from a filename, calculates the class
 * probabilities, and calculates the conditional probabilities of each attribute.
 * Upon completion of the calculations the probabilities are output to the console
 *
 * The <b>classProbabilities</b> ArrayList contains the probabilities of a given data instance
 * being classified as positive or negative.
 *
 * The <b>conditionalProbabilities</b> and <b>negativeConditionalProbabilities</b> LinkedHashMaps
 * contain ArrayLists that the probabilities of an attribute being classified as positive/negative
 * given that the data was classified as positive/negative. <b>conditionalProbabilities</b> is given
 * that the overall data classification was positive and <b>negativeConditionalProbabilities</b> is given
 * that the overall data classification was negative. The contained ArrayLists contain the conditional probabilities
 * that the attribute itself is positive or negative.
 *
 * @see DataSet
 * @see ArrayList
 * @see LinkedHashMap
 * @see IOException
 *
 * @author Charles Krol
 * @version 1.0
 * @since 2017-09-26
 */
class BayesianProb {

    private DataSet dataSet;
    private List<Double> classProbabilities;
    private LinkedHashMap<String, ArrayList<Double>> conditionalProbabilities;
    private LinkedHashMap<String, ArrayList<Double>> negativeConditionalProbabilities;

    /**
     * Class constructor which initializes data structures used to calculate probabilities.
     * Initializes a DataSet passing a filename to the constructor which parses the data file to
     * store in the DataSet. It then calculates the probabilities and outputs the results to the console
     *
     * @throws IOException the exception is from closing the BufferedReader and FileReader objects in the
     * data set and the objects are checked before attempting to close them
     *
     * @param filename the name of the file containing the data to be trained with
     */
    BayesianProb(String filename) throws IOException{
        classProbabilities = new ArrayList<>();
        conditionalProbabilities = new LinkedHashMap<>();
        negativeConditionalProbabilities = new LinkedHashMap<>();
        dataSet = new DataSet(filename);

        // Calculate the probabilities
        calculateProbabilities();

        // Print the results to the console
        printProbabilities();
    }

    /**
     * Calls methods to calculate class probabilities and conditional probabilities
     */
    private void calculateProbabilities() {

        calculateClass();
        calculateConditional();

    }

    /**
     * Calculates positive and negative class probabilities
     */
    private void calculateClass(){
        int positive = 0;

        // Count data instances where the class is positive
        for(int data : dataSet.getClassifierList()){
            if(data == 1)
                positive++;
        }

        // Calculate the probability of the data having a positive class
        double positiveProb = ((double)positive)/((double)dataSet.getDataInstances());
        positiveProb = round2DecimalPlaces(positiveProb);

        // Store the positive and negative probabilities
        classProbabilities.add((1-positiveProb));
        classProbabilities.add(positiveProb);
    }

    /**
     * Grabs data from the DataSet that is used to calculate the conditional probabilities
     * and passes them to the methods to calculate them
     */
    private void calculateConditional(){
        // Get data from the DataSet that is used in calculating the conditional probabilities
        LinkedHashMap<String, ArrayList<Integer>> attributeMap = dataSet.getAttributeMap();
        List<Integer> classifierList = dataSet.getClassifierList();

        // Calculate conditional probabilities
        calculatePositiveClassConditional(attributeMap, classifierList);
        calculateNegativeClassConditional(attributeMap, classifierList);

    }

    /**
     * Calculates conditional probabilities where the Class value is positive
     * @param attribMap contains attributes values for each data instance contained in the data set
     * @param classList contains the final classification values for each data instance in the data set
     */
    private void calculatePositiveClassConditional(LinkedHashMap<String, ArrayList<Integer>> attribMap, List<Integer> classList){

        // Iterate through attributes to calculate their conditional probability
        for (Map.Entry<String, ArrayList<Integer>> entry : attribMap.entrySet()) {

            // Used to count where the instances are positive
            int positive = 0;

            // Used to grab the class value for an instance out of the classList
            int i = 0;

            // Add attribute to the LinkedHashMap storing all of the conditional probabilities
            // and initialize the ArrayList in each cell which stores the positive and negative
            // probabilities
            conditionalProbabilities.put(entry.getKey(), new ArrayList<>());

            // Iterate through the data instances and count the positive instances with the specified
            // attribute as positive
            for(int data : entry.getValue()){
                if(data == 1 && classList.get(i) == 1)
                    positive++;
                i++;
            }

            // Calculate the conditional probability of this attribute
            double positiveProb = ((double)positive)/((double)dataSet.getNumOfPositives());

            // Round the probability off to 2 decimal places
            positiveProb = round2DecimalPlaces(positiveProb);

            // Add the positive and negative conditional probabilities to the data structure
            conditionalProbabilities.get(entry.getKey()).add(1 - positiveProb);
            conditionalProbabilities.get(entry.getKey()).add(positiveProb);
        }
    }

    /**
     * Calculates conditional probabilities where the Class value is negative
     * @param attribMap contains attributes values for each data instance contained in the data set
     * @param classList contains the final classification values for each data instance in the data set
     */
    private void calculateNegativeClassConditional(LinkedHashMap<String, ArrayList<Integer>> attribMap, List<Integer> classList){

        // Iterate through attributes to calculate their conditional probability
        for (Map.Entry<String, ArrayList<Integer>> entry : attribMap.entrySet()) {

            // Used to count where the instances are positive
            int positive = 0;

            // Used to grab the class value for an instance out of the classList
            int i = 0;

            // Add attribute to the LinkedHashMap storing all of the conditional probabilities
            // and initialize the ArrayList in each cell which stores the positive and negative
            // probabilities
            negativeConditionalProbabilities.put(entry.getKey(), new ArrayList<>());

            // Iterate through the data instances and count the negative instances with the specified
            // attribute as positive
            for(int data : entry.getValue()){
                if(data == 1 && classList.get(i) == 0)
                    positive++;
                i++;
            }

            // Calculate the conditional probability of this attribute
            double positiveProb = ((double)positive)/((double)dataSet.getNumOfNegatives());

            // Round the probability off to 2 decimal places
            positiveProb = round2DecimalPlaces(positiveProb);

            // Add the positive and negative conditional probabilities to the data structure
            negativeConditionalProbabilities.get(entry.getKey()).add(1 - positiveProb);
            negativeConditionalProbabilities.get(entry.getKey()).add(positiveProb);
        }
    }

    /**
     * Print out the calculated class probabilities and conditional probabilities
     */
    private void printProbabilities(){

        // Grab the list of Attribute names to print out the "class" name
        List<String> attributeList = dataSet.getAttributeList();

        // Iterate over the positive and negative class probabilities
        for(int i = 1; i >= 0; i--){
            // Print out class Probabilities
            System.out.print("P(" + attributeList.get(dataSet.getNumOfAttributes() - 1) + "=" + i + ")=");
            System.out.printf("%.2f ", classProbabilities.get(i));

            // If i == 1 print out positive class conditional probabilities
            if(i == 1)
                for (Map.Entry<String, ArrayList<Double>> entry : conditionalProbabilities.entrySet()){
                    for(int j = 1; j >= 0; j--){
                        System.out.printf("P(" + entry.getKey() + "=" + j + "|" + i + ")=%.2f ", entry.getValue().get(j));
                    }
                }
            else
                for (Map.Entry<String, ArrayList<Double>> entry : negativeConditionalProbabilities.entrySet()){
                    for(int j = 1; j >= 0; j--){
                        System.out.printf("P(" + entry.getKey() + "=" + j + "|" + i + ")=%.2f ", entry.getValue().get(j));
                    }
                }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * Rounds calculated probability values to 2 decimal places
     * @param round the decimal to be rounded to 2 decimal places
     * @return a decimal rounded to 2 decimal places
     */
    private double round2DecimalPlaces(double round){
        return Math.round(round * 100) / 100.0;
    }

    /**
     * Getter for returning the calculated Class Probabilities List used in testing the accuracy
     * of the calculations
     * @return Class Probabilities List
     */
    List<Double> getClassProbabilities() {
        return classProbabilities;
    }

    /**
     * Getter for returning the calculated Conditional Probabilities Map where the class value is Positive
     * used in testing the accuracy of the calculations
     * @return Positive Conditional Probabilities Map
     */
    LinkedHashMap<String, ArrayList<Double>> getConditionalProbabilities() {
        return conditionalProbabilities;
    }

    /**
     * Getter for returning the calculated Conditional Probabilities Map where the class value is Negative
     * used in testing the accuracy of the calculations
     * @return Positive Conditional Probabilities Map
     */
    LinkedHashMap<String, ArrayList<Double>> getNegativeConditionalProbabilities() {
        return negativeConditionalProbabilities;
    }
}
