
/**
 * Date: May 20, 2021
 * Name: Cynthia Lei, Daiphy Lee, Johnny He, Sophia Nguyen
 * Teacher: Mr. Ho
 * Description: Flashcards Final Project
 */
 
//import classes
import java.util.Scanner; // scanner
// File imports
import java.util.ArrayList;
import java.io.IOException;
import java.io.*;
import java.io.File;
// Javafx packages
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// CSV reader
// Get the included jar file in the github
// In VSCode, Explorer > JAVA PROJECTS > Referenced Libraries > Add library (the jar file)
import com.opencsv.CSVReader;

public class flashcardsCode extends Application {

Scene scene1, scene2, scene3;
   // start method will become the new "main" method, so all the codes is able to work together    
   @Override
   public void start(Stage primaryStage) {

      // initialize scanner
      Scanner reader = new Scanner(System.in);
      String filePath = "";
      
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
           System.out.println("CSV Input");
          
           filePath = getUserFilePath(reader); // asks user for file path
           
            // initialize the arrays for the questions and answers
            String[] questionsArr = new String[totalLinesInFile(filePath)];
            String[] answersArr = new String[totalLinesInFile(filePath)];
           
            readCSVFile(filePath, questionsArr, answersArr);
         }
         else if (userInput.equals(enterTXTFile)) {
            System.out.println("txt Input");

            filePath = getUserFilePath(reader);

            int fileLineLength = totalLinesInFile(filePath);
            String[] questionsArr = new String[fileLineLength];
            String[] answersArr = new String[fileLineLength];

            readTxtFile(filePath, questionsArr, answersArr); // reading txt file
         }
         else if (userInput.equals(manuallyEnter)){
            System.out.println("terminal Input");

            // Needs an array list because I cannot properly predict when the user wants to quit
            // Could ask them for the amount of questions and answers beforehand but that is not really flexible to the user
            ArrayList<String> userQuestions = new ArrayList<>();
            ArrayList<String> userAnswers = new ArrayList<>();
            // Determining whether or not user wants to quit
            Boolean quit = false;
            do{
                quit = checkForQuit(reader, userQuestions, userAnswers);
            }while(quit == false);
            // Final questions and answers
            // Converting from an arraylist to an array for integration
            String[] questions = userQuestions.toArray(new String[lengthOfArrayLists(userQuestions)]);
            String[] answers = userAnswers.toArray(new String[lengthOfArrayLists(userAnswers)]);
            printArr(questions);
            printArr(answers);
           
         }
         else{
            System.out.println("Please type in a valid option (A number from 1-4)");
         }

      }while (!userInput.equals(exitCondition));         // Exits once the user types 
        
      reader.close();   // close reader
      System.out.println("Program Terminated");


      // GUI basic template
      primaryStage.setTitle("Flash card GUI");

        // Scene 1
        Label label1 = new Label("Question #1");
        Button button1 = new Button("Next");
        button1.setOnAction(e -> primaryStage.setScene(scene2));   
        // Setting the location of the button
        button1.setTranslateX(200);
        button1.setTranslateY(150);
        // Scene size modification
        VBox layout1 = new VBox(20);     
        layout1.getChildren().addAll(label1, button1);
        scene1= new Scene(layout1, 300, 250);

        // Scene 2
        Label label2 = new Label("Answer #1");
        Button button2 = new Button("Next");
        button2.setOnAction(e -> primaryStage.setScene(scene3));
        Button button4 = new Button("Back"); 
        button4.setOnAction(e -> primaryStage.setScene(scene1));  
        // Setting the location of the button
        button2.setTranslateX(200);
        button2.setTranslateY(150);
        button4.setTranslateX(75);
        button4.setTranslateY(105);
        // Scene size modification
        VBox layout2= new VBox(20);
        layout2.getChildren().addAll(label2, button2, button4);
        scene2= new Scene(layout2,300,250);
        
        // Scene 3
        Label label3 = new Label("Question #2");
        Button button3 = new Button("Back");
        button3.setOnAction(e -> primaryStage.setScene(scene2));     
        // Setting the location of the button
        button3.setTranslateX(200);
        button3.setTranslateY(150);
        // Scene size modification
        VBox layout3 = new VBox(20);     
        layout3.getChildren().addAll(label3, button3);
        scene3= new Scene(layout3, 300, 250);
                    
        primaryStage.setScene(scene1);
        primaryStage.show();
   }


    public static void main(String[] args) {
    launch(args);
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
    * @author Cynthia Lei
    * prompt user for file path
    * @param reader scanner for user input
    * @return user file path for either .txt or .csv file
    */
   public static String getUserFilePath(Scanner reader){
      System.out.println("Enter FULL file path");
      String userFilePath = reader.nextLine();
      return userFilePath;
   }

   /**
    * @author Cynthia Lei Determine the total number of lines in the given file.
    * This is so we can initialize arrays the size of the total file lines
    * 
    * @param fullFilePath user inputted file's path to access the file
    * @return the number of lines in the file
    */
   public static int totalLinesInFile(String fullFilePath) {
      int totalLines = 0;
      try {
         String line = "";
         File file = new File(fullFilePath);
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
   
    /**
    * @author Cynthia Lei
    * Reads the .txt file by identifying the question and answer portion of each line.
    * Then it will sort the question and answer strings into their respective arrays 
    * 
    * @param fullFilePath user inputted .txt file path
    * @param questionsArray array that contains all the question strings
    * @param answersArray array that contains all the answer strings
    */
   public static void readTxtFile(String fullFilePath, String[] questionsArray, String[] answersArray) {
      String txtLine = " ";
      int questionMarkIndex = 0;
      String realQuestion = "";
      String realAnswer = "";
      int lineNum = 0; // tells us the index of the element to populate for the question and answer arrays

      try {
         File file = new File(fullFilePath);
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
            troubleshootAndAddElementToArr(questionsArray, lineNum, realQuestion);
            
            // Adding the answer portion into the answers array
            realAnswer = getAnswer(txtLine, questionMarkIndex);
            troubleshootAndAddElementToArr(answersArray, lineNum, realAnswer);
            
            System.out.println();
            lineNum++; // next line, next element

         }
         System.out.println("QUESTIONS arr: ");
         printArr(questionsArray);
         System.out.println("ANSWERS arr: ");
         printArr(answersArray);
         System.out.println("Total line count: " + totalLinesInFile(fullFilePath));

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
      return -1; // no '?' which means no question
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
   public static void troubleshootAndAddElementToArr(String[] arr, int arrLength, String element){
      if (element.equals("")){ // no question or answer present in the line
         // this prevents an array from "lagging behind" due to missing elements
         arr[arrLength] = "Placeholder"; 
      }
      else {
         arr[arrLength] = element;
      }
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
   
   // iterate and print the array elements
   public static void printArr(String[] arr) {
      for (int i = 0; i < arr.length; i++) {
         System.out.print(arr[i] + ",");
      }
      System.out.println();
   }
   /**
    * @author Sophia Nguyen
    * Getting the length of an arraylist
    *
    * @param al the array list being passed through
    * @return the length of the arraylist to later initialize arrays
    */
    public static int lengthOfArrayLists (ArrayList<String> al){
        int length = al.size();
        return length;
    }

   /**
    * @author Sophia Nguyen
    *
    * To collect the questions
    * @param reader
    * @return the string containing the question
    */
   public static String fileQuestions(Scanner reader){
      System.out.println("What is your question? To break, type an @");
      String question = reader.nextLine();
      return question;
   }

   /**
    * @author Sophia Nguyen
    *
    * To collect the answers
    * @param reader
    * @return the string containing the answer
    */
   public static String fileAnswers(Scanner reader){
      System.out.println("What is the answer to that question? To break, type an @ but beware doing this may delete your previous question");
      String answer = reader.nextLine();
      return answer;
   }
   
   /**
    * @author Sophia Nguyen
    *
    * To determine whether or not user wants to quit by searching for an "@"" symbol
    * @param reader
    * @param inquiry which is the arraylist carrying all of the questions
    * @param reply which is the arraylist carrying all of the answer
    * @return whether or not the user wants to quit
    */
   public static Boolean checkForQuit(Scanner reader, ArrayList<String> inquiry, ArrayList<String> reply){
      String question = fileQuestions(reader);
      // if question has the @ symbol
      if(question.contains("@")){
         return true;
      }
      String answer = fileAnswers(reader);
      // if answers has the @ symbol 
      if(answer.contains("@")){
         return true;
      }
      else{
         // Adds the questions and answers to the arraylists
         inquiry.add(question);
         reply.add(answer);
      }
      return false;

   }
  

 }
