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

import java.awt.Desktop;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// CSV reader
// Get the included jar file in the github
// In VSCode, Explorer > JAVA PROJECTS > Referenced Libraries > Add library (the jar file)
import com.opencsv.CSVReader;

public class flashcardsCode extends Application {
   // Global variables
   Scene flashcardsScene, sceneInputText, menuScene, instructionScene;
   
   // start method will become the new "main" method, so all the codes is able to work together    
   @Override
   public void start(final Stage primaryStage) {
      // arrIndex keeps track of which question/answer is being displayed in flashcards GUI
      // We use array instead of int datatype because this variable will be
      // used inside an arrow function and integer values are not changed inside
      // these functions so we must use array instead
      int[] arrIndex = {0}; 
      
      // Initialize arraylists
      ArrayList<String> questionsArr = new ArrayList<String>(); 
      ArrayList<String> answersArr = new ArrayList<String>(); 

      System.out.println("Terminal Terminated. GUI");

      // GUI
      primaryStage.setTitle("Flash card GUI"); // GUI Window Title
      
      // All scenes' resolution
      int width = 700;
      int height = 350;
      int centreX = width/2; // DELETE IF YALL AINT USING IT
      int centreY = height/2;

      try{
         // assign scenes with its display components
         // flashcardsScene = showFlashcardsGUI(questionsArr, answersArr, arrIndex, width, height);
         menuScene = showMenuGUI(primaryStage, width, height, questionsArr, answersArr, arrIndex);
                  
         primaryStage.setScene(menuScene); // select which scene to display
         primaryStage.show(); // display the selected scene
      }
      catch(Exception e){
         System.out.println("No input");
      }
   }

    public static void main(String[] args) {
      launch(args);
    }  

