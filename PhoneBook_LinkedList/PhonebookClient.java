/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: July. 18th, 2023
   Subject: Assignment 2 Phone Book
   Purpose: The phonebook is implemented using a linked list data structure, and the 
            program handles input validation for phone number formats and duplicate 
            phone numbers. Users can interact with the phonebook through a simple 
            text-based menu interface, including viewing all records, adding new 
            records, deleting a record and edit the information. The basic phone 
            book information involves first and last name, phone number, address 
            and city. 
*/

import java.util.*;


public class PhonebookClient {

   public static void main(String[] args) {

      PhonebookManager phonebookManager = new PhonebookManager();
      Scanner input = new Scanner(System.in);
      String choice;

      displayMenu();
      
      // Continuously loop until user chooses to quit (q)
      while (input.hasNext()) {
         choice = input.next();

         switch (choice) {
            // Quit the program if users select q
            case "q":
               System.exit(-1);
               break;
            // View all records in the phonebook if users select v
            case "v":
               phonebookManager.showAllRecords();
               break;
            // Delete a record from the phonebook if users select d
            case "d":
               phonebookManager.deleteRecord();
               break;
            // Edit an existing record in the phonebook if users select e
            case "e":
               phonebookManager.editRecord();
               break;
            // Add a new record to the phonebook if users select a
            case "a":
               phonebookManager.addNewRecord();
               break;

            default:
               System.out.println("The option you've selected is not available. " +
                  "Please choose from the available options.");
               break;
         }
         // Display the menu again to prompt for next action
         displayMenu();
      }

   }

   // Display the menu to introduce the basic functions
   public static void displayMenu() {
      System.out.println("Welcome to PhoneBook:\n" +
         "    v     View all records\n" +
         "    a     Add a new record\n" + 
         "    d     Delete a record\n" +
         "    e     Edit a record\n" +
         "    q     Quit\n");
      System.out.println("Enter a command from the list above (q to quit): ");
   }
}