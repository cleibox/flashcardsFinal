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
      // OBV I SHALL DELETE THIS WHEN MERGING WITH OTHERS
      String path = "/Users/cynthia/Documents/CompSci11/summatives/flashcardsFinal/";
      String name = "test.txt";

      readTxtFile(path, name);
   }
   
   // Reads the file
   public static void readTxtFile(String filePath, String fileName){
      String txtLine = "";
      int questionMarkIndex = 0;
      try{ 
         File file = new File(filePath+fileName);
         BufferedReader br = new BufferedReader(new FileReader(file));
         while ((txtLine = br.readLine()) != null){
            // we assume the user's txt file is formatted with 1 question, denotated
            // by a '?' and followed by the answer ALL ON THE SAME LINE
            // in addition, there should be a space after the '?'
            // Example: 
            // What is your name? Mr. Ho
            
            questionMarkIndex = findQuestionMarkTxt(txtLine);
            
            // The question starts from the beginning of each line to the '?'
            // the questionMarkIndex goes up to but does not include the '?'
            // so we need to add 1 to include the '?' in the question string
            System.out.println(txtLine.substring(0, questionMarkIndex + 1)); 
            // We assume the answer is followed by a space after the '?'
            // so we add 2
            // the answer ends at the end of the line
            System.out.println(txtLine.substring(questionMarkIndex + 2, txtLine.length())); 

         }
         br.close();
      }
      // Program cannot find file
      catch (IOException e){
         System.out.println("Invalid");
      } 
  }

  /**
   * @author Cynthia Lei
   * Determine the location of the question the txt line
   * 
   * @param txtLine Given a line of a txt file
   * @return the index of the question mark to locate the end of a question;
   * otherwise return 0 if the user messes up and there is no question in the line
   */
  public static int findQuestionMarkTxt(String txtLine){
     int len = txtLine.length();
     for (int i = 0; i < len; i++){
        // search for '?'
        if (txtLine.charAt(i) == '?'){
           return i;
         }
      }
      return 0;
}


}
