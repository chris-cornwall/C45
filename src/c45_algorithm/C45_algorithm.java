/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c45_algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author cristoir95
 */
public class C45_algorithm {
    
    
    
    public static void main(String[] args) {
        String file = "/Users/cristoir95/Downloads/owls15.csv";
       
        //Create arrays for total file and for values found in attributes
        ArrayList<ArrayList<String>> csv = read(file);
        ArrayList<Float> attrib1Floats = convertToFloat(csv.get(0));
        ArrayList<Float> attrib2Floats = convertToFloat(csv.get(1));
        ArrayList<Float> attrib3Floats = convertToFloat(csv.get(2));
        ArrayList<Float> attrib4Floats = convertToFloat(csv.get(3));
        
        
        Attribute attrib1 = new Attribute (attrib1Floats, csv.get(4));
        Attribute attrib2 = new Attribute (attrib2Floats, csv.get(4));
        Attribute attrib3 = new Attribute (attrib3Floats, csv.get(4));
        Attribute attrib4 = new Attribute (attrib4Floats, csv.get(4));
        
       ArrayList<String> classes =  attrib1.getClassifier();
       for (String i:classes)
           System.out.println(i);
        
       

        
    }
    
    //Returns the values for an attribute without the headings
    public static ArrayList<Float> convertToFloat(ArrayList<String> stringArray){
       ArrayList<Float> floatArray = new ArrayList();
        for (int i=1; i<stringArray.size(); i++){
            
                float f = Float.parseFloat(stringArray.get(i));
                floatArray.add(f);
                //System.out.println(floatArray.get(i-1));
            }
        
        return floatArray;    
    }
    
   /* public static void calcEntropy(ArrayList<Float> attrib, ArrayList<String> target){
        int counter = 0;
        Float threshold = attrib.get(0);
   
    }
     */   
    
    
    public static ArrayList<ArrayList<String>> read(String file){    
        String line = "";
        String splitOn = ",";
        
        
        ArrayList<String> bodyLength = new ArrayList();
        ArrayList<String> wingLength = new ArrayList();
        ArrayList<String> bodyWidth = new ArrayList();
        ArrayList<String> wingWidth = new ArrayList();
        ArrayList<String> type = new ArrayList ();
       
        //create array holding arrays of attributes and classes individually
        ArrayList<ArrayList <String>> csvArray = new ArrayList();
        csvArray.addAll(Arrays.asList(bodyLength, wingLength, bodyWidth, wingWidth, type)); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
 
            while ((line = br.readLine()) != null) {
                
                String[] owls = line.split(splitOn);
                
                //Add each column to its own arraylist
                csvArray.get(0).add(owls[0]);
                csvArray.get(1).add(owls[1]);
                csvArray.get(2).add(owls[2]);
                csvArray.get(3).add(owls[3]); 
                csvArray.get(4).add(owls[4]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvArray;
        /*for (int i=0; i<csvArray.size(); i++){
            for (int j=0; j<csvArray.get(i).size(); j++){
                System.out.println(csvArray.get(i).get(j));
            }
        }*/
    }
    
}