    /**
     * @author Cynthia Lei
     * Responsible for coding the components of the menu
     * @author Johnny He 
     * Responsible for styling the menu // JOHNNY I WILL ERASE YO NAME IF U DONT ADD STUFF :'(
     * Displays the menu GUI
     * 
     * @param primaryStage The "base" of this GUI. It hosts all the scenes.
     * @param width the width of the menu GUI scene
     * @param height the width of the menu GUI scene
     * @param questionsArrList arraylist that contains all the questions
     * @param answersArrList arraylist that contains all the answers that correspond to the questions in questionsArrList
     * @param arrIndex this tracks which question number and answer the flashcards GUI should display
     * @return the menu scene with all the necessary components (buttons etc.)
     */
    public Scene showMenuGUI(Stage primaryStage, int width, int height, ArrayList<String> questionsArrList, ArrayList<String> answersArrList, int[] arrIndex){
      Text warningText = new Text("");
      // int centreY = height/2; // placement
      // JOHNNY I SUGGEST CONSIDERING CENTERING THE MENU MAYBE IDK

      Label menuTitleLabel = new Label("MENU"); // menu title
      menuTitleLabel.setFont(new Font("Arial", 50)); // label font style and size JOHNNY
      
      /* Initialize buttons ------------------------------ */
      // Button for inputting files
      Button instructionsbutton = new Button("Instructions");
      instructionsbutton.setOnAction(action ->{
         instructionScene = showInstructions(primaryStage, width, height, questionsArrList, answersArrList, arrIndex); 
         primaryStage.setScene(instructionScene);          
      });
      Button openFileButton = new Button("Open File (.txt or .csv)");
      openFileButton.setOnAction(
            new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent e) {
                  try {
                     // if the user does not input .txt or .csv, the warning text will be shown
                     warningText.setText(checkInputtedFileGUI(primaryStage, questionsArrList, answersArrList, arrIndex, width, height));
                  }
                  catch(Exception error){
                     System.out.println("Invalid, action terminated.");
                  }
               }
            });
      // JOHNNY U CAN MAKE THE BUTTONS BIGGER OR EVEN ADD IMAGE TO THE BUTTONS
      // openFileButton.setTranslateX(0); 
      // openFileButton.setTranslateY(centreY);

      // Button for manualling inputting questions and answers
      Button manualInputButton = new Button("Input questions/answers");
      // SOPHIA ADD YO BUTTON ACTION EVENT HEREE
      // manualInputButton.setTranslateX(0);
      // manualInputButton.setTranslateY(centreY);

      /* Scene graphical display ------------------------------ */
      // Vbox displays components vertically
      VBox menuLayout = new VBox(menuTitleLabel, instructionsbutton, openFileButton, manualInputButton, warningText); 
      menuLayout.setSpacing(10); // Vertical distance between each component JOHNNY
      
      // Adding components into the scene
      // menuLayout.getChildren().addAll(menuLayout);
      menuScene = new Scene(menuLayout, width, height);
      
      return menuScene;

    }

   /**
    * @author Daiphy Lee 
    * Shows the instructions when the user presses the 'Instructions' button. Allows user to return to the Menu scene to choose one of the options to create the flash cards
    *
    * @param primaryStage The "base" of this GUI. It hosts all the scenes.
    * @param width the width of the menu GUI scene
    * @param height the width of the menu GUI scene
    * @param questionsArrList arraylist that contains all the questions
    * @param answersArrList arraylist that contains all the answers that correspond to the questions in questionsArrList
    * @param arrIndex this tracks which question number and answer the flashcards GUI should display
    * @return the instructions scene with all the necessay components
    */
   public Scene showInstructions(Stage primaryStage, int width, int height, ArrayList<String> questionsArrList, ArrayList<String> answersArrList, int[] arrIndex){
      
      Label instructionsLabel = new Label("INSTRUCTIONS"); // instructions title
      instructionsLabel.setFont(new Font("Arial", 50)); // label font style and size JOHNNY
      
      // the instructions text
      Text instruct = new Text("Welcome. You have 3 different options to create your very own personalized flashcards! \nOption 1 and 2 is to enter a .txt or .csv file with your questions and answers. \nThis file may have been already created from your notes in advanced. \nOption 3 allows you to manually enter your questions and answers. \nPlease ensure that you enter all of your q&as before clicking Finished Inputting ! \nGood Luck and have fun studying!");
   
      Button back = new Button("Back to Menu"); // the button to return to the menu scene
      back.setOnAction(action ->{
         primaryStage.setScene(showMenuGUI(primaryStage, width, height, questionsArrList, answersArrList, arrIndex));       
      });   // calls on action

      VBox instructiosLayout = new VBox(instructionsLabel, instruct, back); 
      instructiosLayout.setSpacing(10); // Vertical distance between each component JOHNNY
      
      // Adding components into the scene
      instructionScene = new Scene(instructiosLayout, width, height);

      return instructionScene;
    }

    /**
     * @author Cynthia Lei
     * Checks whether the inputted file is .txt or .csv
     * 
     * @param primaryStage The "base" of this GUI. It hosts all the scenes.
     * @param questionsArrList arraylist that contains all the questions
     * @param answersArrList arraylist that contains all the answers that correspond to the questions in questionsArrList
     * @param arrIndex this tracks which question number and answer the flashcards GUI should display
     * @param width the width of the menu GUI scene
     * @param height the width of the menu GUI scene
     * @return warning string if the user does not input .txt or .csv file
     */
   public String checkInputtedFileGUI(Stage primaryStage, ArrayList<String> questionsArrList, ArrayList<String> answersArrList, int[] arrIndex, int width, int height) {
      FileChooser fileChooser = new FileChooser(); // allow user to input file
      File file = fileChooser.showOpenDialog(primaryStage);
      String fileName = file.getName();
      String filePath = file.getAbsolutePath();

      String warningText = " "; 
      
      if (file != null) {
         // If inputted file is .txt file
         if ((fileName.substring(fileName.length() - 4, fileName.length())).equals(".txt")) {
            // openFile(file);
            // reading txt file by adding elements to questions and answers arraylist
            readTxtFile(filePath, questionsArrList, answersArrList); 

            // Set flashcards scene now that the components (arraylists) are set
            flashcardsScene = showFlashcardsGUI(questionsArrList,answersArrList,arrIndex, width, height);
            primaryStage.setScene(flashcardsScene);
         }
         // If inputted file is .csv file
         else if ((fileName.substring(fileName.length() - 4, fileName.length())).equals(".csv")) {
            // openFile(file);
            // reading csv file by adding elements to questions and answers arraylist
            readCSVFile(filePath, questionsArrList, answersArrList);

            // Set flashcards scene now that the components (arraylists) are set
            flashcardsScene = showFlashcardsGUI(questionsArrList, answersArrList, arrIndex, width, height);
            primaryStage.setScene(flashcardsScene);
         }
         // Forces user to input either .txt or .csv in order to go to flashcards GUI
         else {
            System.out.println("Please enter either a .txt or .csv file.");
            warningText = "Please enter either a .txt or .csv file.";

         }     
      }
      return warningText;
   }

    /**
     * @author Cynthia Lei
     * Responsible for creating the method itself, assigning the parameters
     * @author Johnny He
     * Responsible for creating the content/code inside the method
     * JOHNNY ADD COMMENTS AND/OR FORMATTING FOR UR CODE ERASE THIS COMMENT ALSO
     * This method displays the flashcards GUI
     * 
     * @param questionsArrList arraylist that contains all the questions
     * @param answersArrList arraylist that contains all the answers that correspond to the questions in questionsArrList
     * @param arrIndex this tracks which question number and answer the flashcards GUI should display
     * @param width the width of the flashcards GUI scene
     * @param height the width of the flashcards GUI scene
     * @return the flashcards scene with all the necessary components (questions & answers, buttons etc.)
     */
    public Scene showFlashcardsGUI(ArrayList<String> questionsArrList, ArrayList<String> answersArrList, int[] arrIndex, int width, int height){
      Label questionLabel = new Label(questionsArrList.get(arrIndex[0]));  
      Label answerLabel = new Label("");  
      
      Button showAns = new Button("Show Answer");
      showAns.setTranslateX(500);
      showAns.setTranslateY(50);
      showAns.setOnAction(action -> {
         answerLabel.setText(answersArrList.get(arrIndex[0]));
      });

      Button clearAns = new Button("Clear Answer");
      clearAns.setTranslateX(100);
      clearAns.setTranslateY(0);
      clearAns.setOnAction(action -> {
         answerLabel.setText("");
      });

      Text warningText = new Text("");
      Button nextButton = new Button("Next");
      nextButton.setOnAction(action -> {
         if (arrIndex[0] == questionsArrList.size() - 1){
            System.out.println("This is the last question. You cannot go next.");
            warningText.setText("This is the last question. You cannot go next.");
         }
         else {
            arrIndex[0]++;
            questionLabel.setText(questionsArrList.get(arrIndex[0]));
            answerLabel.setText("");
            warningText.setText("");
         }
      });

      Button backButton = new Button("Back");
         backButton.setOnAction(action -> {
            if (arrIndex[0] == 0){
               System.out.println("This is the first question. You cannot go back.");
               warningText.setText("This is the first question. You cannot go back.");
            }
            else {
               arrIndex[0]--;
               questionLabel.setText(questionsArrList.get(arrIndex[0]));
               answerLabel.setText("");
               warningText.setText("");
            }
         });
         // Setting the location of the button
         backButton.setTranslateX(100);
         backButton.setTranslateY(0);

         // Setting the location of the button
         nextButton.setTranslateX(500);
         nextButton.setTranslateY(50);
         // Scene size modification 
         VBox layout1 = new VBox(20);
         layout1.getChildren().addAll(questionLabel, nextButton, backButton, answerLabel, showAns, clearAns, warningText);
         flashcardsScene= new Scene(layout1, width, height);
         return flashcardsScene; // since we're returning a global variable, this is a nonstatic method
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
    * @author Cynthia Lei
    * Reads the .txt file by identifying the question and answer portion of each line.
    * Then it will sort the question and answer strings into their respective arrays 
    * 
    * @param fullFilePath user inputted .txt file path
    * @param questionsArray array that contains all the question strings
    * @param answersArray array that contains all the answer strings
    */
   public static void readTxtFile(String fullFilePath, ArrayList<String> questionsArrList, ArrayList<String> answersArrList) {
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

            // Dividing the line into 2 parts: question and answer
            questionMarkIndex = findQuestionMarkTxt(txtLine); 
            
            // Adding the question portion into the question array
            realQuestion = getQuestionTxt(txtLine, questionMarkIndex);
            troubleshootAndAddElementToArr(questionsArrList, lineNum, realQuestion);
            
            // Adding the answer portion into the answers array
            realAnswer = getAnswerTxt(txtLine, questionMarkIndex);
            troubleshootAndAddElementToArr(answersArrList, lineNum, realAnswer);
            
            lineNum++; // next line, next element

         }
         System.out.println(".txt FILE SUCCESSFULLY READ");

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
    * Stores the question as a string from .txt file
    *
    * @param txtLine this line from the .txt will be read
    * @param questionMarkLoc the index of the question mark in the line
    * @return the question portion in the line as a string
    */
   public static String getQuestionTxt(String txtLine, int questionMarkLoc){
      // The question starts from the beginning of each line to the '?'
      // the questionMarkLoc goes up to but does not include the '?'
      // so we need to add 1 to include the '?' in the question string
      
      // if there is no question, questionMarkLoc + 1 = 0 so the question would be ""
      // that is important for troubleshooting (whether a placeholder is necessary)
      String question = txtLine.substring(0, questionMarkLoc + 1);
      
      return question;
   }

   /**
    * @author Cynthia Lei
    * Stores the answer as a string from .txt file
    * 
    * @param txtLine this line from the .txt will be read
    * @param questionMarkLoc the index of the question mark in the line
    * @return the answer portion in the line as a string
    */
   public static String getAnswerTxt(String txtLine, int questionMarkLoc){
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
   public static void troubleshootAndAddElementToArr(ArrayList<String> arr, int arrLength, String element){
      if (element.equals("")){ // no question or answer present in the line
         // this prevents an array from "lagging behind" due to missing elements
         // arr[arrLength] = "Placeholder"; 
         arr.add("Placeholder");
      }
      else {
         // arr[arrLength] = element;
         arr.add(element);
      }
   }

    /**
    * @author Daiphy Lee
    * Reads CSV file using CSVReader. Automatically splits the information using it's delimeter. 
    * Differentiates the questions from the answers and puts them into its own array.
    *
    * @param route   the combined file path and file name to find the data
    * @param questionsArrList   the arraylist for the questions
    * @param answerArrList  the arraylist for the answers
    */
    public static void readCSVFile(String route, ArrayList<String> questionsArrList, ArrayList<String> answersArrList){
      
      // initialize lineArr to read every line
      String[] linesArr = new String[totalLinesInFile(route)];

      try{

         // reads the CSV file
         CSVReader reader = new CSVReader(new FileReader(route));

         // conditions while there is still data on the next line
         while ((linesArr = reader.readNext()) != null) {
            // the answer array
            answersArrList.add(linesArr[findAnswersCsv(totalLinesInFile(route))]);
            // the question array
            questionsArrList.add(linesArr[findQuestionCsv(totalLinesInFile(route))]);

         }
         System.out.println(".csv FILE SUCCESSFULLY READ");
         
         reader.close();   // closes CSVReader
      }
      // catch when file is not found or when there is only 1 question/answer
      catch(Exception e){
         System.out.println("Error occured. Please reinput.");         
     }
   }

   /**
    * @author Daiphy Lee
    * Uses Modulus to determine the even lines which are the lines containing the questions
    *
    * @param totalLines the total number of lines in the CSVFile
    * @return the even line number until there are no more lines with data; if there are no even lines it'll return -1
    */
   public static int findQuestionCsv(int totalLines){
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
   public static int findAnswersCsv(int totalLines){
      for (int i = 0; i < totalLines; i++){
         // search for every odd line
         if (i % 2 != 0) {
            return i;
         }   
      }
      return -1;
   }
   
   // iterate and print the array elements
   public static void printArr(ArrayList<String> arr) {
      for (int i = 0; i < arr.size(); i++) {
         System.out.print(arr.get(i) + ",");
      }
      System.out.println();
   }

   // HI SOPHIA IM NOT SURE BUT I THINK YOUR METHODS BELOW ARE NOT BEING USED
   
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

//delete this line later im jus having trouble commiting again :( -daiphy