/**
 * Date: May 20, 2021
 * Name: Cynthia Lei, Daiphy Lee, Johnny He, Sophia Nguyen
 * Teacher: Mr. Ho
 * Description: Flashcards Final Project
 */
 
// Imports
import java.util.Scanner; // scanner
import java.io.IOException;
import java.io.*;
import java.io.File; 

class flashcardsCode {
   public static void main(String[] args){
      String path = "/Users/cynthia/Documents/CompSci11/summatives/flashcardsFinal/";
      String name = "test.txt";

      readTxtFile(path, name);
   }

    // Reads the file
    public static void readTxtFile(String filePath, String fileName){
       String line = "";
       try{ 
         File file = new File(filePath+fileName);
         BufferedReader br = new BufferedReader(new FileReader(fileName));
         while ((line = br.readLine()) != null){
            System.out.println(line);
         }

       }
       // Program cannot find file
       catch (IOException e){
          System.out.println("Invalid");
       } 

  }

}
