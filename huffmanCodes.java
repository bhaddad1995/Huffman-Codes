import java.io.ObjectInputStream;

// Blake Haddad & Andy Nguyen
// CS-570
// Huffman Codes Assignment

import java.io.*;
import java.util.Scanner;

class huffmanCodes{

    public String input = "";
    public static void main(String[] args){
        huffmanCodes huffCodes = new huffmanCodes();
        huffCodes.loadFile();
        huffCodes.printInput();
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

    public boolean charIsLetter(char a){
        int x = (int) a;
        if(a >= 65 && a <= 122){
            return true;
        }else{
            return false;
        }
    }

}