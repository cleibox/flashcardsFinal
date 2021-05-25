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
import java.util.ArrayList; // arrayList

class flashcardsCode {
   public static void main(String[] args) {
      // OBV I SHALL DELETE THIS WHEN MERGING WITH OTHERS
      String path = "/Users/cynthia/Documents/CompSci11/summatives/flashcardsFinal/";
      String name = "test.txt";
      ArrayList<String> questionsArrList = new ArrayList<String>(); // Create an ArrayList object
      ArrayList<String> answersArrList = new ArrayList<String>(); // Create an ArrayList object
      // String[] questionsArr = new String[fileLineLength];
      // String[] answersArr = new String[fileLineLength];

      readTxtFile(path, name, questionsArrList, answersArrList);
   }

   // /**
   //  * @author Cynthia Lei Determine the total number of lines in the given file.
   //  *         This is so we can initialize arrays the size of the total file lines
   //  * 
   //  * @param filePath user inputted file's path to access the file
   //  * @param fileName user inputted file's name to access the file
   //  * @return the number of lines in the file
   //  */
   // public static int totalLinesInFile(String filePath, String fileName) {
   //    int totalLines = 0;
   //    try {
   //       String line = "";
   //       File file = new File(filePath + fileName);
   //       BufferedReader br = new BufferedReader(new FileReader(file));
   //       while ((line = br.readLine()) != null) {
   //          // if (!line.equals("")){
   //          //    totalLines++; 
   //          //    // this is if we want to discard blank flashcards so we'd only count lines with either a question or answer
   //          // }
   //          totalLines++;
   //       }
   //       br.close();
   //    }

   //    // Program cannot find file
   //    catch (IOException e) {
   //       System.out.println("Invalid file");
   //    }
   //    return totalLines;
   // }

   /**
    * @author Cynthia Lei
    * Reads the .txt file by identifying the question and answer portion of each line.
    * Then it will sort the question and answer strings into their respective arrays 
    * 
    * @param filePath user inputted .txt file path
    * @param fileName user inputted .txt file name
    * @param questionsArray array that contains all the question strings
    * @param answersArray array that contains all the answer strings
    */
   public static void readTxtFile(String filePath, String fileName, ArrayList<String> questionsArrayList, ArrayList<String> answersArrayList) {
      String txtLine = " ";
      int questionMarkIndex = 0;
      String realQuestion = "";
      String realAnswer = "";

      try {
         File file = new File(filePath + fileName);
         BufferedReader br = new BufferedReader(new FileReader(file));
         while ((txtLine = br.readLine()) != null) {
            // we assume the user's txt file is formatted with 1 question, denotated
            // by a '?' and followed by the answer ALL ON THE SAME LINE
            // in addition, there should be a space after the '?'
            // Example:
            // What is your name? Mr. Ho
            System.out.println("line is <" + txtLine + ">");

            // Dividing the line into 2 parts: question and answer
            questionMarkIndex = findQuestionMarkTxt(txtLine); 
            
            // Adding the question portion into the question array
            realQuestion = getQuestion(txtLine, questionMarkIndex);
            troubleshootAndAddElementToArr(questionsArrayList, realQuestion);
            
            // Adding the answer portion into the answers array
            realAnswer = getAnswer(txtLine, questionMarkIndex);
            troubleshootAndAddElementToArr(answersArrayList, realAnswer);

            // if (!((questionsArray[lineNum].equals("Placeholder")) && (answersArray[lineNum].equals("Placeholder")))) {
            //    lineNum++; // no need to save the elements if both of them are placeholder. it's equivalent to a blank flashcard.
            // }
            
            System.out.println();

         }
         System.out.println("QUESTIONS arr: ");
         printArrList(questionsArrayList);
         System.out.println("ANSWERS arr: ");
         printArrList(answersArrayList);
         // System.out.println("Total line count: " + totalLinesInFile(filePath, fileName));

         br.close();
      }

      // Program cannot find file
      catch (IOException e) {
         System.out.println("Invalid file");
      }
   }

   /**
    * @author Cynthia Lei
    * Stores the question as a string
    *
    * @param txtLine this line from the .txt will be read
    * @param questionMarkLoc the index of the question mark in the line
    * @return the question portion in the line as a string
    */
   public static String getQuestion(String txtLine, int questionMarkLoc){
      // The question starts from the beginning of each line to the '?'
      // the questionMarkLoc goes up to but does not include the '?'
      // so we need to add 1 to include the '?' in the question string
      
      // if there is no question, questionMarkLoc + 1 = 0 so the question would be ""
      // that is important for troubleshooting (whether a placeholder is necessary)
      String question = txtLine.substring(0, questionMarkLoc + 1);
      System.out.println("question is !" + question);
      return question;
   }

   /**
    * @author Cynthia Lei
    * Stores the answer as a string
    * 
    * @param txtLine this line from the .txt will be read
    * @param questionMarkLoc the index of the question mark in the line
    * @return the answer portion in the line as a string
    */
   public static String getAnswer(String txtLine, int questionMarkLoc){
      String answer = "";
      try{
         // We assume the answer is followed by a space after the '?' so we add 2 
         // End at the line length since the answer goes all the way to the end
         answer = txtLine.substring(questionMarkLoc + 2, txtLine.length());
      }
      // No answer so (questionMarkLoc + 2) will be out of bounds
      catch (java.lang.StringIndexOutOfBoundsException e) { 
         answer = ""; // later we will use this string value to troubleshoot
      }
      
      // No question. Answer is at the start of line
      if (questionMarkLoc == -1){ 
         answer = txtLine.substring(0, txtLine.length());
      }
      
      System.out.println("answer is !" + answer);
      return answer;
   }

   /**
    * @author Cynthia Lei
    * Determining whether it is necessary to add a placeholder. 
    * Usually that is when there is no question or answer in the line
    * 
    * @param arr either the questions array or the answers array
    * @param arrLength The length of the array
    * @param element the question or answer string that is to be added into the questions or answers array respectively
    */
   public static void troubleshootAndAddElementToArr(ArrayList<String> arrList, String element){
      if (element.equals("")){ // no question or answer present in the line
         // this prevents an array from "lagging behind" due to missing elements
         arrList.add("Placeholder"); 
      }
      else {
         arrList.add(element);
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
      return -1; // no '?' which means no question
   }

   public static void printArrList(ArrayList<String> arrList) {
      for (int i = 0; i < arrList.size(); i++) {
         System.out.print(arrList.get(i) + ",");
      }
      System.out.println();
      System.out.println("The array size is " + arrList.size());
   }

}
