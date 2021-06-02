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

   private Desktop desktop = Desktop.getDesktop();

   Scene scene0, flashcardsScene, scene2, scene3, sceneFile, sceneInputText;
   
   
   // start method will become the new "main" method, so all the codes is able to work together    
   // public ArrayList<String> questionsArr = new ArrayList<String>(); // Create an ArrayList object
   // public ArrayList<String> answersArr = new ArrayList<String>(); // Create an ArrayList object

   @Override
   public void start(final Stage primaryStage) {
      int[] arrIndex = {0};
      // initialize scanner
      Scanner reader = new Scanner(System.in);
      String filePath = "";
      
      String userInput, enterCSVfile, enterTXTFile, manuallyEnter, exitCondition;
      enterCSVfile = "1";
      enterTXTFile = "2";
      manuallyEnter = "3";
      exitCondition = "4";
      
      
      // allows user to be able to continue using the different ways to create flashcards until they choose to quit
      
      ArrayList<String> questionsArr = new ArrayList<String>(); // Create an ArrayList object
      ArrayList<String> answersArr = new ArrayList<String>(); // Create an ArrayList object

      do{
         printMenu();                                    // Printing out the main menu
         userInput = reader.nextLine();                  // User selection from the menu

         if (userInput.equals(enterCSVfile)){
         //   System.out.println("CSV Input");
          
         //   filePath = getUserFilePath(reader); // asks user for file path
           
         //    // initialize the arrays for the questions and answers
         //    String[] questionsArr = new String[totalLinesInFile(filePath)];
         //    String[] answersArr = new String[totalLinesInFile(filePath)];
           
         //    readCSVFile(filePath, questionsArr, answersArr);
         }
         else if (userInput.equals(enterTXTFile)) {
            System.out.println("txt Input");

            filePath = getUserFilePath(reader);

            int fileLineLength = totalLinesInFile(filePath);


            // String[] questionssss = questionsArr.toArray(new String[lengthOfArrayLists(questionsArr)]);
            // String[] answerssss = answersArr.toArray(new String[lengthOfArrayLists(answersArr)]);
            // String[] questionsArr = new String[fileLineLength];
            // String[] answersArr = new String[fileLineLength];

            readTxtFile(filePath, questionsArr, answersArr); // reading txt file
            // System.out.println("CHECKPOTIN FIRSTTTT- ---- - - -------------------------------------------------");
            // printArr(questionsArr);
            // printArr(answersArr);
            // System.out.println("CHECKPOTIN end ------------------------------------------------------------------------");

         }
         else if (userInput.equals(manuallyEnter)){
            // System.out.println("terminal Input");

            // // Needs an array list because I cannot properly predict when the user wants to quit
            // // Could ask them for the amount of questions and answers beforehand but that is not really flexible to the user
            // ArrayList<String> userQuestions = new ArrayList<>();
            // ArrayList<String> userAnswers = new ArrayList<>();
            // // Determining whether or not user wants to quit
            // Boolean quit = false;
            // do{
            //     quit = checkForQuit(reader, userQuestions, userAnswers);
            // }while(quit == false);
            // // Final questions and answers
            // // Converting from an arraylist to an array for integration
            // String[] questions = userQuestions.toArray(new String[lengthOfArrayLists(userQuestions)]);
            // String[] answers = userAnswers.toArray(new String[lengthOfArrayLists(userAnswers)]);
            // printArr(questions);
            // printArr(answers);
            
         }
         else{
            System.out.println("Please type in a valid option (A number from 1-4)");
         }
         
         
      }while (!userInput.equals(exitCondition));         // Exits once the user types
      // System.out.println("\nCHECKPOTIN AFTER DO WHILE +++++++++++++++++++++++++++++++++++++++++++++");
      // printArr(questionsArr);
      // printArr(answersArr);
      // System.out.println("CHECKPOTIN end +++++++++++++++++++++++++++++++++++++++++++++");

      reader.close();   // close reader
      System.out.println("Terminal Terminated");
      
      // String[] questionssss = {"What name?", "How you?", "Knni?"};
      // String[] answerssss = {"HoHo", "Ungood", "Chiwa"};
      
      System.out.println("GUI");
      // GUI basic template
      primaryStage.setTitle("Flash card GUI");
      
      // Scene 0
      int width = 300;
      int height = 250;
      int centreX = width/2;
      int centreY = height/2;

      /*
      // Label label0 = new Label("MENU HO");
      // Button inputButton1 = new Button("txt input");
      // inputButton1.setOnAction(e -> primaryStage.setScene(scene1));   
      // // Setting the location of the button
      // inputButton1.setTranslateX(centreX);
      // inputButton1.setTranslateY(centreY + 50);
      // // Scene size modification
      // VBox layout0 = new VBox(20);     
      // layout0.getChildren().addAll(label0, inputButton1);
      // scene0 = new Scene(layout0, width, height);
      
      // // scene txt input
      // FileChooser fileChooser = new FileChooser();
      
      // Button openButton = new Button("Open File (.txt or .csv)");
      
      
      // openButton.setOnAction(
      //    new EventHandler<ActionEvent>() {
      //       @Override
      //       public void handle(final ActionEvent e) {
      //          File file = fileChooser.showOpenDialog(primaryStage);
      //          String fileName = file.getName();
      //          String filePath = file.getAbsolutePath();
      //          if (file != null) {
      //             if ((fileName.substring(fileName.length() - 4, fileName.length())).equals(".txt")) {
      //                openFile(file);
      //                int fileLineLength = totalLinesInFile(filePath);
      //                // String[] questionsArr = new String[fileLineLength];
      //                // String[] answersArr = new String[fileLineLength];
                     
      //                readTxtFile(filePath, questionsArr, answersArr); // reading txt file
                     
      //                primaryStage.setScene(scene1);
      //             }
      //             else if ((fileName.substring(fileName.length() - 4, fileName.length())).equals(".csv")) {
      //                openFile(file);
      //                primaryStage.setScene(scene1);
      //             }
                  
      //          }
      //       }
      //    }); 

      //    openButton.setTranslateX(0);
      //    openButton.setTranslateY(centreY);
      //    // Scene size modification
         
      //    Button terminalInputButton = new Button("Input questions/answers");
      //    terminalInputButton.setOnAction(e -> primaryStage.setScene(sceneInputText));
         
      //    terminalInputButton.setTranslateX(0);
      //    terminalInputButton.setTranslateY(centreY);
      //    // Scene size modification
      //    VBox menuLayout = new VBox(20);     
      //    menuLayout.getChildren().addAll(openButton, terminalInputButton);
      //    sceneFile = new Scene(menuLayout, width, height);
         
      //    //sceneInputText
      //    //Creating a GridPane container
      //    GridPane grid = new GridPane();
      //    grid.setPadding(new Insets(10, 10, 10, 10));
      //    grid.setVgap(5);
      //    grid.setHgap(5);
      //    //Defining the Name text field
      //    TextField question = new TextField();
      //    question.setPromptText("Enter question.");
      //    question.setPrefColumnCount(10);
      // question.getText();
      // GridPane.setConstraints(question, 0, 0);
      // grid.getChildren().add(question);
      
      // //Defining the Last Name text field
      // TextField answer = new TextField();
      // answer.setPromptText("Enter answer.");
      // GridPane.setConstraints(answer, 0, 1);
      // grid.getChildren().add(answer);
      
      // //Defining the Submit button
      // Button submit = new Button("Submit");
      // GridPane.setConstraints(submit, 1, 0);
      // grid.getChildren().add(submit);
      
      // Text text = new Text("");
      // submit.setOnAction(action -> {
      //    String questionString = question.getText();
      //    String answerString = answer.getText();
      //    System.out.println(questionString); 
      //    System.out.println(answerString);
      //    text.setText("Hello Welcome to Tutorialspoint. From now, we will communicate with you at ");
      // });
      
      // // TextField textField = new TextField();
      // // Button getTextButton = new Button("Click to get text");
      // // getTextButton.setOnAction(action -> {System.out.println(textField.getText());});
      // // getTextButton.setTranslateX(0);
      // // getTextButton.setTranslateY(centreY);
      // VBox layout100 = new VBox(20);
      // layout100.getChildren().addAll(question, answer, submit, text);
      // sceneInputText = new Scene(layout100, width, height);
      
      // HBox hbox = new HBox(textField, getTextButton);
      // sceneInputText = new Scene(hbox, width, height);
      // /Users/cynthia/Documents/CompSci11/summatives/flashcardsFinal/test.txt
      
      // Scene 1
      // System.out.println("CHECKPOTIN");
      // printArr(questionsArr);
      // printArr(answersArr);
      // System.out.println("CHECKPOTIN end");
      */

      try{
         // call on the method to form the flashcards GUI page
         flashcardsScene = showFlashcardsGUI(questionsArr, answersArr, arrIndex);
         primaryStage.setScene(flashcardsScene);
         primaryStage.show();
      }
      catch(Exception e){
         System.out.println("No input");
      }
   }

    public static void main(String[] args) {
      launch(args);
    }  

    /**
     * PS IDK IF WE DO DOCSTRINGS THIS FOR NONSTATIC METHODS
     * @author Cynthia Lei
     * Responsible for creating the method itself, assigning the parameters
     * @author Johnny He
     * Responsible for creating the content/code inside the method
     * JOHNNY ADD COMMENTS AND/OR FORMATTING FOR UR CODE ERASE THIS COMMENT ALSO
     * 
     * @param questionsArrList
     * @param answersArrList
     * @param arrIndex
     * @return the flashcards scene with all the necessary components (questions & answers, buttons etc.)
     */
    public Scene showFlashcardsGUI(ArrayList<String> questionsArrList, ArrayList<String> answersArrList, int[] arrIndex){
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
            System.out.println("NOOO U CANT GO NEXT");
            warningText.setText("NOOOO STOP NEXT");
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
               System.out.println("NOOO U CANT GO BACK");
               warningText.setText("NOOO U CANT GO BACK");
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
         flashcardsScene= new Scene(layout1, 700, 350);
         return flashcardsScene; // since we're returning a global variable, this is a nonstatic method
    }

    private void openFile(File file) {
       try {
          desktop.open(file);
       } 
       catch (IOException ex) {
          Logger.getLogger(flashcardsCode.class.getName()).log(Level.SEVERE, null, ex);
       }
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
        .concat("4. Quit & See flashcards\n")
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
               // printArr(questionArr);
               System.out.print("Answer: ");
               // printArr(answerArr);
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
   public static void readTxtFile(String fullFilePath, ArrayList<String> questionsArray, ArrayList<String> answersArray) {
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
         System.out.println("QUESTIONS arr ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++: ");
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
   public static void printArr(ArrayList<String> arr) {
      for (int i = 0; i < arr.size(); i++) {
         System.out.print(arr.get(i) + ",");
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
