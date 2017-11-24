/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c45algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cornwall
 */
public class C45Algorithm {

    public static String classifier1, classifier2, classifier3;
    public static int chosenAttrib, testAttribPos, testInstancePos;
    public static int numRows = 5;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String file = "C:\\Users\\cornwall\\Downloads\\owls15.csv";

        //Read in file
        ArrayList<ArrayList<String>> csv = read(file);

        //Get classifier
        csv.get(4).remove(0);
        ArrayList<String> classifiers = new ArrayList();
        classifiers = csv.get(4);
        
//        ArrayList<String> uniqueClass = getClassifiers(classifiers);
//        for(int i=0; i<uniqueClass.size(); i++){
//            System.out.println("---------------------");
//            System.out.println(uniqueClass.get(i));
//        }

        //Convert attribute values from strings to doubles
        ArrayList<Double> attrib1 = convertToDouble(csv.get(0));
        ArrayList<Double> attrib2 = convertToDouble(csv.get(1));
        ArrayList<Double> attrib3 = convertToDouble(csv.get(2));
        ArrayList<Double> attrib4 = convertToDouble(csv.get(3));

        ArrayList<ArrayList<Double>> allAttribs = new ArrayList();
        allAttribs.addAll(Arrays.asList(attrib1, attrib2, attrib3, attrib4));
        
       
         //All test code.. Delete away!
        //Create Subset (Which is the total set for now)
        SubSet original = new SubSet(allAttribs, classifiers);
        int[] infoGain1 = calcInfoGain(original);

