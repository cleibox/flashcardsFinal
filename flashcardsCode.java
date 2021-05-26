/**
 * Date: May 20, 2021
 * Name: Cynthia Lei, Daiphy Lee, Johnny He, Sophia Nguyen
 * Teacher: Mr. Ho
 * Description: Flashcards Final Project
 */

import java.util.Scanner;
import java.util.ArrayList;
 class flashcardsCode {
    public static void main(String[] args) {
      Scanner reader = new Scanner(System.in);
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
      printArray(questions);
      printArray(answers);
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
    * To print the array
    * @param arr the array being printed
    */
   public static void printArray(String[] arr){
      for (int i = 0; i < arr.length; i++){
          System.out.print(arr[i]);
          System.out.print(",");
      }
      System.out.println();
  }
   /**
    * @author Sophia Nguyen
    *
    * To collect the questions and looks for break
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
    * To collect the answers and looks for break
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
