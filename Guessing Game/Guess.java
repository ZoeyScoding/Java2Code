// Programmer: Ziwei Shen
// Class: CS 145
// Date: June. 30th, 2023
// Assignment: Lab1 GuessingGame
// Purpose: This program is to implement a guessing game, allowing the user
//          to play the game multiple times. In each game, the program generates 
//          a random number between 1 and 100, and the user tries to guess the 
//          correct number. After each guess, the program provides feedback to 
//          the user, indicating whether the actual number is higher or lower 
//          than the guess.At the end of the program, the overall results are 
//          displayed, including the total number of games, the total number of 
//          guesses, the average number of guesses per game, and the best game.
 
 
      
// Import Scanner class to read user input from the console 
import java.util.*;

public class Guess {
   
   // Main method - ececution of Java applicaion
   public static void main(String[] args) {
        
      // create a scanner object to read user input
      Scanner input = new Scanner(System.in);      
      // Declare several variables to track game statistics 
      int totalGames = 0;
      int totalGuesses = 0;
      int bestGame = 101;
      
      // Display the introduction to the game
      introduction();
      
      // Ask the user if they want to play again 
      boolean playAgain = true;
      while(playAgain) {
         
         // Update game statistics
         totalGames++;
         int numGuesses = playGame();
         totalGuesses += numGuesses;
         
         if (numGuesses < bestGame) {
            bestGame = numGuesses;
         }
         
         // Ask the user if they want to play again
         System.out.print("Do you want to play again?");
         String response = input.next();
         if (response.toLowerCase().startsWith("n")){
            playAgain = false;
         } else if (response.toLowerCase().startsWith("y")){
            playAgain = true; 
         }
      }// end of while loop 
      
      // Diasplay the game results
      reportResults(totalGames, totalGuesses, bestGame);
   }// end of main method
   
     
   public static void introduction () 
   /*
      Description:
         This method displays an introduction to the game, explaining the rules and 
         instructions to the user.
   */
   {
        System.out.println("This program allows you to play a guessing game.");
        System.out.println("I will think of a number between 1 and ");
        System.out.println("100 and will allow you to guess until");
        System.out.println("you get it. For each guess, I will tell you");
        System.out.println("whether the right answer is higher or lower");
        System.out.println("than your guess.");
        System.out.println();
   }// end of introduction method 
   
   
   public static int playGame () 
   /*
      Description:
         This method conducts a single guessing game. It generates a random number between 1 and 
         a specified range. The user is prompted to make guesses, and the program provides feedback 
         on whether the guess is higher or lower than the random number. The method returns the 
         number of guesses made.

      Returns: 
         int - the number of guesses made
   */
   {
      // create a random object and generate a random number 
      Random random = new Random();
      int range = 2;
      int randomNumber = random.nextInt(range) + 1;
      
      // create a scanner object to read user input
      Scanner input = new Scanner(System.in);
      int guess;
      int numGuesses = 0;
      
      System.out.println("I'm thinking of a number between 1 and " + range + "...");
      System.out.print("Your guess? ");
      
      // Comparing uses' guess to the correct result
      while((guess = input.nextInt()) != randomNumber){
         numGuesses++;
         
         // Provide feedback to the user based on their guess
         if(guess < randomNumber) {
            System.out.println("It's higher.");
         } else if (guess > randomNumber) {
            System.out.println("It's lower.");    
         }


      }// end of while loop 
      numGuesses++;
      System.out.println(
         "You got it right in " + numGuesses + " guess" + (numGuesses > 1 ? "es" : ""));
      System.out.println();
      
      return numGuesses;
   }// end of playGame method 


   private static void reportResults(int totalGames, int totalGuesses, int bestGame) 
   /*
      Description:
         This method reports the overall results of the game. It takes the total number 
         of games played, the total number of guesses made, and the best game (fewest 
         guesses) as parameters. It calculates the average number of guesses per game 
         and displays all the results.

      Parameters:
         totalGames (type: int) - the total number of games played
         totalGuesses (type: int) - the total number of guesses made
         bestGame (type: int) - the best game (fewest guesses)
   */
   {
      double avgGuesses = (double) totalGuesses / totalGames;

      System.out.println("Overall results:");
      System.out.println("  total games = " + totalGames);
      System.out.println("  total guesses = " + totalGuesses);
      System.out.printf("  guesses/game = %.1f\n", avgGuesses);
      System.out.println("  best game = " + bestGame);
   }// end of reportResults method
}// end of class 
      