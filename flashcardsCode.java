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
   public static void main(String[] args) {
      // OBV I SHALL DELETE THIS WHEN MERGING WITH OTHERS
      String path = "/Users/cynthia/Documents/CompSci11/summatives/flashcardsFinal/";
      String name = "test.txt";
      int fileLineLength = totalLinesInFile(path, name);
      String[] questionsArr = new String[fileLineLength];
      String[] answersArr = new String[fileLineLength];

      readTxtFile(path, name, questionsArr, answersArr);
   }

   /**
    * @author Cynthia Lei Determine the total number of lines in the given file.
    *         This is so we can initialize arrays the size of the total file lines
    * 
    * @param filePath given file's path to access the file
    * @param fileName given file's name to access the file
    * @return the number of lines in the file
    */
   public static int totalLinesInFile(String filePath, String fileName) {
      int totalLines = 0;
      try {
         File file = new File(filePath + fileName);
         BufferedReader br = new BufferedReader(new FileReader(file));
         while ((br.readLine()) != null) {
            totalLines++;
         }
         br.close();
      }

      // Program cannot find file
      catch (IOException e) {
         System.out.println("Invalid file");
      }
      return totalLines;
   }

   // Reads the file
   public static void readTxtFile(String filePath, String fileName, String[] questionsArray, String[] answersArray) {
      String txtLine = "";
      int questionMarkIndex = 0;
      int totalFileLines = 0;
      String question = "";
      String answer = "";
      int lineNum = 0; // tells us the index of the element to populate for the question and answer arrays

      try {
         File file = new File(filePath + fileName);
         BufferedReader br = new BufferedReader(new FileReader(file));
         while ((txtLine = br.readLine()) != null) {
            // we assume the user's txt file is formatted with 1 question, denotated
            // by a '?' and followed by the answer ALL ON THE SAME LINE
            // in addition, there should be a space after the '?'
            // Example:
            // What is your name? Mr. Ho

            questionMarkIndex = findQuestionMarkTxt(txtLine);
            // The question starts from the beginning of each line to the '?'
            // the questionMarkIndex goes up to but does not include the '?'
            // so we need to add 1 to include the '?' in the question string
            question = txtLine.substring(0, questionMarkIndex + 1);
            questionsArray[lineNum] = question;
            
            // We assume the answer is followed by a space after the '?'
            // so we add 2 and go to the line length since the answer ends at the end of the line
            answer = txtLine.substring(questionMarkIndex + 2, txtLine.length());
            answersArray[lineNum] = answer;

            lineNum++; // next line, next element

         }
         System.out.println("questions arr: ");
         printArr(questionsArray);
         System.out.println("answers arr: ");
         printArr(answersArray);

         br.close();
      }

      // Program cannot find file
      catch (IOException e) {
         System.out.println("Invalid file");
      }
   }

   /**
    * @author Cynthia Lei Determine the location of the question the txt line
    * 
    * @param txtLine Given a line of a txt file
    * @return the index of the question mark to locate the end of a question;
    *         otherwise return 0 if the user messes up and there is no question in
    *         the line
    */
   public static int findQuestionMarkTxt(String txtLine) {
      int len = txtLine.length();
      for (int i = 0; i < len; i++) {
         // search for '?'
         if (txtLine.charAt(i) == '?') {
            return i;
         }
      }
      return 0;
   }

   public static void printArr(String[] arr) {
      for (int i = 0; i < arr.length; i++) {
         System.out.print(arr[i] + ", ");
      }
      System.out.println();
   }

}
