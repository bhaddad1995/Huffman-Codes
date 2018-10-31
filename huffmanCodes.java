import java.io.ObjectInputStream;

// Blake Haddad & Andy Nguyen
// CS-570
// Huffman Codes Assignment

import java.io.*;
import java.util.Scanner;
import java.util.*;

class huffmanCodes{

    public String input = "";
    HashMap<Character, Integer> characterCountMap = new HashMap<Character, Integer>();
    HashMap<Character, Double> characterPercentageMap = new HashMap<Character, Double>();

    public static void main(String[] args){
        huffmanCodes huffCodes = new huffmanCodes();
        huffCodes.loadFile();
        huffCodes.printInput();
        huffCodes.populateCharacterCountMap();
        huffCodes.printCharacterCountMap();
        huffCodes.calculateFrequencyPercentages();
        huffCodes.printCharacterPercentMap();
        huffCodes.sortFrequencyPercentages();
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
            if(charIsLetter(s.charAt(i))){
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
        }
    }

    public void sortFrequencyPercentages(){
        Map<Character, Double> treeMap = new TreeMap<Character, Double>(characterPercentageMap);
        printMap(treeMap);
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() 
				+ " Value : " + entry.getValue());
        }
    }

    public boolean charIsLetter(char a){
        int x = (int) a;
        if(a >= 65 && a <= 122){
            return true;
        }else{
            return false;
        }
    }

}