        System.out.println("------------------------SPLIT1----------------------------");
        ArrayList<SubSet> split1 =  split(original, infoGain1[0], infoGain1[1]);
        System.out.println("------------------------SPLIT2----------------------------");
        int[] infoGain2 = calcInfoGain(split1.get(1));
        ArrayList<SubSet> split2 =  split(split1.get(1), infoGain2[0], infoGain2[1]);
        System.out.println("------------------------SPLIT3----------------------------");
        int[] infoGain3 = calcInfoGain(split2.get(0));
        ArrayList<SubSet> split3 =  split(split2.get(0), infoGain3[0], infoGain3[1]);
        System.out.println("------------------------SPLIT4----------------------------");
        int[] infoGain4 = calcInfoGain(split3.get(1));
        ArrayList<SubSet> split4 =  split(split3.get(1), infoGain4[0], infoGain4[1]);


    
    }
    
    public static void createTree(String name, Double val, Node left, Node right){
        Node rootNode = new Node("Wing-width", 0.6, null, null);
        Node node1 = new Node("longeared Owl", null, null, null);
        Node node2 = new Node("Wing-width", 0.7, null, null);
        rootNode.addLeft(node1);
        rootNode.addRight(node2);
        rootNode.print();
    }
    
    public static int countClassifiers(String target, ArrayList<String> classifiers){
        int counter = 0;
        for (int i=0; i<classifiers.size(); i++){
            if (classifiers.get(i).equals(target))
                    counter++;
        } 
        return counter;
    }
    
    public static ArrayList<SubSet> split(SubSet subset, int attribPos, int instPos){
        
        //Need to create these arrays using a loop6
       ArrayList<String> classifiers = subset.getClassifiers(); 
       ArrayList<ArrayList<Double>> allAttribs = subset.getAttribs();
       ArrayList<Double> targetAttrib = subset.getAttribs().get(attribPos);
       ArrayList<Double> attrib1 = new ArrayList();
       ArrayList<Double> attrib2 = new ArrayList();
       ArrayList<Double> attrib3 = new ArrayList();
       ArrayList<Double> attrib4 = new ArrayList();
       ArrayList<Double> attrib1r = new ArrayList();
       ArrayList<Double> attrib2r = new ArrayList();
       ArrayList<Double> attrib3r = new ArrayList();
       ArrayList<Double> attrib4r = new ArrayList();
       ArrayList<String> leftClassArray = new ArrayList();
       ArrayList<String> rightClassArray = new ArrayList();
       ArrayList<ArrayList<Double>> leftAttribsArray = new ArrayList();
       ArrayList<ArrayList<Double>> rightAttribsArray = new ArrayList();
     
     //REALLY NEED TO CHANGE THIS PART.... Add loop to create arrays based on number of attribs
          leftAttribsArray.add(attrib1);
          leftAttribsArray.add(attrib2);
          leftAttribsArray.add(attrib3);
          leftAttribsArray.add(attrib4);
          
          rightAttribsArray.add(attrib1r);
          rightAttribsArray.add(attrib2r);
          rightAttribsArray.add(attrib3r);
          rightAttribsArray.add(attrib4r);
           

       System.out.println("TARGET = " + targetAttrib.get(instPos));
       for (int j=0; j<targetAttrib.size(); j++){

           if (targetAttrib.get(j)<=targetAttrib.get(instPos)){
               leftClassArray.add(classifiers.get(j));
               for (int k=0; k<allAttribs.size(); k++){
                   leftAttribsArray.get(k).add(allAttribs.get(k).get(j));
                   
               }
           }
           
           else{ 
               rightClassArray.add(classifiers.get(j));
               for (int k=0; k<allAttribs.size(); k++){
                   rightAttribsArray.get(k).add(allAttribs.get(k).get(j));    
               }  
           }
       }
       
       SubSet leftSub = new SubSet(leftAttribsArray, leftClassArray);
       SubSet rightSub = new SubSet(rightAttribsArray, rightClassArray);
       
       ArrayList<SubSet> subsetArray = new ArrayList();
       subsetArray.add(0, leftSub);
       subsetArray.add(1, rightSub);
       
       
       
       //Some testing code
       System.out.println("---------- LEFT ---------");
       leftSub.printSubSet();
       System.out.println("---------- RIGHT ---------");
       rightSub.printSubSet();
       
       System.out.println("right class array size = " + rightClassArray.size());
        return subsetArray;
    }

   
    public static int[] calcInfoGain(SubSet subSet) {
        
        ArrayList<ArrayList<Double>> attribs = new ArrayList();
        attribs = subSet.getAttribs();
        double finalGain = 0;
        int finalPos = 0;
        int[] attribPosInstancePos = {0,0};
        ArrayList<ArrayList<Double>>  entArrayHolder = new ArrayList();
        double lowestEnt = 2;
        int lowestEntPos = 0;
        int attribPos = 0;
        double newThreshold = 0;
        ArrayList<Double> entArray;


        for (attribPos = 0; attribPos < attribs.size(); attribPos++) {
            entArray = new ArrayList();
            double denom1 = 0, denom2 = 0, num1 = 0, num2 = 0, num3 = 0;
            double class1Entropy = 0, class2Entropy = 0, class3Entropy = 0;


            ArrayList<String> target = subSet.getClassifiers();
            ArrayList<Double> attrib = attribs.get(attribPos);
            
            // Count the number of instances of a certain class
           int class1Count = countClassifiers(classifier1, target);

            for (int i = 0; i < attrib.size(); i++) {
                
               // setClassifiers(target, i);
                ArrayList<String> classifierArray = setClassifiers(target, i);
//                classifier1 = classifierArray.get(0);
//                classifier2 = classifierArray.get(1);
//                classifier3 = classifierArray.get(2);
//                
               // System.out.println("C1" + classifier1 + "C2" + classifier2 + "C3" + classifier3);
                Double threshold = attrib.get(i);
                denom1 = 0;
                denom2 = 0;
                num1 = 0;
                num2 = 0;
                num3 = 0;

                for (int j=0; j < attrib.size(); j++) {

                    if (attrib.get(j) <= threshold) {
                        denom1 += 1;
                    
                        if (target.get(j).equals(classifier1)) {
                            num1 += 1;    
                        }         
                    } 

                    if (attrib.get(j) > threshold) {
                        denom2 += 1;

                        if (target.get(j).equals(classifier2)) {
                            num2 += 1;
                        }
                        if (target.get(j).equals(classifier3)) {
                            num3 += 1;
                        }
                    }

                }
                
                if (num1==denom1 && num1==class1Count){
                    attribPosInstancePos [0] = attribPos;
                    attribPosInstancePos [1] = i;
                    //Test code... DELETE
                    // System.out.println("Lowest Entropy = " + lowestEnt);
                    System.out.println("Attrib Position =" + attribPos);
                    System.out.println("Lowest Entropy Position = " + i );
                    //return attribPosInstancePos;
                }
                //If some number perfectly splits the data
                if (denom2 == 0) {
                    class2Entropy = 0;
                    class3Entropy = 0;
                } else {
                    //Entropy calculations
                    class2Entropy = (num2 / denom2) * ((Math.log(num2 / denom2)) / Math.log(2));
                    class3Entropy = (num3 / denom2) * ((Math.log(num3 / denom2)) / Math.log(2));
                }
                class1Entropy = (num1 / denom1) * ((Math.log(num1 / denom1)) / Math.log(2));
                

                //Following if statement may be wrong
                if (Double.isNaN(class2Entropy) || Double.isInfinite(class2Entropy)) {
                    class2Entropy = 0;
                }
                
                if (Double.isNaN(class3Entropy) || Double.isInfinite(class3Entropy)) {
                    class3Entropy = 0;
                }
                double setSize = (double)(attrib.size());
                double entropy = -((denom1/setSize)*class1Entropy) - ((denom2/setSize)*class2Entropy) - ((denom2/setSize)*class3Entropy);
                entArray.add(entropy);  

                
          // for now, I'm just looking at the right side of the Info Gain equation so I'm looking for the smallest values
                
            }
            entArrayHolder.add(entArray);
            

        }
        
        for (int i = 0; i < entArrayHolder.size(); i++) {
            entArray = entArrayHolder.get(i);
            for (int j = 0; j < entArray.size(); j++) {
               // System.out.println("***" + entArray.get(j));
                if (entArray.get(j) <= lowestEnt) {
                    newThreshold = entArray.get(j);
                    lowestEntPos = j;
                    lowestEnt = entArray.get(j);
                    finalPos = attribPos;
                    attribPosInstancePos[0] = i;
                    attribPosInstancePos[1] = j;
                }
            }
        }

         System.out.println("*****ARRAY********");
         System.out.println("Attrib Position =" + attribPosInstancePos[0]);
         System.out.println("Lowest Entropy Position = " + attribPosInstancePos[1]);
         
           
        return attribPosInstancePos;
    }
    //   }    

    //Returns the values for an attribute as doubles without the headings
    public static ArrayList<Double> convertToDouble(ArrayList<String> stringArray) {
        ArrayList<Double> doubleArray = new ArrayList();
        for (int i = 1; i < stringArray.size(); i++) {

            double f = Double.parseDouble(stringArray.get(i));
            doubleArray.add(f);
            //System.out.println(floatArray.get(i-1));
        }

        return doubleArray;
    }
    
  
  
    public static ArrayList<String> setClassifiers(ArrayList<String> classifiers, int pos) {
        Set<String> uniqueSet = new HashSet<String>(classifiers);
        Object [] uniqueArray = uniqueSet.toArray();
        ArrayList<String> classifierArray = new ArrayList();
        classifier1 = classifiers.get(pos);
        
        for (int i=0; i<uniqueArray.length; i++){ 
            if (!(uniqueArray[i].toString().matches(classifier1))){
                classifierArray.add(uniqueArray[i].toString());   
            }
        } 
        classifier2 = classifierArray.get(0);
        if (classifierArray.size()>=3){
        classifier3 = classifierArray.get(1);
        }
       // classifierArray.add(0, classifier1);
        
//        
//        for (int i=0; i<classifierArray.size(); i++){
//            System.out.println(classifierArray.get(i));
//        }
        
        
//         
//            if (classifiers.get(pos).equals("LongEaredOwl")){
//                classifier1 = "LongEaredOwl";
//                classifier2 = "BarnOwl";
//                classifier3 = "SnowyOwl";
//            }
//            else if (classifiers.get(pos).equals("BarnOwl")){
//                classifier1 = "BarnOwl";
//                classifier2 = "LongEaredOwl";
//                classifier3 = "SnowyOwl";
//            }    
//         
//            else{
//                classifier1 = "SnowyOwl";
//                classifier2 = "BarnOwl";
//                classifier3 = "LongEaredOwl";
//            }
// 
//            
//            if (classifiers.get(pos).equals(uniqueArray[i].toString())){
//                classifier1 = uniqueArray[i].toString();
//                classifier2 = uniqueArray[i].toString();
//                classifier3 = uniqueArray[i].toString();
//            }
//            else if (classifiers.get(pos).equals(uniqueArray[i+1])){
//                classifier1 = uniqueArray[i+1].toString();
//                classifier2 = uniqueArray[i].toString();
//                classifier3 = uniqueArray[i+1].toString();
//            }    
//         
//            else{
//                classifier1 = uniqueArray[i+2].toString();
//                classifier2 = uniqueArray[i+1].toString();
//                classifier3 = uniqueArray[i].toString();
//            }
    
return classifierArray;

    }

    public static ArrayList<ArrayList<String>> read(String file) {
        String line = "";
        String splitOn = ",";
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        //create array holding arrays of attributes and classes individually
        for (int i=0; i<numRows; i ++){
            ArrayList<String> row = new ArrayList();
            rows.add(row);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            while ((line = br.readLine()) != null) {

                String[] columns = line.split(splitOn);
                //Add each column to its own arraylist
                for (int i=0; i<rows.size(); i++){
                    rows.get(i).add(columns[i]);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;

    }

}
