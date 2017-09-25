package com.ctk150230;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Charles Krol
 * @version 1.0
 * @since 2017-09-21
 */
public class DataSet {

    private int numOfAttributes; // Number of Attributes used to classify data
    private LinkedHashMap<String, ArrayList<Integer>> attributeMap;
    private List<List<Integer>> dataSet;
    private List<String> attributeList;
    private int dataInstances;

    /**
     * Class constructor which initializes the fields to the default values
     */
    public DataSet(){
        attributeMap = new LinkedHashMap<String, ArrayList<Integer>>();
        dataSet = new ArrayList<List<Integer>>();
        attributeList = new ArrayList<String>();
        numOfAttributes = 0;
        dataInstances = 0;

    }

    /**
     * Parse the data file and organize the data into various Data Structures
     * For use in the ID3 Algorithm
     * @param filename
     */
    public void parseFile(String filename){
        try {

            // Create FileReader and BufferedReader
            FileReader fr = null;
            BufferedReader br = null;
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            // Read in line from file
            String line  = br.readLine();

            // Split line based on whitespaces
            String[] splitStr = line.split("\\s+");

            // Build AttributeMap and initalize the ArrayLists within
            for(int i = 0; i < splitStr.length; i++){
                attributeMap.put(splitStr[i], new ArrayList<Integer>());
            }


            // Built AttributeList
            for(int i = 0; i < splitStr.length; i++){
                attributeList.add(splitStr[i]);
                numOfAttributes++;
            }

            // Read in first data values
            line = br.readLine();

            // Iterate through Data Instances from the file
            while(line != null){
                // Split line based on whitespaces
                splitStr = line.split("\\s+");

                // Store values in the attributeMap
                int j = 0;
                for (Map.Entry<String, ArrayList<Integer>> entry : attributeMap.entrySet()) {
                    entry.getValue().add(Integer.parseInt(splitStr[j]));
                    j++;
                }

                // Add Data Instances to dataSet ArrayList
                dataSet.add(dataInstances, new ArrayList<Integer>());

                for (int i = 0; i < numOfAttributes ; i++) {
                    dataSet.get(dataInstances).add(Integer.parseInt(splitStr[i]));
                }

                // Increment number of Data Instances
                dataInstances++;

                // Read nextline
                line = br.readLine();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Used to for debugging to check Data Parsing
     */
    public void checkParsing(){
        System.out.println("Check Data Parsing");

        // Print Out Attributes
        for (String attribute : attributeList) {
            System.out.print(attribute + "  ");
        }

        System.out.println();
        // Print Out Data Instances
        for (List instance : dataSet){
            for(int i = 0; i < numOfAttributes; i++) {
                System.out.print(instance.get(i) + "       ");
            }
            System.out.println();
        }
        System.out.println("\n");
        // Print Out Attributes
        for (String attribute : attributeList) {
            System.out.print(attribute + "  ");
        }
        System.out.println();
        for(int i = 0; i< dataInstances; i++) {
            for (Map.Entry<String, ArrayList<Integer>> entry : attributeMap.entrySet())
                System.out.print(entry.getValue().get(i) + "       ");

            System.out.println();
        }
    }
}
