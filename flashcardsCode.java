
/**
 * Date: May 20, 2021
 * Name: Cynthia Lei, Daiphy Lee, Johnny He, Sophia Nguyen
 * Teacher: Mr. Ho
 * Description: Flashcards Final Project
 */
 
//import classes
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

 class flashcardsCode {
    public static void main(String[] args) {

      String path = "/Users/daiphylee/11 ComSci Java Lessons/A. Assignments/flashcardsFinal/";
      String name = "tester.csv";
      String fullroute = path+name;
      ArrayList<String> questionsArr = new ArrayList<String>();
      ArrayList<String> answersArr = new ArrayList<String>();

      readCSVFile(fullroute, questionsArr, answersArr);

   }
   public static void readCSVFile(String route, ArrayList<String> questionArr, ArrayList<String> answerArr){
      String line = "";
      String realQuestion = "";

      // ArrayList<String> allArr = new ArrayList<String>();
      try{
         File file = new File(route);
         BufferedReader br = new BufferedReader(new FileReader(file));
         while((line = br.readLine()) != null){
            
            // uses the "," delimeter to find the question and answer
            // String[] elements = line.split("\\,");
            // for(String all:elements){
               int lastCharNum = findQuestionMarkTxt(line);
               // Adding the question portion into the question array
               realQuestion = findQuestion(line, lastCharNum);
               troubleshootAndAddElementToArr(questionArr, realQuestion);
            // }
            // String all = allArr.toString();
            // int lastCharNum = allArr.length;
            // // Adding the question portion into the question array
            // realQuestion = findQuestion(all, lastCharNum);
            // troubleshootAndAddElementToArr(questionArr, realQuestion);
            // for(String str:lineArr){
            // System.out.println("line is <" + str + ">");
            // }
         }
         printArrList(questionArr);
         br.close();
      }
      catch(IOException e){
         System.out.println("Cannot find File. Please Reenter.");
      }
   }
   public static String findQuestion(String CSVLine, int lastnum){
      String question = CSVLine.substring(0, lastnum +1);
      System.out.println("question is " + question);
      return question;
   }
   public static int findQuestionMarkTxt(String txtLine) {
      int len = txtLine.length();
      for (int i = 0; i < len; i++) {
         // search for '?'
         if (txtLine.charAt(i) == ',') {
            return i;
         }
      }
      return -1; // no '?' which means no question
   }
   public static void troubleshootAndAddElementToArr(ArrayList<String> arrList, String element){
      if (element.equals("")){ // no question or answer present in the line
         // this prevents an array from "lagging behind" due to missing elements
         arrList.add("Placeholder"); 
      }
      else {
         arrList.add(element);
      }
   }
   public static void printArrList(ArrayList<String> arrList) {
      for (int i = 0; i < arrList.size(); i++) {
         System.out.print(arrList.get(i) + ",");
      }
      System.out.println();
      System.out.println("The array size is " + arrList.size());
   }

 }
