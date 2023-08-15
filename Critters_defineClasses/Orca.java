/*
   Description: 
   This is part of the Critters Lab, Lab2.
   This file will determine the look and behavior of the Orca in the game,
   
   Instructions from the Lab:
   *Orca*
      - Constructor: public Orca()
      - getColor: Returns the color black when the Orca is hungry, return the color white when it's not hungry.
      - toString: Displays "~~~" when the Orca is hungry and "OOOOO" when it's not hungry.
      - getMove:  The Orca always infects if there's an enemy in front.
                  If the front is empty, it hops.
                  If there's an enemy on the right, it turns right.
                  If there's an enemy on the left, it turns left.
                  Otherwise, it turns left (rotate 90 degrees counter-clockwise) as the default action
*/

import java.awt.*;

public class Orca extends Critter {
   private boolean hungry;

   public Orca() {
      // Initialize the Orca as hungry
      hungry = true;
   }
   
/*
   Description:
   Returns the color of the Orca based on its hunger state.
   If the Orca is hungry, it returns Color.BLACK, otherwise Color.WHITE.

   Parameters:
   None

   Returns:
   Color - the color of the Orca
*/
   public Color getColor(){           
      if (hungry){
         return Color.BLACK;
      } else {
         return Color.WHITE;
      }
   }
   
/*
   Description:
   Returns a string representation of the Orca based on its hunger state.
   If the Orca is hungry, it returns "~~~", otherwise "OOOOO".

   Parameters:
   None

   Returns:
   String - the string representation of the Orca
*/
   public String toString() {
      if (hungry) {
         return "~~~";
      } else {
         return "OOOOO";
      }
   }
   
/*
   Description:
   Determines the next action for the Orca based on the surrounding conditions.
   If an enemy critter is in front, it returns Action.INFECT to infect the enemy.
   If the front neighbor is an empty square, it returns Action.HOP to move forward.
   If there's an enemy on the right, it returns Action.RIGHT to turn right.
   If there's an enemy on the left, it returns Action.LEFT to turn left.
   If none of the above conditions are met, it returns Action.LEFT as the default action.

   Parameters:
   - info: a CritterInfo object containing information about the Orca's surroundings

   Returns:
   Action - the next action for the Orca
*/
   public Action getMove(CritterInfo info){
      if (info.getFront() == Neighbor.OTHER){
         hungry = false;
         // Infect the critter when encountering a critter of another species
         return Action.INFECT;
      } else if (info.getFront() == Neighbor.EMPTY){
         return Action.HOP;
      } else if (info.getRight() == Neighbor.OTHER) {
         return Action.RIGHT;
      } else if (info.getLeft() == Neighbor.OTHER) {
         return Action.LEFT;
      } else {
         return Action.LEFT; 
      }
   }
}
