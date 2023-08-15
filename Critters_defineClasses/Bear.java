/*
   Description: 
   This is part of the Critters Lab, Lab2.
   This file will determine the look and behavior of the bears in the game,
   
   Instructions from the Lab:
   *Bear*
      - Constructor: public Bear(boolean polar)
      - getColor: Color.WHITE for a polar bear (when polar is true),
                  Color.Black otherwise (when polar is false)
      - toString: Should alternate on each different move between a slash character (/)
                  and a backslash character (\) starting with a slash.
      - getMove:  always infect if an enemy is in front
                  otherwise hop if possible
                  otherwise turn left.
*/

import java.awt.*;

public class Bear extends Critter {
   
   private boolean polar;
   private boolean slash; 
   
   public Bear(boolean polar){  
      this.polar = polar;
      this.slash = true;
   }
   
   
/*
   Description:
   Returns the color of the bear based on its polar attribute.
   If the bear is polar, it returns Color.WHITE, otherwise Color.BLACK.

   Parameters:
   None

   Returns:
   Color - the color of the bear
*/
   public Color getColor(){
      if (this.polar) {
         return Color.WHITE;
      }else {
         return Color.BLACK;
      }
   }
   
   
/*
   Description:
   Returns a string representation of the bear's appearance.
   The appearance alternates between a slash character ("/") and a backslash character ("\")
   on each different move, starting with a slash.

   Parameters:
   None

   Returns:
   String - the string representation of the bear's appearance
*/
   public String toString() {
      if(this.slash){
         return "/";
      }else {
         return "\\";
      }
   }
   
   
/*
   Description:
   Determines the next action for the bear based on the surrounding conditions.
   If an enemy critter is in front, it returns Action.INFECT to infect the enemy.
   If the front neighbor is an empty square, it returns Action.HOP to move forward.
   If none of the above conditions are met, it returns Action.LEFT to turn left.

   Parameters:
   - info: a CritterInfo object containing information about the bear's surrounding

   Returns:
   Action - the next action for the bear
*/
   public Action getMove(CritterInfo info){
      slash = ! slash;
      
      if (info.getFront() == Neighbor.OTHER){
         // Infect the critter when encountering a critter of another species
         return Action.INFECT; 
      } else if (info.getFront() == Neighbor.EMPTY){
         // Move forward one square in its current direction when the neighbor is an empty square
         return Action.HOP; 
      } else {
         // Turn left (rotate 90 degrees counter-clockwise) 
         return Action.LEFT;
      }
   }
}
