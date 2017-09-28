package com.ctk150230;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>DataSet</h1>
 *
 * A class to parse data files and organize them into data structure
 * for use in Bayesian Learning. The class also calculates useful information of each
 * data set such as the number of attributes, data instances, and the number of data instances with
 * positive/negative outcomes.
 *
 * The <b>attributeMap</b> holds an ArrayList for each Attribute with the attribute values for each data
 * instance. Each LinkedHashMap corresponds to an attribute and the each entry in the contained ArrayLists
 * corresponds to a data instance.
 *
 * The <b>attributeList</b> holds the names of each attribute.
 *
 * The <b>classifierList</b> holds the final classification value for each data instance
 *
 * @see ArrayList
 * @see LinkedHashMap
 * @see FileReader
 * @see BufferedReader
 * @see FileNotFoundException
 * @see IOException
 *
 * @author Charles Krol
 * @version 1.0
 * @since 2017-09-26
 */
class DataSet {

    private int numOfAttributes;                                    // Number of Attributes used to classify data
    private List<String> attributeList;                             // Holds the names of the Attributes
    private List<Integer> classifierList;                           // Holds the final classification value of data instances
    private LinkedHashMap<String, ArrayList<Integer>> attributeMap; // Holds an ArrayList for each Attribute and each data
                                                                    // Instances' value for that attribute
    private int dataInstances;                                      // The number of data instances in this set
    private String fileName;                                        // The name of the file holding the data
    private int numOfPositives;                                     // The number of data instances with a positive classification
    private int numOfNegatives;                                     // The number of data instances with a negative classification

    /**
     * Class constructor which accepts a file name, initializes all fields to default values,
     * and calls the parse file function to sort the contents of the file into data structures for
     * pricessing
     * @throws IOException from the parseFile method and is taken care of by checking before closing
     * BufferedReader and FileReader objects
     * @param fn a filename containing training or testing data
     */
    DataSet(String fn) throws IOException{
        classifierList = new ArrayList<>();
        attributeMap = new LinkedHashMap<>();
        attributeList = new ArrayList<>();
        numOfAttributes = 0;
        dataInstances = 0;
        numOfPositives = 0;
        numOfNegatives = 0;
        fileName = fn;
        parseFile();

    }

    /**
     * Parse the data file and organize the data into various Data Structures
     * For use in the training and testing of Bayesian Learning Algorithm
     * If the file does not exist the program displays the filename causing the error
     * and exits
     * @throws IOException from closing the BufferedReader and FileReader, handled with an if
     * statement to check if either are null before closing
     */
    private void parseFile() throws IOException{
        FileReader fr = null;
        BufferedReader br = null;
        try {
            // Create FileReader and BufferedReader
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            // Read in line from file
            String line  = br.readLine();

            // Split line based on whitespaces
            String[] splitStr = line.split("\\s+");

            // Build the AttributeMap and initialize ArrayLists within
            // Populate the AttributeList
            buildAttributeMap(splitStr);

            // Read in first data values
            line = br.readLine();

            // Iterate through Data Instances from the file
            while(line != null){
                // Split line based on whitespaces
                splitStr = line.split("\\s+");

                // Add current line's data to the attribute map
                fillAttributeMap(splitStr);

                // Increment number of Data Instances
                dataInstances++;

                // Read next line
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Filename " + fileName + " does not exist");
            System.exit(1);
        } finally {
            if (br != null)
                br.close();
            if (fr != null)
                fr.close();
        }
    }

    /**
     * Builds the AttributeMap with Attribute Names as keys and initializes
     * the ArrayLists corresponding with each field
     * The ArrayList containing the Attribute Names is built and the number of
     * Attributes is stored in numOfAttributes
     *
     * @param split a String Array containing the first line of the file which holds
     *              the attribute names
     */
    private void buildAttributeMap(String[] split){
        // Build AttributeMap and initialize the ArrayLists within
        for(int i = 0; i < (split.length - 1); i++){
            attributeMap.put(split[i], new ArrayList<>());
        }

        // Built AttributeList
        for (String aSplit : split) {
            attributeList.add(aSplit);
            numOfAttributes++;
        }
    }

    /**
     * Processes a single line of data representing an instance of a datum and store
     * the attribute values into the AttributeMap and the datum's classification into an
     * ArrayList
     * @param split a String Array containing a line from the data file with the
     *              values for the attributes of a data instance
     */
    private void fillAttributeMap(String[] split){
        // Store values in the attributeMap
        int i = 0;
        for (Map.Entry<String, ArrayList<Integer>> entry : attributeMap.entrySet()) {
            entry.getValue().add(Integer.parseInt(split[i]));
            i++;
        }

        // Add the classification of a data instance into an ArrayList
        classifierList.add(Integer.parseInt(split[i]));

        // Count the number of positive and negative classifications.
        if(Integer.parseInt(split[i]) == 1)
            numOfPositives++;
        else
            numOfNegatives++;
    }

    /**
     * A getter for the getNumOfAttributes field
     * @return The number of attributes in this data set
     */
    int getNumOfAttributes() {
        return numOfAttributes;
    }

    /**
     * A getter for the AttributeMap
     * @return The attributeMap holding the attributes and their ArrayLists that
     * store the values of each data instance for that attribute
     */
    LinkedHashMap<String, ArrayList<Integer>> getAttributeMap() {
        return attributeMap;
    }

    /**
     * A getter for the AttributeList
     * @return An ArrayList containing the names of each Attribute
     */
    List<String> getAttributeList() {
        return attributeList;
    }

    /**
     * A getter for the dataInstances field
     * @return The number of data instances in this data set
     */
    int getDataInstances() {
        return dataInstances;
    }

    /**
     * A getter for the classifierList ArrayList
     * @return an ArrayList containing the final classification values for each
     * data instance
     */
    List<Integer> getClassifierList() {
        return classifierList;
    }

    /**
     * A getter for the numOfPositives
     * @return the number of data instances with a positive classification value
     */
    int getNumOfPositives() {
        return numOfPositives;
    }

    /**
     * A getter for the numOfNegatives
     * @return the number of data instances with a negative classification value
     */
    int getNumOfNegatives() {
        return numOfNegatives;
    }
}
