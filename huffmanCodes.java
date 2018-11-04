import java.io.ObjectInputStream;

// Blake Haddad & Andy Nguyen
// CS-570
// Huffman Codes Assignment

import java.io.*;
import java.util.Scanner;
import java.util.*;

class treeNode{
    private treeNode leftChild;
    private treeNode rightChild;
    private treeNode parent;
    private double value;
    private char letter;

    treeNode(){
        setLeftChild(null);
        setRightChild(null);
        setParent(null);
    }

    treeNode(treeNode left, treeNode right, double value){
        setLeftChild(left);
        setRightChild(right);
        setValue(value);
        setLetter('\u0000');
        setParent(null);
    }

    treeNode(treeNode left, treeNode right, double value, char letter){
        setLeftChild(left);
        setRightChild(right);
        setValue(value);
        setLetter(letter);
        setParent(null);
    }

    public void setValue(double val){
        this.value = val;
    }

    public double getValue(){
        return this.value;
    }

    public void setLetter(char letter){
        this.letter = letter;
    }

    public char getLetter(){
        return this.letter;
    }

    public void setLeftChild(treeNode left){
        this.leftChild = left;
    }

    public treeNode getLeftChild(){
        return this.leftChild;
    }

    public void setRightChild(treeNode right){
        this.rightChild = right;
    }

    public treeNode getRightChild(){
        return this.rightChild;
    }

    public void setParent(treeNode parent){
        this.parent = parent;
    }

    public treeNode getParent(){
        return this.parent;
    }
}

class forestNode{
    private double weight;
    private treeNode root;

    forestNode(){

    }

    forestNode(double weight, treeNode root){
        setWeight(weight);
        setRoot(root);
    }

    public double getWeight(){
        return this.weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public treeNode getRoot(){
        return this.root;
    }

    public void setRoot(treeNode root){
        this.root = root;
    }
}

class forest{
    public Vector<forestNode> forestElements = new Vector<forestNode>();
    String huffCode = "";

    public void insert(forestNode element){

        forestElements.add(element);

    }

    public forestNode returnMin(){
        int length = forestElements.size();
        forestNode currentMin = new forestNode();
        double currentMinWeight = Double.MAX_VALUE;
        int currentMinIndex = -1;
        for(int i = 0; i < length; i++){
            if(forestElements.get(i).getWeight() < currentMinWeight){
                currentMin = forestElements.get(i);
                currentMinWeight = forestElements.get(i).getWeight();
                currentMinIndex = i;
            }
        }
        if(currentMinIndex >= 0){
            remove(currentMinIndex);
        }

        System.out.println("Min found: Element " + currentMin.getRoot().getLetter() + " with weight of " + currentMin.getWeight());

        return currentMin;
    }

    public void remove(int index){
        forestElements.remove(index);
    }

    public int getSize(){
        return forestElements.size();
    }

    public void printLeafNodes(treeNode root) 
    { 
        // if node is null, return 
        if (root == null){ 
            return;
        }
        
        // if node is leaf node, print its data     
        if (root.getLeftChild() == null && root.getRightChild() == null){ 
            System.out.println("Leaf node " + root.getLetter() + " with weight of " + root.getValue() + "and huff code " + huffCode);  
            return; 
        } 
    
        // if left child exists, check for leaf  
        // recursively 
        if (root.getLeftChild() != null){
            huffCode = huffCode + "0";
            printLeafNodes(root.getLeftChild());
            huffCode = huffCode.substring(0,huffCode.length()-1);
        }
            
        // if right child exists, check for leaf  
        // recursively 
        if (root.getRightChild() != null){ 
            huffCode = huffCode + "1";
            printLeafNodes(root.getRightChild());
            huffCode = huffCode.substring(0,huffCode.length()-1); 
        }
    }  

    public void printElements(){
        int s = forestElements.size();
        for(int i = 0; i < s; i++){
            System.out.println("Forest Node element " + forestElements.get(i).getRoot().getLetter() +" with weight" + forestElements.get(i).getWeight());
        }
    }
}

class huffmanCodes{

    public String input = "";
    public HashMap<Character, Integer> characterCountMap = new HashMap<Character, Integer>();
    public HashMap<Character, Double> characterPercentageMap = new HashMap<Character, Double>();
    public Map<Character, Double> treeMap;
    public HashMap<Character,String> huffmanCodeMap = new HashMap<Character,String>();
    public forest myForest = new forest();

