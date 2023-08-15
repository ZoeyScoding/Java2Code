/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: July. 12th, 2023
   Subject: Lab 4 Deck of cards
   Purpose: Represents the BlackJack game with a deck, dealer hand, player hands, scores, and 
            game flow.
*/

import java.util.*;


public class BlackJack {

   private Deck deck;
   private Queue<Card> dealerHand;
   // Declare the hands of each player
   private Queue<Card> [] playerHands;
   // Declare the scores of each player
   private int[] playerScores;
   private Scanner scanner;
   // Indicates whether the game is for a single player or not
   private boolean isSinglePlayer;
   // The number of players in the game
   private int numPlayers;


   /*
      Description
         - Constructs a BlackJack game with the specified number of players and game mode.
      Parameters
         - isSinglePlayer: true if the game is for a single player, false otherwise
         - numPlayers: the number of players in the game
      Return
         - None
   */
   public BlackJack(boolean isSinglePlayer, int numPlayers) {
      this.deck = new Deck();
      this.dealerHand = new LinkedList<>();
      this.playerHands = new Queue[numPlayers];
      this.playerScores = new int[numPlayers];
      this.scanner = new Scanner(System.in);
      this.isSinglePlayer = isSinglePlayer;
      this.numPlayers = numPlayers;

      // Create a new hand for each player and initialize the score of each player to 0
      for (int i = 0; i < numPlayers; i++) {
         playerHands[i] = new LinkedList<>();
         playerScores[i] = 0;
      }
   }

   /*
      Description
         - Plays a game of BlackJack, including rounds, player turns, dealer turn, and determining the winner.
      Parameters
         - None
      Return
         - None
   */
   public void playGame() {
      boolean validInput = false;
      roundSetUp();
      
      while (!validInput){
         // roundSetUp();
         for (int i = 0; i < numPlayers; i++) {
            playerTurn(i);
         }

         dealerTurn();
         determineWinner();
         
         // Check if allowing user to play again
         //    - if user choose "y", then continue the game; 
         //    - if "n", then exit the game.
         System.out.println("Play again? yes(y) no(n)");
         String playAgain = scanner.next().toLowerCase();

         switch (playAgain) {
            case "y":
               validInput = true; 
               break;
            case "n":
               System.out.println("Game ended. Thank you for playing!");
               validInput = true; 
               System.exit(0);
               break;
            default:
               System.out.println("Invalid input. Please try again.");
               break;
         }
      }
   }

   /*
      Description 
         - Sets up a new round by clearing hands, scores, and dealing initial cards.
      Parameters
         - None
      Return
         - None
   */
   private void roundSetUp() {
      // Clear the dealer's hand
      dealerHand.clear();
      
      // Clear the hands of each player
      for (int i = 0; i < numPlayers; i++) {
         playerHands[i].clear();
      }
      
      // Reset the scores of each player to 0
      Arrays.fill(playerScores, 0);
      int dealerScore = 0;

      // Initial deal, add a card to the hands of dealer and each player respectively
      for (int i = 0; i < 2; i++) {
         dealerHand.add(deck.drawCard());

         for (int j = 0; j < numPlayers; j++) {
            playerHands[j].add(deck.drawCard());
         }
      }

      // Display the hands after the initial deal
      showHands();
   }


   /*
      Description
         - Performs a player's turn by allowing them to hit or stay until they reach 21 or decide to stay.
      Parameters
         - playerIndex: the index of the player in the playerHands and playerScores arrays
      Return
         - None
   */
   private void playerTurn(int playerIndex) {
      Queue<Card> playerHand = playerHands[playerIndex];
      // Calculate the score of the player's hand
      int playerScore = calculateScore(playerHand);

      while (playerScore < 21) {
         System.out.print("\nPlayer " + (playerIndex + 1) + ", do you want to (H)it or (S)tay? ");
         String choice = scanner.next().toLowerCase();

         // If user select "h", adding a card to the player's hand and display the updated hands and the score.
         // If user select "s", skipping the user and exit the loop 
         if (choice.equals("h")) {
            playerHand.add(deck.drawCard());
            showHands();
            playerScore = calculateScore(playerHand);
         } else if (choice.equals("s")) {
            break;
         } else {
            System.out.println("Invalid choice. Please try again.");
         }
      }
      // Update the player's score
      playerScores[playerIndex] = playerScore;
   }

