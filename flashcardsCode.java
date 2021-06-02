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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


// CSV reader
// Get the included jar file in the github
// In VSCode, Explorer > JAVA PROJECTS > Referenced Libraries > Add library (the jar file)
import com.opencsv.CSVReader;

public class flashcardsCode extends Application {

   private Desktop desktop = Desktop.getDesktop();

   Scene scene0, scene1, scene2, scene3, sceneFile, sceneInputText;
      
   // start method will become the new "main" method, so all the codes is able to work together    

   @Override
   public void start(final Stage primaryStage) {
      int[] arrIndex = {0};
      // initialize scanner
      Scanner reader = new Scanner(System.in);
      ArrayList<String> questionsArr = new ArrayList<String>(); // Create an ArrayList object
      ArrayList<String> answersArr = new ArrayList<String>(); // Create an ArrayList object

      // GUI basic template
      primaryStage.setTitle("Flash card GUI");

         // Scene 0
         int width = 700;
         int height = 350;
         int centreX = width/2;
         int centreY = height/2;
         
         Label label0 = new Label("MENU HO");
         Button inputButton1 = new Button("txt input");
         inputButton1.setOnAction(e -> primaryStage.setScene(scene1));   
         // Setting the location of the button
         inputButton1.setTranslateX(centreX);
         inputButton1.setTranslateY(centreY + 50);
         // Scene size modification
         VBox layout0 = new VBox(20);     
         layout0.getChildren().addAll(label0, inputButton1);
         scene0 = new Scene(layout0, width, height);
         
         // scene txt input --------------------------------------------------------
         FileChooser fileChooser = new FileChooser();
         Button openButton = new Button("Open File (.txt or .csv)");
         openButton.setTranslateX(200);
         openButton.setTranslateY(100);
         
         openButton.setOnAction(
            new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent e) {
                  try {
                     File file = fileChooser.showOpenDialog(primaryStage);
                     String fileName = file.getName();
                     String filePath = file.getAbsolutePath();
                     if (file != null) {
                        if ((fileName.substring(fileName.length() - 4, fileName.length())).equals(".txt")) {
                           openFile(file);
                           
                           readTxtFile(filePath, questionsArr, answersArr); // reading txt file
   
                           scene1 = showFlash(questionsArr,answersArr,arrIndex, width, height);
                           primaryStage.setScene(scene1);
   
                        }
                        else if ((fileName.substring(fileName.length() - 4, fileName.length())).equals(".csv")) {
                           openFile(file);
   
                           readCSVFile(filePath, questionsArr, answersArr);                        
   
                           scene1 = showFlash(questionsArr,answersArr,arrIndex, width, height);
                           primaryStage.setScene(scene1);
                        }
                        else {
                           System.out.println("please enter .txt or .csv");
                        }     
                     }
                  }
                  catch(Exception error){
                     System.out.println("Invalid, action terminated.");
                  }
               }
            }); 
            openButton.setTranslateX(0);
            openButton.setTranslateY(centreY);
            
            // Scene size modification --------------------------------------------------------
            Button terminalInputButton = new Button("Input questions/answers");
            terminalInputButton.setOnAction(e -> primaryStage.setScene(sceneInputText));
            
            terminalInputButton.setTranslateX(0);
            terminalInputButton.setTranslateY(centreY);
            // Scene size modification
            VBox menuLayout = new VBox(20);     
            menuLayout.getChildren().addAll(openButton, terminalInputButton);
            sceneFile = new Scene(menuLayout, width, height);
            
            //sceneInputText --------------------------------------------------------
            //Creating a GridPane container
            Label questionLabel = new Label("Question");  
            Label answerLabel = new Label("Answer");  
            TextField questionInput = new TextField();  
            TextField answerInput = new TextField();  
            Button b = new Button("Submit"); 
            Button finish = new Button("Finished Inputting");

            Text error = new Text("");
            
            //error.setTranslateX(300);
            
            b.setOnAction(action -> {
               String questionString = questionInput.getText();
               String answerString = answerInput.getText();
               System.out.println(questionString);
               System.out.println(answerString);
                  if(questionString.equals("")){
                     error.setText("Please input a question.");
                     //answerString = answerInput.getText();
                     
                  }
                  else if(answerString.equals("")){
                     error.setText("Please input an answer.");
                     //questionString = questionInput.getText();
                  }
                  else if(!(questionString.equals("")&&(answerString.equals("")))){
                     error.setText("");
                     questionString = questionInput.getText();
                     answerString = answerInput.getText();
                     questionsArr.add(questionString);
                     answersArr.add(answerString);
                     questionInput.setText("");
                     answerInput.setText("");
                  }
   
            });
                  
            finish.setOnAction(action -> {
               try{
                  scene1 = showFlash(questionsArr,answersArr,arrIndex, width, height);
                  primaryStage.setScene(scene1);
               }
               catch(Exception e){
                  primaryStage.setScene(sceneInputText);
                  error.setText("Please hit submit before finish");
               }
            });

            GridPane root = new GridPane();  
            root.addRow(0, questionLabel, questionInput);  
            root.addRow(1, answerLabel, answerInput);  
            root.addRow(2, b, finish, error);  
            //root.addRow(3,"",error);
            sceneInputText = new Scene(root, width, height);
         
         primaryStage.setScene(sceneFile);
         primaryStage.show();
   }

   public Scene showFlash(ArrayList<String> questionsArr, ArrayList<String> answersArr, int[] arrIndex, int width, int height){
         // try{
            Label questionLabel = new Label(questionsArr.get(arrIndex[0]));  
            questionLabel.setTranslateX(300);
            Label answerLabel = new Label(""); 
            answerLabel.setTranslateX(300); 
            
            Button showAns = new Button("Show Answer");
            showAns.setTranslateX(500);
            showAns.setTranslateY(50);
            showAns.setOnAction(action -> {
               answerLabel.setText(answersArr.get(arrIndex[0]));
            });
      
            Button clearAns = new Button("Clear Answer");
            clearAns.setTranslateX(100);
            clearAns.setTranslateY(0);
            clearAns.setOnAction(action -> {
               answerLabel.setText("");
            });
      
            Text warningText = new Text("");
            warningText.setTranslateX(300);
            Button nextButton = new Button("Next");
            nextButton.setOnAction(action -> {
               if (arrIndex[0] == questionsArr.size() - 1){
                  warningText.setText("Unable to move forward.");
               }
               else {
                  arrIndex[0]++;
                  questionLabel.setText(questionsArr.get(arrIndex[0]));
                  answerLabel.setText("");
                  warningText.setText("");
               }
            });
      
            Button backButton = new Button("Back");
            backButton.setOnAction(action -> {
               if (arrIndex[0] == 0){
                  warningText.setText("Unable to move back.");
               }
               else {
                  arrIndex[0]--;
                  questionLabel.setText(questionsArr.get(arrIndex[0]));
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
            scene1= new Scene(layout1, width, height);
            return scene1;
            
            //   primaryStage.setScene(scene0);
            
            // }
            // catch(Exception e){
               //    System.out.println("No input");
             
   }  

    public static void main(String[] args) {
      
      launch(args);

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
   public static void readCSVFile(String route, ArrayList<String> questionArr, ArrayList<String> answerArr){
      
      // initialize lineArr to read every line
      String[] linesArr = new String[totalLinesInFile(route)];

      try{

         // reads the CSV file
         CSVReader reader = new CSVReader(new FileReader(route));

            // conditions while there is still data on the next line
            while ((linesArr = reader.readNext()) != null) {
               // the answer array
               answerArr.add(linesArr[findAnswers(totalLinesInFile(route))]);
               // the question array
               questionArr.add(linesArr[findQuestion(totalLinesInFile(route))]);
               
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
            //System.out.println("line is <" + txtLine + ">");

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
         // System.out.println("QUESTIONS arr ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++: ");
         // printArr(questionsArray);
         // System.out.println("ANSWERS arr: ");
         // printArr(answersArray);
         // System.out.println("Total line count: " + totalLinesInFile(fullFilePath));

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
      //System.out.println("question is !" + question);
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
      
      //System.out.println("answer is !" + answer);
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
   // public static void printArr(ArrayList<String> arr) {
   //    for (int i = 0; i < arr.size(); i++) {
   //       System.out.print(arr.get(i) + ",");
   //    }
   //    System.out.println();
   // }
   // /**
   //  * @author Sophia Nguyen
   //  * Getting the length of an arraylist
   //  *
   //  * @param al the array list being passed through
   //  * @return the length of the arraylist to later initialize arrays
   //  */
   //  public static int lengthOfArrayLists (ArrayList<String> al){
   //      int length = al.size();
   //      return length;
   //  }

   // /**
   //  * @author Sophia Nguyen
   //  *
   //  * To collect the questions
   //  * @param reader
   //  * @return the string containing the question
   //  */
   // public static String fileQuestions(Scanner reader){
   //    System.out.println("What is your question? To break, type an @");
   //    String question = reader.nextLine();
   //    return question;
   // }

   // /**
   //  * @author Sophia Nguyen
   //  *
   //  * To collect the answers
   //  * @param reader
   //  * @return the string containing the answer
   //  */
   // public static String fileAnswers(Scanner reader){
   //    System.out.println("What is the answer to that question? To break, type an @ but beware doing this may delete your previous question");
   //    String answer = reader.nextLine();
   //    return answer;
   // }
   
   /**
    * @author Sophia Nguyen
    *
    * To determine whether or not user wants to quit by searching for an "@"" symbol
    * @param reader
    * @param inquiry which is the arraylist carrying all of the questions
    * @param reply which is the arraylist carrying all of the answer
    * @return whether or not the user wants to quit
    */
   public static Boolean checkForQuit(String question,  String answer, ArrayList<String> inquiry, ArrayList<String> reply){
      // if question has the @ symbol
      if(question.contains("@")){
         return true;
      }
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