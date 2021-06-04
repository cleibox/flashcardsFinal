/**
 * Date: May 20, 2021
 * Name: Cynthia Lei, Daiphy Lee, Johnny He, Sophia Nguyen
 * Teacher: Mr. Ho
 * Description: Flashcards Final Project
 */
 
//import classes
// File imports
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.CornerRadii;
import javafx.scene.image.Image;
import javafx.scene.Group;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

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

      // GUI
      primaryStage.setTitle("Flashcard GUI"); // GUI Window Title
      
      // All scenes' resolution
      int width = 720;
      int height = 450;

      try{
         // assign scenes with its display components
         menuScene = showMenuGUI(primaryStage, width, height, questionsArr, answersArr, arrIndex);
                  
         primaryStage.setScene(menuScene); // select which scene to display
         primaryStage.show(); // display the selected scene
      }
      catch(Exception e){
      }
   }

    public static void main(String[] args) {
      launch(args);
    }  

    /**
     * @author Cynthia Lei
     * Responsible for coding the components of the menu
     * @author Johnny He 
     * Responsible for styling the menu
     * Displays the menu GUI
     * 
     * @author Daiphy Lee
     * Responsible for adding instructions
     * @author Sophia Nguyen 
     * Responsible for setting up manual input scene
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
      // Button font modifications
      Font font = Font.font("Arial", FontWeight.BOLD, 24);

      // Title Dropshadow
      DropShadow shadow = new DropShadow();
      shadow.setOffsetX(10);
      shadow.setOffsetY(10);
      shadow.setColor(Color.rgb(40, 40,40, 0.5));

      Text menuTitleLabel = new Text ("MENU"); // menu title
      // Menu title modifications (Bolded, Size, Colors, Dropshadow)
      menuTitleLabel.setFont(Font. font ("Arial", FontWeight.BOLD, 96)); // label font style and size 
      menuTitleLabel.setFill(Color.BLUEVIOLET);
      menuTitleLabel.setEffect(shadow);
      
      /* Initialize buttons ------------------------------ */
      // Button for inputting files
      Button instructionsbutton = new Button("Instructions");
      instructionsbutton.setFont(font);
      instructionsbutton.setOnAction(action ->{
         instructionScene = showInstructions(primaryStage, width, height, questionsArrList, answersArrList, arrIndex); 
         primaryStage.setScene(instructionScene);          
      });      
      Button openFileButton = new Button("Open File (.txt or .csv)");
      openFileButton.setFont(font);
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

      /* Manual Input Scene ------------------------------ */
      // When user wants to input questions and answers manually
      Button manualInputButton = new Button("Input questions/answers");
      manualInputButton.setFont(font);
       manualInputButton.setOnAction(action -> {
           sceneInputText = sceneManualInputText(primaryStage, questionsArrList, answersArrList, arrIndex, width, height);
           primaryStage.setScene(sceneInputText);
           primaryStage.show();
       });

      /* Scene graphical display ------------------------------ */
      GridPane menuLayout = new GridPane();  
      menuLayout.addRow(0, menuTitleLabel);  
      menuLayout.addRow(1, instructionsbutton);  
      menuLayout.addRow(2, openFileButton);
      menuLayout.addRow(3, manualInputButton);  
      menuLayout.addRow(4, warningText); 
      // Center Gridpane    
      menuLayout.setAlignment(Pos.CENTER);
      // Adding space between rows of the Gridpane
      menuLayout.setVgap(10);


      Background background = backgroundImage("school.jpg");
      menuLayout.setBackground(background);

      menuScene = new Scene(menuLayout, width, height);
      
      return menuScene;

   }
    /**
     * @author Sophia Nguyen
     * Setting background image for flashcards scene
     * 
     * @return background that will be used
     * if program can find the image, it will use a flashcard background
     * if not, it will set the colour to default grey
     */
    public Background backgroundImage(String backgroundFileName){
      // Background default if program cannot find image
      BackgroundFill background_fill = new BackgroundFill(Color.GREY, 
      CornerRadii.EMPTY, Insets.EMPTY);

      // create default Background
      Background background = new Background(background_fill);

      //Grabbing pathway of flashcards.png
      Path currentDir = Paths.get(backgroundFileName); 
      Path fullPath = currentDir.toAbsolutePath(); 
      String bckImagePath = fullPath.toString();

      // create a input stream
      try{
      FileInputStream input = new FileInputStream(bckImagePath);
         // create a image
         Image image = new Image(input);

         // create a background image
         BackgroundImage backgroundimage = new BackgroundImage(image, 
                                          BackgroundRepeat.NO_REPEAT, 
                                          BackgroundRepeat.NO_REPEAT, 
                                          BackgroundPosition.DEFAULT, 
                                             BackgroundSize.DEFAULT);

         // create flashcards Background
         Background flashcards = new Background(backgroundimage);
         // return if it can find
         return flashcards;
      } 
      //return default if it can't find
      catch(Exception e){
         return background;
      }
    }

    /**
     * 
     * @author Sophia Nguyen
     * Setting up the manual input scene and checking to sure there are questions and answers before adding to arrays
     * 
     * @param primaryStage the stage that is set
     * @param questionsArr the array list containing the questions that are inputted
     * @param answersArr the array list containing the answers that are inputted
     * @param arrIndex this tracks which question number and answer the flashcards GUI should display
     * @param width the width of the menu GUI scene
     * @param height the height of the menu GUI scene
     * @return sceneInputText is the scene for the manual input
     */
    public Scene sceneManualInputText(Stage primaryStage, ArrayList<String> questionsArr, ArrayList<String> answersArr, int[] arrIndex, int width, int height){
      //Button font modifications
      Font font = Font.font("Arial", FontWeight.BOLD, 14);

      Text questionText = new Text("Question");
      // Question input fonts modifications
      questionText.setFont(Font. font("font", FontWeight.BOLD, 15));

      Text answerLabel = new Text("Answer");
      // Answer input fonts modifications
      answerLabel.setFont(Font. font("font", FontWeight.BOLD, 15));

        // Creating textfields to get input for questions and answers
        TextField questionInput = new TextField();  
        TextField answerInput = new TextField();  
        // Creating a button for users to submit their questions and adding more
        Button submit = new Button("Submit"); 
        submit.setFont(font);
        // Creating a button for users to view their flashcards after they inputted all their questions
        Button finish = new Button("Finished Inputting");
        finish.setFont(font);
        // Error text to force users to input both a question and answer before submitting
        Text error = new Text("");
        
        // When the user hits submit, program takes in user input from the text field
        submit.setOnAction(action -> {
           // Turns it into a string
           String questionString = questionInput.getText();
           String answerString = answerInput.getText();
               // If the question text field is empty, program does not let user submit and prompts them for a question
              if(questionString.equals("")){
                 error.setText("Please input a question.");   
              }
              // If the answer text field is empty, program does not let user submit and prompts them for an answer
              else if(answerString.equals("")){
                 error.setText("Please input an answer.");
              }
              // If the question text field and answer text field both have an answer, program adds the string from the textfields into the array
              // Program then resets the question and answer textfield for user to input more
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
        // When the user is done inputting all of their questions and answers they would hit submit
        // It directs them to their flashcards
        finish.setOnAction(action -> {
           try{
               flashcardsScene = showFlashcardsGUI(primaryStage,questionsArr,answersArr,arrIndex, width, height);
               primaryStage.setScene(flashcardsScene);
           }
           catch(Exception e){
              primaryStage.setScene(sceneInputText);
              error.setText("Please hit submit before finish");
           }
        });

        Background background = backgroundImage("school.jpg");
        // The setup of the scene for manual inputs
        GridPane root = new GridPane();  
        root.addRow(0, questionText, questionInput);  
        root.addRow(1, answerLabel, answerInput);  
        root.addRow(2, submit, finish, error, backToMenuButton(primaryStage, width, height, questionsArr, answersArr, arrIndex));  
        //Set background
        root.setBackground(background);
        // Center Gridpane  
        root.setAlignment(Pos.CENTER);
        sceneInputText = new Scene(root, width, height);
        return sceneInputText;
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
      
      // Instruction title Dropshadow
      DropShadow shadow = new DropShadow();
      shadow.setOffsetX(5);
      shadow.setOffsetY(5);
      shadow.setColor(Color.rgb(20, 20,20, 0.5));

      Text instructionsLabel = new Text ("INSTRUCTIONS"); // instructions title
      // Instruction title modifications (Bolded, Size, Colors, Dropshadow)
      instructionsLabel.setFont(Font. font("font", FontWeight.BOLD, 60));
      instructionsLabel.setFill(Color.BLUEVIOLET);
      instructionsLabel.setEffect(shadow);

      // the instructions text
      Text instruct = new Text("Welcome. You have 3 different options to create your very own personalized flashcards! \nOption 1 and 2 is to enter a .txt or .csv file with your questions and answers. \nThis file may have been already created from your notes in advanced. \nOption 3 allows you to manually enter your questions and answers. \nPlease ensure that you enter all of your Q&As before clicking 'Finished Inputting'! \nBeware, whenever you click 'Back to Menu' all inputted questions and answers will be erased! \nGood Luck and have fun studying!");
      instruct.setFont(Font. font("Arial", 16));

      instruct.setTranslateX(10);

      VBox instructionsLayout = new VBox(instructionsLabel, instruct, backToMenuButton(primaryStage, width, height, questionsArrList, answersArrList, arrIndex));
      instructionsLayout.setSpacing(10);

      Background background = backgroundImage("school.jpg");
      instructionsLayout.setBackground(background);
      
      // Adding components into the scene
      instructionScene = new Scene(instructionsLayout, width, height);

      return instructionScene;
    }
    /**
    * @author Daiphy Lee 
    * Button that returns user to the main menu as well as delete past user inputs for the questions and answers in the arrayList
    *
    * @param primaryStage The "base" of this GUI. It hosts all the scenes.
    * @param width the width of the menu GUI scene
    * @param height the width of the menu GUI scene
    * @param questionsArrList arraylist that contains all the questions
    * @param answersArrList arraylist that contains all the answers that correspond to the questions in questionsArrList
    * @param arrIndex this tracks which question number and answer the flashcards GUI should display
    * @return the back button which deletes all past user inputs into the arraylist as well as returns to menu
    */
    public Button backToMenuButton(Stage primaryStage, int width, int height, ArrayList<String> questionsArrList, ArrayList<String> answersArrList, int[] arrIndex){
      Button back = new Button("Back to Menu"); // the button to return to the menu scene
      // Button font modifications
      Font font = Font.font("Arial", FontWeight.BOLD, 14);
      back.setFont(font);
      back.setPrefSize(150,30);
      back.setOnAction(action ->{
         // returns to the main menu scene
         primaryStage.setScene(showMenuGUI(primaryStage, width, height, questionsArrList, answersArrList, arrIndex)); 
         // deletes past user inputs for both questions and answers
         questionsArrList.removeAll(questionsArrList);      
         answersArrList.removeAll(answersArrList);
         arrIndex[0] = 0; // resets the index array to 0      
      }); 
      return back;
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
     * @param height the height of the menu GUI scene
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
            // reading txt file by adding elements to questions and answers arraylist
            try{
               readTxtFile(filePath, questionsArrList, answersArrList); 
            }
            catch(Exception e){
               warningText = "Invalid, action terminated.";
            }
            // Set flashcards scene now that the components (arraylists) are set
            flashcardsScene = showFlashcardsGUI(primaryStage, questionsArrList,answersArrList,arrIndex, width, height);
            primaryStage.setScene(flashcardsScene);
         }
         // If inputted file is .csv file
         else if ((fileName.substring(fileName.length() - 4, fileName.length())).equals(".csv")) {
            // reading csv file by adding elements to questions and answers arraylist
            try{
               readCSVFile(filePath, questionsArrList, answersArrList);
            }
            catch(Exception e){
               warningText = "Invalid, action terminated.";
            }
            // Set flashcards scene now that the components (arraylists) are set
            flashcardsScene = showFlashcardsGUI(primaryStage, questionsArrList, answersArrList, arrIndex, width, height);
            primaryStage.setScene(flashcardsScene);
         }
         // Forces user to input either .txt or .csv in order to go to flashcards GUI
         else {
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
    public Scene showFlashcardsGUI(Stage primaryStage, ArrayList<String> questionsArrList, ArrayList<String> answersArrList, int[] arrIndex, int width, int height){
      // Button font modifications
      Font font = Font.font("Arial", FontWeight.BOLD, 14);
   
      Text cardCounter = new Text("Card: " + (arrIndex[0]+1)); // the card number when the flashcards are first shown
      cardCounter.setFont(Font. font("font", FontWeight.BOLD, 16));

      //Adding the first question on the flashcard
      Text questionText = new Text("Question: " + questionsArrList.get(arrIndex[0])); 
      // Flashcard question font style and size
      questionText.setFont(Font. font("font", FontWeight.BOLD, 18));
      questionText.setTranslateX(100);
      questionText.setTranslateY(10);

      //Wrapping text for questions
      questionText.setWrappingWidth(520);


      //Label for answer is empty until user chooses to show
      Text answerText = new Text("");
      // Flashcard answer font style and size
      answerText.setTranslateX(100);
      answerText.setFont(Font. font("font", FontWeight.BOLD, 18));

      //Wrapping text for answers
      answerText.setWrappingWidth(520);
      
      //User chooses to look at answer
      //Adding the answer on the flashcard
      Button showAns = new Button("Show Answer");
      showAns.setFont(font);
      showAns.setPrefSize(150,30);
      showAns.setTranslateX(500);
      showAns.setTranslateY(50);
      showAns.setOnAction(action -> {
         answerText.setText("Answer: " + answersArrList.get(arrIndex[0]));
      });

      //User no longer wants to look at answer
      //Button clears the label
      Button clearAns = new Button("Clear Answer");
      clearAns.setFont(font);
      clearAns.setPrefSize(150,30);
      clearAns.setTranslateX(100);
      clearAns.setTranslateY(0);
      clearAns.setOnAction(action -> {
         answerText.setText("");
      });

      Text warningText = new Text("");
      //User chooses to go to next question
      Button nextButton = new Button("Next");
      nextButton.setFont(font);
      nextButton.setPrefSize(150,30);
      nextButton.setOnAction(action -> {
         //There are no more flashcards so program displays an error
         if (arrIndex[0] == questionsArrList.size() - 1){
            warningText.setText("This is the last question. You cannot go next.");
            warningText.setTranslateX(247.5);
         }
         // There are more flashcards so program continues
         else {
            arrIndex[0]++;
            questionText.setText("Question: " + questionsArrList.get(arrIndex[0]));
            cardCounter.setText("Card: " + (arrIndex[0]+1)); // the card number when user clicks next
            answerText.setText("");
            warningText.setText("");
         }
      });

      //User chooses to go to the previous question
      Button backButton = new Button("Back");
      backButton.setFont(font);
      backButton.setPrefSize(150,30);
      backButton.setOnAction(action -> {
         //There are no flashcards behind so program displays an error
         if (arrIndex[0] == 0){
            warningText.setText("This is the first question. You cannot go back.");
            warningText.setTranslateX(247.5);
         }
         // There are more flashcards so program continues
         else {
            arrIndex[0]--;
            questionText.setText("Question: " + questionsArrList.get(arrIndex[0]));
            cardCounter.setText("Card: " + (arrIndex[0]+1)); // the card number when user clicks back
            answerText.setText("");
            warningText.setText("");
         }
      });
         // Setting the location of the button
         backButton.setTranslateX(100);
         backButton.setTranslateY(0);

         Background background = backgroundImage("flashcards.png");
         // Setting the location of the button
         nextButton.setTranslateX(500);
         nextButton.setTranslateY(50);

         //Set the location of the button
         cardCounter.setTranslateX(600);
         //cardCounter.setTranslateY(400);

         // Scene size modification 
         VBox layout1 = new VBox(20);
         //Setting background
         layout1.setBackground(background);
         layout1.getChildren().addAll( questionText, nextButton, backButton, answerText, showAns, clearAns, warningText, cardCounter,backToMenuButton(primaryStage, width, height, questionsArrList, answersArrList, arrIndex));
         flashcardsScene= new Scene(layout1, width, height);
         return flashcardsScene; // since we're returning a global variable, this is a nonstatic method
    }

   /**
    * @author Cynthia Lei Determine the total number of lines in the given file.
    * Determine the total number of lines in the given file.
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

         br.close();
      }

      // Program cannot find file
      catch (IOException e) {
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
         
         reader.close();   // closes CSVReader
      }
      // catch when file is not found or when there is only 1 question/answer
      catch(Exception e){     
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

}
