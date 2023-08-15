/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: July. 18th, 2023
   Subject: Assignment 2 Phone Book
   Class Description: This class serves as a manager for a phone book application. It 
                      handles the phonebook data using a linked list data structure and 
                      provides functionalities to interact with the phonebook through a 
                      text-based menu interface. The class allows users to perform 
                      various operations, including adding new records, deleting records, 
                      editing existing records, displaying all records, and formatting 
                      phone numbers.It also includes methods for input validation and 
                      handling user choices.
*/

import java.util.*;


public class PhonebookManager {
   // The head of the linked list that represents the phone book. It points to the 
   // first node in the linked list.
   private ListNode head;
   // A Scanner object used to read user input from the console. It allows the class 
   // to interact with the user for various phone book operations.
   private Scanner input;

   // Constructor to initialize the PhonebookManager with an empty linked list and a 
   // scanner object for user input.
   // Precondition: None.
   // Postcondition: The PhonebookManager is created with an empty linked list (head 
   // is set to null) and a Scanner object (input) for user input from the console.
   public PhonebookManager() {
      head = null;
      input = new Scanner(System.in);
   }


   // Add a New Record (using ListNode)
   // Precondition: None.
   // Postcondition: Prompts the user to enter details (first name, last name, phone 
   // number, address, and city) for a new record and validates the phone number 
   // format. If the new phone number is not a duplicate and is in the correct format, 
   // it creates a new Person object and adds it to the linked list at the specified 
   // position (beginning or end) based on user choice.
   public void addNewRecord() {
      // Get user input for new record details (first name, last name)
      System.out.print("Enter first name: ");
      String first = input.next();
      first = capitalize(first);

      System.out.print("Enter last name: ");
      String last = input.next();
      last = capitalize(last);
      
      // Flag to track if the phone number format is correct and not a duplicate.
      boolean isNumberCorrect = false;
      String number = "";

      // Validate the phone number format and prevent duplicates
      while (!isNumberCorrect) {
         System.out.print("Enter phone number (xxx-xxx-xxxx): ");
         number = input.next();
         // Check if the user wants to quit the record entry process
         if (number.equals("q")) {
            System.exit(0);
         }
         // Format the phone number to xxx-xxx-xxxx format
         number = formatNumber(number);
         // Check if the formatted number is empty (invalid format)
         if (number.isEmpty()) {
            // Prompt the user to enter phone number
            System.out.println("Incorrect number format. Try again.");
            continue;
         }
         // Check if the phone number already exists in the phone book
         if (checkIfPhoneNumberExists(number)) {
            System.out.println("Number already exists. Try again");
            System.out.println("You may also enter 'q' to quit.");
            continue;
         }

         // Validates all North American Phone standards
         // i.e. 123456789 OR 123-456-7890 OR 123.456.7890 OR 123 456 7890
         //      OR (123) 456 7890
         String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
         // Check if the number length or format is invalid
         if (number.length() != 12 || !number.matches(regex)) {
            System.out.println("Incorrect Number entered. Try again.");
            System.out.println("You may enter 'q' to quit or try again.");
         } else {
            // If the number is in the correct format and unique, set the flag to true
            isNumberCorrect = true;
         }
      }

      input.nextLine();
      // Prompt the user to enter the address and city
      System.out.print("Enter address: ");
      String address = input.nextLine();

      System.out.print("Enter city: ");
      String city = input.nextLine();
      
      // Create a new Person object with the user provided data
      Person individual = new Person(first, last, number, address, city);

      int choice = 0;
      boolean validChoice = false;

      // Add the new record to the specific position based on user choice
      //    - 1 is to put the record at the beginning of the phone book
      //    - 2 is to put the record at the end of the phone book
      while (!validChoice) {
         try {
            System.out.println("Where do you want to add the record?");
            System.out.println("1. Beginning");
            System.out.println("2. End");

            // Read the user's choice and consume the newline character
            choice = input.nextInt();
            input.nextLine(); 
            // Check if the choice is valid (1 or 2)
            if (choice == 1 || choice == 2) {
               validChoice = true;
            } else {
               System.out.println("Invalid input. Please enter a valid choice (1 or 2).");
            }

         } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid choice (1 or 2).");
            input.nextLine(); 
         }
      }

