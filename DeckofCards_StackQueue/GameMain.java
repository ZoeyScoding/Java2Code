/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: July. 12th, 2023
   Subject: Lab 4 Deck of cards
   Purpose: This program implements the functionality of a Blackjack game, allowing multiple 
            players to play against the dealer and determining the winner based on the scores.
            The objective is to have a hand value that is closer to 21 than the dealer's hand 
            value, without exceeding 21. Each card has a specific value, with numeric cards 
            worth their face value, face cards worth 10, and Aces worth either 1 or 11, depending 
            on the player's choice. The game starts with each player receiving two cards, and 
            they can choose to "hit" and draw additional cards or "stay" and end their turn. 
            The player with a higher hand value than the dealer wins, while players with a lower 
            hand value lose, and ties can occur if the player and dealer have the same score. 
*/


import java.util.*;

public class GameMain {

   public static void main(String args[]) {
      // Display welcome message and game rules
      System.out.println("======Welcome to BlackJack card game======");
      System.out.println("Rules of the game");
      System.out.println("https://www.blackjackapprenticeship.com/how-to-play-blackjack/");
      System.out.println("==========================================");
      
      Scanner userInput = new Scanner(System.in);
      boolean isPlaying = true;
      
      while(isPlaying) {
         System.out.println("Play game? yes(y) no(n)");
         String choice = userInput.next().toLowerCase();
         
         switch (choice){
            case "y" :{
               System.out.print(
                  "\nChoose the number of players (1 for single player, 2-7 for multiple players): "
                  );
               int numPlayers = userInput.nextInt();
               if (numPlayers < 1 || numPlayers > 7) {
                  System.out.println("Invalid number of players. Please try again.");
                  continue;
               }
   
               for (int i = 1; i <= numPlayers; i++) {
                  System.out.println("\n===== Player " + (i + 1) + " =====");
                  BlackJack game = new BlackJack(numPlayers == 1, numPlayers);
                  // game.roundSetUp(numPlayers);
                  game.playGame();
               }
               break;
            }
            case "n":{
               System.out.println("Game ended. Thank you for playing!");
               isPlaying = false;
               break;
            }
            default:{
               System.out.println("Invalid choice try again");
               break;
            }
         }
         
         // Exit the while loop if isPlaying is false
         if (!isPlaying) {
            break;
         }
      }
      
   }
}