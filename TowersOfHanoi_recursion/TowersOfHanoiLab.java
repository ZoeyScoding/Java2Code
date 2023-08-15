/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: August. 1st, 2023
   Subject: Lab 5 Towers of Hanoi
   Purpose: The program is to solve the Towers of Hanoi puzzle for a given number of 
            disks using a recursive algorithm. The code initializes three towers with 
            disks stacked in decreasing order, where the largest disk is at the bottom 
            and the smallest disk is at the top. It then prints the initial state of 
            the towers and proceeds to solve the puzzle by moving the disks from the 
            source tower (Tower 1) to the target tower (Tower 3) with the help of an 
            auxiliary tower (Tower 2). After each move, it prints the updated state of 
            the towers, showing the disks' positions on each tower, until all the disks
            are successfully moved to the target tower.

*/

import java.util.*;


public class TowersOfHanoiLab {

   public static void main(String[] args) {
      // Number of disks
      int n = 4;

      System.out.println("Initial Tower State:");
      // Create a List of Lists to represent the three towers and disks. Each inner list will
      // represent a tower, and the outer list will hold all three towers.
      List < List<Integer>> towers = new ArrayList<>();

      // Initialize each tower as an empty list
      for (int i = 0; i < 3; i++) {
         towers.add(new ArrayList<>());
      }

      // Set up the initial state of the towers by adding disks to the first tower (Tower 1)
      initializeTowers(towers, n);
      // Display the arrangement of disks on each tower
      printTowerState(towers);

      System.out.println("The moves are:");

      // Solve the Towers of Hanoi puzzle using the recursiveHanoi method
      recursiveHanoi(n, 0, 2, 1, towers);
   }

   /*
      Moves disks from the source tower to the target tower using an auxiliary tower.
      Parameter:
         - n: The number of disks to move. (Precondition: n must be a positive integer)
         - fromTower: The index of the source tower (0-based index). (Precondition: fromTower
           must be 0, 1, or 2)
         - toTower : The index of the target tower (0-based index). (Precondition: toTower
           must be 0, 1, or 2)
         - auxTower : The index of the auxiliary tower (0-based index). (Precondition: 
           auxTower must be 0, 1, or 2 and different from fromTower and toTower)
         - towers : The List of Lists representing the towers and disks. (Precondition: 
           towers must be initialized with three empty lists)
   */

   public static void recursiveHanoi(int n, int fromTower, int toTower, int auxTower, 
                                     List < List<Integer>> towers) 
      {
      // Base case: If there is only one disk to move, move it directly from the source tower 
      // to the target tower
      if (n == 1) {
         moveDisk(fromTower, toTower, towers);
         return;
      }
      // Move n-1 disks from the source tower to the auxiliary tower using the target tower 
      // as the auxiliary tower
      recursiveHanoi(n - 1, fromTower, auxTower, toTower, towers);
      // Move the remaining largest disk from the source tower to the target tower
      moveDisk(fromTower, toTower, towers);

      recursiveHanoi(n - 1, auxTower, toTower, fromTower, towers);
   }

   /*
     Moves a disk from the source tower to the target tower.
     Parameter:
        - fromTower : The index of the source tower (0-based index). (Precondition: fromTower
          must be 0, 1, or 2)
        - toTower: The index of the target tower (0-based index). (Precondition: toTower must 
          be 0, 1, or 2)
        - towers : The List of Lists representing the towers and disks. (Precondition: towers 
          must be initialized with three non-null lists)
   */

   public static void moveDisk(int fromTower, int toTower, List < List<Integer>> towers) {
      // Get the top disk from the source tower
      int disk = towers.get(fromTower).remove(towers.get(fromTower).size() - 1);
      // Add the disk to the top of the target tower
      towers.get(toTower).add(disk);
      // Display the updated state of the towers after the move
      printTowerState(towers);
      System.out.println();
   }

   /*
     Initializes the towers with the specified number of disks.
     Parameter:
        - towers: The List of Lists representing the towers and disks. (Precondition: towers
          must be initialized with three empty lists)
        - n : The number of disks to initialize the towers with. (Precondition: n must be a 
          non-negative integer)
   */

   public static void initializeTowers(List < List<Integer>> towers, int n) {
      // Add disks to the first tower (Tower 1) in decreasing order of size, starting 
      // from n down to 1
      for (int i = n; i >= 1; i--) {
         towers.get(0).add(i);
      }
   }

   /*
     Prints the current state of the towers.
     Parameter:
        - towers: The List of Lists representing the towers and disks. (Precondition: towers
          must be initialized with three non-null lists)
   */

   public static void printTowerState(List < List<Integer>> towers) {
      // Iterate through the towers and print the current state of each tower
      for (int i = 0; i < 3; i++) {
         System.out.print("Tower " + (i + 1) + ": ");
         System.out.println(towers.get(i));
      }
   }
}