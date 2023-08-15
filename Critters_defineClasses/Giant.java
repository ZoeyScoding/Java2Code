/*
   Description: 
   This is part of the Critters Lab, Lab2.
   This file will determine the look and behavior of the Giant in the game,
   
   Instructions from the Lab:
   *Giant*
      - Constructor: public Giant()
      - getColor: Color.GRAY
      - toString: "fee" for 6 moves, then "fie" for 6 moves, then "foe" for 6 moves, 
                  then "fum" for 6 moves, then repeat.
      - getMove:  always infect if an enemy is in front
                  otherwise hop if possible
                  otherwise turn right.
*/

import java.awt.*;

public class Giant extends Critter {
   // Counter to keep track of the number of moves made by the lion
   private int movesCount;
   // Variable to store the last displayed giantName
   private String lastGiant;
   
   public Giant() {
      // Initialize the lastGiant variable
      toString();
      movesCount = 0;
   }
   
/*
   Description:
   Returns the color of the Giant.

   Parameters:
   None

   Returns:
   Color.GRAY - the color of the Giant
*/
   public Color getColor(){     
      return Color.GRAY;
   }
   
/*
   Description:
   Returns a string representation of the Giant."fee" for 6 moves, then "fie" for 6 moves, then "foe" for 6 moves,
   then "fum" for 6 moves, then repeat.

   Parameters:
   None

   Returns:
   lastGiant - the string representation of the Giant
*/
   public String toString() {
      String[] giantName = {"fee", "fie", "foe", "fum"};
      // Calculate the index based on the movesCount
      int index = movesCount % 24 / 6;
      
      for (int i = 0; i < giantName.length; i++){
         if(i == index){
            // Update the last displayed giantName
            lastGiant = giantName[i];
            break;
         }
      }
      return lastGiant;
   }
   
   
/*
   Description:
   Determines the next action for the Giant based on the surrounding conditions.
   If an enemy critter is in front, it returns Action.INFECT to infect the enemy.
   If the front neighbor is an empty square, it returns Action.HOP to move forward.
   If none of the above conditions are met, it returns Action.RIGHT to turn right.

   Parameters:
   - info: a CritterInfo object containing information about the Giant's surroundings

   Returns:
   Action - the next action for the Giant
*/
   public Action getMove(CritterInfo info){
      // Increment the moves count for every move
      movesCount++;
      
      if (info.getFront() == Neighbor.OTHER){
         return Action.INFECT;
      } else if (info.getFront() == Neighbor.EMPTY){
         return Action.HOP;
      } else {
         return Action.RIGHT;
      }
   }
}
