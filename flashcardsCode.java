
/**
 * Date: May 20, 2021
 * Name: Cynthia Lei, Daiphy Lee, Johnny He, Sophia Nguyen
 * Teacher: Mr. Ho
 * Description: Flashcards Final Project
 */
 
//import classes
import java.util.Scanner; // scanner
// File imports
import java.io.IOException;
import java.io.*;
import java.io.File;
// CSV reader
// Get the included jar file in the github
// In VSCode, Explorer > JAVA PROJECTS > Referenced Libraries > Add library (the jar file)
import com.opencsv.CSVReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

 class flashcardsCodet {
    public static void main(String[] args) {

      // delete this later it's just for testing
      String path = "/Users/daiphylee/11 ComSci Java Lessons/A. Assignments/flashcardsFinal/";
      String name = "tester.csv";
      String fullroute = path+name;
      
      // initialize the arrays for the questions and answers
      String[] questionsArr = new String[totalLinesInFile(fullroute)];
      String[] answersArr = new String[totalLinesInFile(fullroute)];

      // initialize scanner
      Scanner reader = new Scanner(System.in);
      String userInput, enterCSVfile, enterTXTFile, manuallyEnter, exitCondition;
      enterCSVfile = "1";
      enterTXTFile = "2";
      manuallyEnter = "3";
      exitCondition = "4";

      // allows user to be able to continue using the different ways to create flashcards until they choose to quit
      do{
         printMenu();                                    // Printing out the main menu
         userInput = reader.nextLine();                  // User selection from the menu

         if (userInput.equals(enterCSVfile)){
            readCSVFile(fullroute, questionsArr, answersArr);
         }
         else if (userInput.equals(enterTXTFile)) {
      
         }
         else if (userInput.equals(manuallyEnter)){

         }
         else{
            System.out.println("Please type in a valid option (A number from 1-4)");
         }

      }while (!userInput.equals(exitCondition));         // Exits once the user types 
        
      reader.close();   // close reader
      System.out.println("Program Terminated");


   }
   /**
    * @author Daiphy Lee
    * Prints the menu options for the user to choose whichever method they prefer to create their flashcards
    */
   public static void printMenu(){
      System.out.println("FlashCard Generator System\n"
        .concat("1. Enter CSV File\n")
        .concat("2. Enter TXT File\n")
        .concat("3. Manually enter information\n")
        .concat("4. Quit\n")
        .concat("Enter menu option (1-4)\n")
        );
   }
   /**
    * @author Daiphy Lee
    * Reads CSV file using CSVReader. Automatically splits the information using it's delimeter. 
    * Differentiates the questions from the answers and puts them into its own array.
    *
    * @param route   the combined file path and file name to find the data
    * @param questionArr   the array for the questions
    * @param answerArr  the array for the answers
    */
   public static void readCSVFile(String route, String[] questionArr, String[] answerArr){
      
      // initialize lineArr to read every line
      String[] linesArr = new String[totalLinesInFile(route)];

      try{

         // reads the CSV file
         CSVReader reader = new CSVReader(new FileReader(route));

            // conditions while there is still data on the next line
            while ((linesArr = reader.readNext()) != null) {
               // the answer array
               answerArr[0] = linesArr[findAnswers(totalLinesInFile(route))];
               // the question array
               questionArr[0] = linesArr[findQuestion(totalLinesInFile(route))];
               
               //DELETE THIS LATER -> IT IS JUST TO TEST AND ENSURE IT WORKS
               System.out.print("Question: ");
               printArr(questionArr);
               System.out.print("Answer: ");
               printArr(answerArr);
            }

         reader.close();   // closes CSVReader
      }
      // catch when file is not found or when there is only 1 question/answer
      catch(Exception e){
         System.out.println("Error occured. Please reinput.");
         
     }
   }

   //cynthias method 
   public static int totalLinesInFile(String fullfilePath) {
      int totalLines = 0;
      try {
         String line = "";
         File file = new File(fullfilePath);
         BufferedReader br = new BufferedReader(new FileReader(file));
         while ((line = br.readLine()) != null) {
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

   /**
    * @author Daiphy Lee
    * Uses Modulus to determine the even lines which are the lines containing the questions
    *
    * @param totalLines the total number of lines in the CSVFile
    * @return the even line number until there are no more lines with data; if there are no even lines it'll return -1
    */
   public static int findQuestion(int totalLines){
      for (int i = 0; i < totalLines; i++){
         // search for every even line
         if (i % 2 == 0) {
            return i;
         }
      }
      return -1;
   }

   /**
    * @author Daiphy Lee
    * Uses Modulus to determine the odd numbered lines which are the lines containing the questions
    *
    * @param totalLines the total number of lines in the CSVFile
    * @return the odd line number until there are no more lines with data; if there are no odd lines it'll return -1
    */
   public static int findAnswers(int totalLines){
      for (int i = 0; i < totalLines; i++){
         // search for every odd line
         if (i % 2 != 0) {
            return i;
         }   
      }
      return -1;
   }
  
   // cynthias method
   public static void printArr(String[] arr) {
      for (int i = 0; i < 1; i++) {
         System.out.print(arr[i] + ",");
      }
      System.out.println();
      // System.out.println("The array size is " + arr.length);
   }

 }
