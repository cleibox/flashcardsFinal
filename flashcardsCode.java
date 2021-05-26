
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
// import org.apache.commons.csv.CSVFormat;
// import org.apache.commons.csv.CSVParser;
// import org.apache.commons.csv.CSVRecord;
import com.opencsv.CSVReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

 class flashcardsCodet {
    public static void main(String[] args) {

      String path = "/Users/daiphylee/11 ComSci Java Lessons/A. Assignments/flashcardsFinal/";
      String name = "tester.csv";
      String fullroute = path+name;
      // int sizefornow = 100;
      String[] questionsArr = new String[totalLinesInFile(fullroute)];
      String[] answersArr = new String[totalLinesInFile(fullroute)];
      String[] linesArr = new String[totalLinesInFile(fullroute)];

      readCSVFile(fullroute, questionsArr, answersArr, linesArr);

   }
   public static void readCSVFile(String route, String[] questionArr, String[] answerArr, String[] lineArr){
      String line = "";
      String realQuestion = "";

      // ArrayList<String> allArr = new ArrayList<String>();
      try{
            // Reader reader = Files.newBufferedReader(Paths.get(route));
            // CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            CSVReader reader = new CSVReader(new FileReader(route));


            while ((lineArr = reader.readNext()) != null) {
               // // answerArr = lineArr[findAnswers(totalLinesInFile(route))];
               // answerArr = (lineArr[findAnswers(totalLinesInFile(route))]);
               System.out.println("Question: " + lineArr[findQuestion(totalLinesInFile(route))]);
               // System.out.println("Answer: ");
               // printArr(lineArr[findQuestion(totalLinesInFile(route))]);
           }
         
         // csvParser.close();
         // CSVReader.close();
      }
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
   public static int findQuestion(int countLines){
      for (int i = 0; i < countLines; i++){
         // search for every other line
         if (i % 2 == 0) {
            return i;
         }
         
      }
      return -1;
   }
   public static int findAnswers(int countLines){
      for (int i = 0; i < countLines; i++){
         // search for every other line
         if (i % 2 != 0) {
            return i;
         }
         
      }
      return -1;
   }
  
   public static void printArr(String[] arr) {
      for (int i = 0; i < arr.length; i++) {
         System.out.print(arr[i] + ",");
      }
      System.out.println();
      System.out.println("The array size is " + arr.length);
   }

 }
