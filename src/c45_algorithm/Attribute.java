/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c45_algorithm;

import java.util.ArrayList;

/**
 *
 * @author cristoir95
 */
public class Attribute {
    public static ArrayList<Float> attribs = new ArrayList();
    public static ArrayList<String> classifier;
    
    public Attribute(ArrayList<Float> attribs, ArrayList<String> classifier){
        this.attribs = attribs;
        this.classifier = classifier;
    }
    
    public static void setAttribs(ArrayList<Float> attribs) {
        Attribute.attribs = attribs;
    }

    public static void setClassifier(ArrayList<String> classifier) {
        Attribute.classifier = classifier;
    }

    public static ArrayList<Float> getAttribs() {
        return attribs;
    }

    public static ArrayList<String> getClassifier() {
        return classifier;
    }
    
          
}
