/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c45algorithm;

/**
 *
 * @author cornwall
 */
public class Node {
    String name;
    Double val;
    Node left;
    Node right;
    
    public Node (String name, Double val, Node left, Node right){
        this.name = name;
        this.val =  val;
        this.left = left;
        this.right = right;
    }
    
    public void addLeft (Node node){
        this.left = node;   
    }
    
    public void addRight (Node node){
        this.right = node;   
    }
    
    public void print (){
        System.out.print(name);   
        if (left == null && right == null){
            System.out.print("\n");   
        }
        if (left != null){
            System.out.println("<=" + val);
            left.print();
        }
        
        if (right != null){
            val = right.val;
            System.out.println(">" + val);
            right.print();
        }
    }
    
}