   /*
      Description
         - Performs the dealer's turn by drawing cards as long as at least one player hasn't busted (score <= 21). 
         However, there is a 50% chance for the dealer to choose to stop drawing cards randomly when their score 
         is between 12 and 16 (inclusive). If the dealer's score is 17 or higher, they will also stop drawing cards.
      Parameters
         - None
      Return
         - None
   */
   private void dealerTurn() {
      // Calculate the score of the dealer's hand
      int dealerScore = calculateScore(dealerHand);

      while (Arrays.stream(playerScores).anyMatch(score -> score <= 21)) {
         // Dealer reaches a score of 17 or more, or randomly chooses to stop drawing cards
         if (dealerScore >= 17 || (dealerScore >= 12 && Math.random() < 0.5)) {
            break;
         }
         // Add a card to the dealer's hand
         dealerHand.add(deck.drawCard());  
         // Recalculate the score of the dealer's hand
         dealerScore = calculateScore(dealerHand);  
      }

      showHands();
   }

   /*
      Description
         - Displays the current hands of the dealer and players.
      Parameters
         - None
      Return
         - None
   */
   private void showHands() {
      System.out.println("\nDealer's Hand:");
      System.out.println("| ============ |");
      // Display the first card of the dealer's hand
      System.out.println(dealerHand.peek());

      for (int i = 0; i < numPlayers; i++) {
         Queue<Card> playerHand = playerHands[i];
         // Calculate the score of the player's hand
         int playerScore = calculateScore(playerHand);

         System.out.println("\nPlayer " + (i + 1) + " Hand:");

         for (Card card: playerHand) {
            // Display each card in the player's hand
            System.out.println(card);
         }
         // Display the player's score
         System.out.println("= " + playerScore);
      }

      System.out.println();
   }

   /*
      Description
         - Calculates the score of a given hand, considering the value of each card and handling aces.
         Aces are initially worth 11;
         If the score exceeds 21 and there are aces, change the value of an ace to 1
      Parameters
         - hand: the hand of cards for which to calculate the score
      Return
         - The calculated score of the hand
   */
   private int calculateScore(Queue<Card> hand) {
      int score = 0;
      int numAces = 0;

      for (Card card: hand) {
         if (card.getRank().equals("Ace")) {
            // Aces are initially worth 11
            score += 11;       
            numAces++;
         } else {
            // Add the value of non-ace cards
            score += card.getValue();   
         }
      }

      while (score > 21 && numAces > 0) {
         // If the score exceeds 21 and there are aces, change the value of an ace to 1
         score -= 10;
         numAces--;
      }

      return score;
   }

   /*
      Description
         - Determines the winner(s) of the round based on the scores of the dealer and players.
      Parameters
         - None
      Return
         - None
   */
   private void determineWinner() {
      System.out.println("\n----- Round Results -----");
      System.out.println("Dealer's Hand:");

      for (Card card: dealerHand) {
         System.out.println(card);
      }
      // Display the score of the dealer's hand
      System.out.println("= " + calculateScore(dealerHand));

      for (int i = 0; i < numPlayers; i++) {
         Queue<Card> playerHand = playerHands[i];
         int playerScore = playerScores[i];
         System.out.println("\nPlayer " + (i + 1) + " Hand:");

         for (Card card: playerHand) {
            System.out.println(card);
         }
         // Display the score of the player's hand
         System.out.println("= " + playerScore);

         // Display the game result, there are four possiblities:
         // - If player's score exceeds 21, they lose;
         // - If player's score is higher than the dealer's score or the dealer's score exceeds 21, they win;
         // - If player's score is lower than the dealer's score, they lose;
         // - If player's score is the same as the dealer's score, it's a tie
         if (playerScore > 21) {
            System.out.println("\nPlayer " + (i + 1) + " Loses!");
         } else if (calculateScore(dealerHand) > 21 || playerScore > calculateScore(dealerHand)) {
            System.out.println("\nPlayer " + (i + 1) + " Wins!");
         } else if (playerScore < calculateScore(dealerHand)) {
            System.out.println("\nPlayer " + (i + 1) + " Loses!");
         } else {
            System.out.println("\nPlayer " + (i + 1) + " It's a tie!");
         }
      }
   }
}