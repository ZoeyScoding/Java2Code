/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: July. 12th, 2023
   Subject: Lab 4 Deck of cards
   Purpose: Represents a deck of cards. 
*/

import java.util.*;

public class Deck {

   private Stack<Card> currentDeck = new Stack<>();
   private Queue<Card> discard = new LinkedList<>();
   
   public Deck(){
      String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
      String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
      
      for (String suit : suits ){
         for(int i = 0; i < ranks.length; i++) {
            // Assigning numeric value for cards 2-10, face cards are 10
            int value;
            if (i < 9) {
               value = i + 2;
            } else {
               value = 10;
            }
            currentDeck.push(new Card(suit, ranks[i], value));
         }
      }
      shuffleDeck();
   }
   
   /*
      Description
         - Draws a card from the deck. If the deck is empty, shuffle the discarded cards and use them 
           as the new deck.
      Parameters
         - None
      Return
         - The card drawn from the deck.
   */
   public Card drawCard() {
      if (currentDeck.isEmpty()) {
         shuffleDeck();
      }
      return currentDeck.pop();
   }
   
   /*
      Description
         - Shuffles the deck by randomly selecting cards from the current deck and adding them to the 
           discard pile.Then, it puts the cards from the discard pile back into the deck.
      Parameters
         - None
      Return
         - None
   */
   public void shuffleDeck() {
      Random random = new Random();
      while(!currentDeck.isEmpty()){
         int index = random.nextInt(currentDeck.size());
         discard.add(currentDeck.remove(index));
      }
      while(!discard.isEmpty()){
         currentDeck.push(discard.remove());
      }
   }
}