    public static void main(String[] args){
        huffmanCodes huffCodes = new huffmanCodes();
        huffCodes.loadFile();
        huffCodes.printInput();
        huffCodes.populateCharacterCountMap();
        huffCodes.printCharacterCountMap();
        huffCodes.calculateFrequencyPercentages();
        huffCodes.printCharacterPercentMap();
        huffCodes.sortFrequencyPercentages();
        huffCodes.writeOutputFile();
        huffCodes.generateHuffmanCodes();
    }


    public void loadFile(){
        try {
            String x;
            FileReader fileIn = new FileReader("infile.dat");
            BufferedReader in = new BufferedReader(fileIn);
            x = in.readLine();
            while (x != null) {
                input = input + formatInput(x);
                x = in.readLine();
            }
            in.close();
            fileIn.close();

         } catch (IOException i) {
            System.err.print("Error loading game with exception:\n"+i);
            i.printStackTrace();
            System.exit(0);
         }
    }

    public String formatInput(String s){
        String formattedInput = "";
        for(int i = 0; i < s.length(); i++){
            if(charIsLetter(s.charAt(i)) || charIsNumber(s.charAt(i))){
                formattedInput = formattedInput + s.charAt(i);
            }else{
                continue;
            }
        }
        return formattedInput;
    }

    public void printInput(){
        System.out.println(input);
    }

    public void printCharacterCountMap(){
        System.out.println(characterCountMap);
    }

    public void printCharacterPercentMap(){
        System.out.println(characterPercentageMap);
    }

    public void populateCharacterCountMap(){
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            Integer val = characterCountMap.get(c);
            if (val != null) {
                characterCountMap.put(c, val + 1);
            }
            else {
               characterCountMap.put(c, 1);
           }
        }
    }

    public void calculateFrequencyPercentages(){
        Integer totalCharCount = input.length();
        for ( Character key : characterCountMap.keySet() ) {
            Double percent = (characterCountMap.get(key).doubleValue() / totalCharCount.doubleValue()) * 100;
            characterPercentageMap.put(key, percent);
            treeNode newTreeNode = new treeNode(null,null,percent,key);
            forestNode newForestNode= new forestNode(percent, newTreeNode);
            myForest.insert(newForestNode);
        }
    }

    public void sortFrequencyPercentages(){
        treeMap = new TreeMap<Character, Double>(characterPercentageMap);
        printMap();
    }

    public void writeOutputFile(){
        try{
            FileWriter fileOut = new FileWriter("outfile.dat");
            BufferedWriter out = new BufferedWriter(fileOut);
            out.write("Symbol|Frequency\n");
            out.write(getTreeMapString());
            out.close();
        }catch(IOException i){
            System.out.println("IO Exception: "+i);
        }
    }

    public void printMap() {
        Map<Character, Double> map = treeMap;
        for (Map.Entry<Character, Double> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() 
				+ " Value : " + entry.getValue());
        }
    }

    public String getTreeMapString() {
        Map<Character, Double> map = treeMap;
        String  s = "";
        for (Map.Entry<Character, Double> entry : map.entrySet()) {
            s = s + "   "+ entry.getKey() 
				+ ",  " + entry.getValue() + "%\n";
        }
        return s;
    }

    public boolean charIsLetter(char a){
        int x = (int) a;
        if(a >= 65 && a <= 122){
            return true;
        }else{
            return false;
        }
    }

    public boolean charIsNumber(char a){
        int x = (int) a;
        if(a >= 48 && a <= 57){
            return true;
        }else{
            return false;
        }
    }

    public void generateHuffmanCodes(){
        myForest.printElements();
        while(myForest.getSize() > 1){
            forestNode min1 = myForest.returnMin();
            forestNode min2 = myForest.returnMin();
            double parentWeight = min1.getWeight() + min2.getWeight();
            treeNode parentTreeNode = new treeNode(min1.getRoot(), min2.getRoot(), parentWeight);
            forestNode newForestNode = new forestNode(parentWeight, parentTreeNode);
            myForest.insert(newForestNode);
            myForest.printElements();
        }
        System.out.println("Printing leaf nodes....");
        myForest.printLeafNodes(myForest.returnMin().getRoot());
    }

}