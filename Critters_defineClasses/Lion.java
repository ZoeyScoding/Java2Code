/*
   Description: 
   This is part of the Critters Lab, Lab2.
   This file will determine the look and behavior of the lions in the game,
   
   Instructions from the Lab:
   *Lion*
      - Constructor: public Lion()
      - getColor: Randomly picks one of three colors (Color.RED, Color.GREEN, Color.BLUE) 
                  and uses that color for three moves, then randomly picks one of those colors 
                  again for the next three moves, then randomly picks another one of those colors 
                  for the next three moves, and so on.
      - toString: "L"
      - getMove:  always infect if an enemy is in front
                  otherwise if a wall is in front or to the right, then turn left
                  otherwise if a fellow Lion is in front, then turn right
                  otherwise hop.
*/

import java.awt.*;
import java.util.Random;

public class Lion extends Critter {
   // Counter to keep track of the number of moves made by the lion
   private int movesCount = 0;
   // Variable to store the last chosen color
   private Color lastColor;

   public Lion() {
      // Initialize the color of the lion
      getColor();
      movesCount = 0;
   }

/*
   Description:
   Returns the color of the Lion based on its movement pattern.
   The Lion randomly picks one of three colors and uses that color for three moves,
   then randomly picks another color for the next three moves, and repeats.

   Parameters:
   None

   Returns:
   lastColor - the color of the Lion
*/
   public Color getColor() {    
      // If not a multiple of 3, return the last chosen color
      if (movesCount % 3 != 0) {
         return lastColor;
      }

      Random random = new Random();
      Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
      // Randomly select an index from 0 to 2
      int randomIndex = random.nextInt(colors.length);
      // Update the last chosen color
      lastColor = colors[randomIndex];
      return lastColor;
   }
   
/*
  Description:
  Returns a string representation of the Lion.

   Parameters:
   None

   Returns:
   String - the string representation of the Lion
*/
   public String toString() {
      return "L";
   }
   
/*
   Description:
   Determines the next action of the Lion based on the provided information.
   The Lion always infects if there's an enemy in front.
   If a wall is in front or to the right, it turns left.
   If a fellow Lion is in front, it turns right.
   Otherwise, it hops.
 
   Parameters:
   info-The information about the Lion's surroundings.
   
   Return:
   Action- The next action of the Lion.
 */

   public Action getMove(CritterInfo info){
      movesCount++;
      
      if (info.getFront() == Neighbor.OTHER){
         return Action.INFECT;
      } else if (info.getFront() == Neighbor.WALL || info.getRight() == Neighbor.WALL){
         return Action.LEFT;
      } else if (info.getFront() == Neighbor.SAME){
         return Action.RIGHT;
      } else {
         return Action.HOP;
      }
   }
}
