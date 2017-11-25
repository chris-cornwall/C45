/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c45algorithm;

import java.util.ArrayList;

/**
 *
 * @author cornwall
 */
public class SubSet {
    ArrayList <ArrayList<Double>> attribs = new ArrayList();
    ArrayList <String> classifier;
    
    public SubSet( ArrayList <ArrayList<Double>> attribs,  ArrayList <String> classifier){
        this.attribs=attribs;
        this.classifier=classifier;
    }
    
    public void printSubSet(){
//        for (int i=0; i<attribs.size(); i++){
            for (int j=0; j<attribs.get(2).size(); j++){
                System.out.println(attribs.get(2).get(j) + "-" + classifier.get(j));
//            }
           
        }
    }

    public ArrayList<ArrayList<Double>> getAttribs() {
        return attribs;
    }

    public ArrayList<String> getClassifiers() {
        return classifier;
    }
    
    public void setAttribs(ArrayList<ArrayList<Double>> attribs) {
        this.attribs = attribs;
    }

    public void setClassifier(ArrayList<String> classifier) {
        this.classifier = classifier;
    }
}