      // Create a new ListNode with the given Person data
      ListNode newNode = new ListNode(individual);
      // Add the new node to the specified position based on user choice
      addNodeBasedOnChoice(newNode, choice);
      // Display the details of the newly added record
      displayCurrentRecord(individual);
   }

   // Delete a Record
   // Precondition: 
   //    - The phone book is not empty.
   //    - The head of the linked list and the scanner object must be initialized.
   // Postcondition: 
   //    - Displays all records in the phone book and prompts the user to enter first 
   //      name and last name to select a record for deletion. If the selected record 
   //      exists, it will be removed.
   public void deleteRecord() {
      // Check if the phone book is empty
      if (head == null) {
         System.out.println("Phonebook is empty.You must add at least one record.");
         return;
      }

      // Show all records before deletion
      showAllRecords();

      // Get user input for selecting a record to delete
      Person selectedPerson = selectRecord();

      // If a valid record is selected (selectedPerson is not null)
      if (selectedPerson != null) {
         // Get the index of the selected Person object in the phone book
         int index = getIndexOfPerson(selectedPerson);
         // Remove the selected record from the linked list.
         // The method returns the updated head of the linked list, so we update the 
         // head variable accordingly.
         head = removeAtIndex(head, index);
         // Print a message indicating that the record is removed.
         System.out.println("Record Removed");
      }
   }

   // Display All Records
   // Precondition: 
   //    - The phone book is not empty.
   //    - The head of the linked list and the scanner object must be initialized.
   // Postcondition: 
   //    - Displays all records in the phone book, including first name, last name, 
   //      phone number, address, and city of each person in a formatted table.
   public void showAllRecords() {
      // Check if the phone book is empty (i.e., the linked list is empty).
      if (head == null) {
         System.out.println("Phonebook is empty.");
         return;
      }
      // Initialize a current variable to point to the head of the linked list.
      ListNode current = head;
      // Display all records in a formatted table
      System.out.print("First Name          Last Name           Phone Number       ");
      System.out.println("Address             City            ");
      System.out.print("------------------- ------------------- ------------------ ");
      System.out.println("------------------- ------------------");
      // Iterate through the linked list to display each record's details in the table format.
      while (current != null) {
         Person individual = current.getPerson();
         // Print the first name
         System.out.print(individual.getFirstName());
         for (int i = individual.getFirstName().length(); i < 20; i++) {
            System.out.print(" ");
         }
         // Print the last name
         System.out.print(individual.getLastName());
         for (int i = individual.getLastName().length(); i < 20; i++) {
            System.out.print(" ");
         }
         // Print the phone number
         System.out.print(individual.getPhoneNumber());
         for (int i = individual.getPhoneNumber().length(); i < 20; i++) {
            System.out.print(" ");
         }
         // Print the address
         System.out.print(individual.getAddress());
         for (int i = individual.getAddress().length(); i < 20; i++) {
            System.out.print(" ");
         }
         // Print the city.
         System.out.println(individual.getCity());
         System.out.println();

         current = current.getNext();
      }

   }

   // Display Current Record
   // Precondition: 
   //    - A valid Person object is passed as input.
   // Postcondition: 
   //    - Displays the details of the given Person object, including first name, last 
   //      name, phone number, address, and city.
   public void displayCurrentRecord(Person person) {
      System.out.println("Current record is: " +
         person.getFirstName() + " " +
         person.getLastName() + " " +
         person.getPhoneNumber() + " " +
         person.getAddress() + " " +
         person.getCity());
   }

   // Check If Phone Number Exists
   // Precondition: 
   //    - A valid phone number (in format xxx-xxx-xxxx) is passed as input.
   // Postcondition: 
   //    - Returns true if the phone number already exists in the phone book.
   //    - Otherwise, returns false.
   public boolean checkIfPhoneNumberExists(String phoneNumber) {
      ListNode current = head;
      // Iterate through the linked list to check each person's phone number.
      while (current != null) {
         Person individual = current.getPerson();
         // Compare the phone number of the current person with the provided phone 
         // number.
         if (individual.getPhoneNumber().equals(phoneNumber)) {
            return true;
         }

         current = current.getNext();
      }

      return false;
   }

   // Create Correct Phone Format
   // Precondition: 
   //    - The start and end indices are valid for the char arrays newNum and num.
   //    - The pass value is either 1, 2, or 3.
   // Postcondition: 
   //    - Updates the newNum array with the correct phone number format by copying
   //      characters from the num array based on the specified start, end, and pass
   //      values.
   public void createCorrectPhoneFormat(
      int start, int end, char[] newNum, char[] num, int pass) {
      // Iterate through the characters of the original phone number (num) from the 
      // start index to the end index.
      for (int i = start; i < end; i++) {
         int j = 0;

         if (pass == 2) {
            j = 1;
         } else if (pass == 3) {
            j = 2;
         }

         newNum[i + j] = num[i];
      }
      // Add the appropriate separator '-' based on the pass value.
      if (pass == 1) {
         newNum[end] = '-';
      } else if (pass == 2) {
         newNum[end + 1] = '-';
      }
   }


   // Get the index of a specific Person object in the phone book
   // Precondition: 
   //    - A valid Person object (selectedPerson) is passed as input.
   // Postcondition: 
   //    - Returns the index of the selectedPerson object in the phone book's linked
   //      list.
   //    - If the object is not found, returns -1.
   public int getIndexOfPerson(Person selectedPerson) {
      // Initialize an index variable to keep track of the current position
      int index = 0;
      // Initialize a current variable to point to the head
      ListNode current = head;
      
      // Iterate through the linked list to find a match for the first name and last 
      // name of the selectedPerson.
      while (current != null) {
         // Get the Person object associated with the current node.
         Person person = current.getPerson();

         if (person.getFirstName().equalsIgnoreCase(selectedPerson.getFirstName()) &&
            person.getLastName().equalsIgnoreCase(selectedPerson.getLastName())) {
            return index;
         }

         current = current.getNext();
         // Increment the index to move to the next position in the linked list.
         index++;
      }

      return -1;
   }

   // Select a Record based on user-provided first and last names.
   // Precondition: 
   //    - The phone book may not be empty. 
   //    - The head of the linked list and the scanner object must be initialized.
   // Postcondition: 
   //    - Prompts the user to enter first name and last name to select a specific 
   //      record.
   //    - If a record with matching first and last names is found in the phone 
   //      book, returns the corresponding Person object. If no matches are found, 
   //      returns null.
   public Person selectRecord() {

      System.out.print("Enter first name: ");
      String first = input.next();
      first = capitalize(first);

      System.out.print("Enter last name: ");
      String last = input.next();
      last = capitalize(last);
      
      // Create a Person object with the provided first and last names and empty 
      // phone number, address, and city.
      Person selectedPerson = new Person(first, last, "", "", "");
      // Initialize a current variable to point to the head of the linked list.
      ListNode current = head;
      
      // Iterate through the linked list to find a match for the selected first 
      // and last names.
      while (current != null) {
         // Get the Person object associated with the current node in the linked list.
         Person person = current.getPerson();
         // Check if the first name and last name of the current Person object match 
         // the selected names.
         if (person.getFirstName().equalsIgnoreCase(selectedPerson.getFirstName()) &&
            person.getLastName().equalsIgnoreCase(selectedPerson.getLastName())) {
            return person;
         }
         // Move to the next node in the linked list.
         current = current.getNext();
      }
      // If no matches are found, print a message indicating that no matches were 
      // found and return null.
      System.out.println("No matches. Try again.");
      return null;
   }

   // Capitalize a String
   // Precondition: 
   //    - A non-null string (s) is passed as input.
   // Postcondition: 
   //    - Returns the input string with its first letter capitalized.
   public String capitalize(String s) {
      return s.substring(0, 1).toUpperCase() + s.substring(1);
   }

   // Format a Phone Number
   // Precondition: 
   //    - A valid phone number (in any format) is passed as input.
   // Postcondition: 
   //    - Returns the phone number in the format xxx-xxx-xxxx if it matches any    
   //      North American phone standards. Otherwise, returns the original phone 
   //      number.
   public String formatNumber(String phoneNumber) {
      // Remove all non-digit characters from the input phoneNumber
      phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
      // Check if the resulting phoneNumber contains exactly 10 digits
      if (phoneNumber.matches("\\d{10}")) {
         // If phoneNumber has 10 digits, return the formatted version with '-' 
         // separators.
         return String.format("%s-%s-%s", phoneNumber.substring(0, 3),
            phoneNumber.substring(3, 6),
            phoneNumber.substring(6, 10));
      }
      // If phoneNumber does not match the 10-digit pattern, return the original 
      // phoneNumber as it is.
      return phoneNumber;
   }

   // Edit an Existing Record
   // Precondition: 
   //    - The phone book is not empty.
   // Postcondition: 
   //    - Allows the user to edit an existing record from the phone book.
   //    - The user can choose to change the first name, last name, phone number, 
   //      address, or city.
   //    - The record is selected based on user-provided first and last names.
   //    - After editing, the updated record is displayed.
   public void editRecord() {
      if (head == null) {
         System.out.println(
            "Phonebook is empty.You must add at least one record.");
         return;
      }

      showAllRecords();
      // Prompt the user to select a record
      Person selectedPerson = selectRecord();

      // If a valid record is selected, prompt the user for the field to edit
      if (selectedPerson != null) {
         System.out.println("What do you want to change?");
         System.out.println("1. First Name");
         System.out.println("2. Last Name");
         System.out.println("3. Phone Number");
         System.out.println("4. Address");
         System.out.println("5. City");
         System.out.println("6. Cancel");

         int choice = input.nextInt();
         input.nextLine();

         // Switch case based on the user's choice
         switch (choice) {
            case 1:
               System.out.print("Enter new first name: ");
               String newFirstName = input.next();
               newFirstName = capitalize(newFirstName);
               selectedPerson.setFirstName(newFirstName);
               break;
            case 2:
               System.out.print("Enter new last name: ");
               String newLastName = input.next();
               newLastName = capitalize(newLastName);
               selectedPerson.setLastName(newLastName);
               break;
            case 3:
               System.out.print("Enter new phone number: ");
               String newNumber = input.next();
               newNumber = formatNumber(newNumber);
               selectedPerson.setPhoneNumber(newNumber);
               break;
            case 4:
               System.out.print("Enter new address: ");
               String newAddress = input.nextLine();
               selectedPerson.setAddress(newAddress);
               break;
            case 5:
               System.out.print("Enter new city: ");
               String newCity = input.nextLine();
               selectedPerson.setCity(newCity);
               break;
            case 6:
               return;
            default:
               System.out.println("Invalid choice. Please try again.");
         }

         System.out.println("Record updated successfully.");
         displayCurrentRecord(selectedPerson);
      }
   }
   
   // Remove the node at the given index from the linked list.
   // Precondition:
   //    - The method is called with the head of the linked list.
   //    - The index of the node to be removed.
   // Postcondition:
   //    - If the head is null (i.e., the linked list is empty), return null, as there 
   //      is nothing to remove.
   //    - If the index is 0, return the second node (head.getNext()) as the new head, 
   //      effectively removing the first node.
   //    - If the index is greater than 0, traverse the linked list to find the node at 
   //      the index just before the desired node to be removed.
   //    - Adjust the "next" reference of the previous node to point to the node after 
   //      the desired node, effectively bypassing the desired node and removing it.
   //    - If the index is out of bounds, and the current node is null (reached the 
   //      end of the list), no changes are made, and the original head is returned.
   private ListNode removeAtIndex(ListNode head, int index) {
      if (head == null) {
         return null;
      }

      if (index == 0) {
         return head.getNext();
      }

      ListNode current = head;
      // Traverse the linked list to find the node at the index just before the desired 
      // node to be removed.
      // The loop condition ensures that we stop at the previous node (index - 1) or the 
      // last node (current.getNext() != null).
      for (int i = 0; i < index - 1 && current.getNext() != null; i++) {
         current = current.getNext();
      }
      // Check if the current node is not null and the node after the current node is not null.
      if (current.getNext() != null) {
         // Adjust the "next" reference of the previous node to point to the node after 
         // the desired node, effectively removing the desired node from the list.
         current.setNext(current.getNext().getNext());
      }
      // Return the head of the modified linked list after removing the node or the 
      // original head if the index is out of bounds.
      return head;
   }

   // Find the last node in the linked list.
   // Precondition: 
   //    - The head of the linked list is passed as input (head).
   // Postcondition: 
   //    - If the linked list is empty (head is null), the method returns null, as  
   //      there is no last node. Otherwise, it traverses the linked list starting  
   //      from the head node until it reaches the last node (the node whose next  
   //      reference is null) and returns that last node.
   private ListNode findLastNode(ListNode head) {
      if (head == null) {
         return null;
      }
      // Start traversing the linked list from the head node.
      ListNode current = head;

      while (current.getNext() != null) {
         current = current.getNext();
      }
      // Return the last node in the linked list.
      return current;
   }

   // Add a new ListNode at the end of the linked list.
   // Precondition: 
   //    - A valid ListNode object (newNode) is passed as input.
   // Postcondition: 
   //    - If the linked list is empty (head is null), the head is set to the newNode, 
   //      making it the only node in the linked list. If the linked list is not empty, 
   //      it finds the last node of the current linked list using the findLastNode() 
   //      method and sets the next node of the last node to the newNode, effectively  
   //      adding the newNode at the end of the linked list.
   private void addNodeAtEnd(ListNode newNode) {
      if (head == null) {
         head = newNode;
      } else {
         ListNode lastNode = findLastNode(head);
         lastNode.setNext(newNode);
      }
   }

   // Add a new ListNode at the beginning of the linked list.
   // Precondition: 
   //    - A valid ListNode object (newNode) is passed as input.
   // Postcondition: 
   //    - If the linked list is empty (head is null), the head is set to the newNode, 
   //      making it the only node in the linked list. If the linked list is not empty, 
   //      the newNode is set as the new head, and its next node becomes the previous  
   //      head,effectively adding the newNode to the beginning of the linked list.
   private void addNodeAtBeginning(ListNode newNode) {
      if (head == null) {
         // If the linked list is empty, set the newNode as the head node.
         head = newNode;
      } else {
         newNode.setNext(head);
         head = newNode;
      }
   }

   // Add a new node to the linked list based on user choice.
   // Precondition: 
   //    - The choice is either 1 or 2.
   // Postcondition: 
   //    - Adds the given newNode to the linked list at the beginning (choice = 1) or 
   //      end (choice = 2).
   private void addNodeBasedOnChoice(ListNode newNode, int choice) {
      if (choice == 1) {
         addNodeAtBeginning(newNode);
      } else {
         addNodeAtEnd(newNode);
      }
   }


}