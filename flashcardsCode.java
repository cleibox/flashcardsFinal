/**
 * Date: May 20, 2021
 * Name: Cynthia Lei, Daiphy Lee, Johnny He, Sophia Nguyen
 * Teacher: Mr. Ho
 * Description: Flashcards Final Project
 */

import java.util.Scanner;
import java.util.ArrayList;
 // Testing new branch
 class flashcardsCode {
    public static void main(String[] args) {
      Scanner reader = new Scanner(System.in);
      // Needs an array list because I cannot properly predict when the user wants to quit
      // Could ask them for the amount of questions and answers beforehand but that is not really flexible to the user
      ArrayList<String> questions = new ArrayList<>();
      ArrayList<String> answers = new ArrayList<>();
      Boolean quit = false;
      do{
         quit = checkForQuit(reader, questions, answers);
         // Checks one last time for quit just in case user typed in wrong or they need the @ symbol in the question or answer
         if (quit==true){
            System.out.println("Are you sure you have inputted all your questions");
            String finish = reader.nextLine();
            // If user chooses not to quit program will loop
            if (finish.equals("No")){
               quit=false;
            }
            else{
               quit=true;
            }
         }
      }while(quit == false);
      // Final questions and answers
      System.out.println(questions);
      System.out.println(answers);
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
