/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: July. 12th, 2023
   Subject: Lab 4 Deck of cards
   Purpose: Represents a playing card with a suit, rank, and value.
*/


/*
   Description
      Represents a playing card with a suit, rank, and value.

   Parameters
      - suit: the suit of the card (e.g., "Spades", "Hearts", "Diamonds", "Clubs")
      - rank: the rank of the card (e.g., "2", "3", ..., "10", "Jack", "Queen", "King", "Ace")
      - value: the value of the card in the game (numeric value for cards 2-10, face cards are 10)
*/
public class Card {
   private String suit;
   private String rank;
   private int value;
   
   public Card(String suit, String rank, int value){
      this.suit = suit;
      this.rank = rank;
      this.value = value;
   }
   
   public String getSuit(){
      return suit;
   }
   
   public String getRank(){
      return rank;
   }
   
   public int getValue(){
      return value;
   }
   
   public String toString(){
      return "| " + rank + " of " + suit + " |";
   }
}