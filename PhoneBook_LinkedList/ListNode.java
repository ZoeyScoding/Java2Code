/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: July. 18th, 2023
   Subject: Assignment 2 Phone Book
   Class Description: This class create a basic building block for a singly linked list,
                      where each node holds a Person object and a reference to the next
                      node. This way, multiple ListNode objects can be linked together 
                      to form a dynamic data structure that can efficiently store and 
                      manage a collection of Person records.
*/



public class ListNode {
   // Stores the data of a person associated with the current node in the linked list
   // The data involves first name, last name, phone number, address, and city.
   private Person person;
   // Store the next node after the current node. 
   // If the next variable is null, it means the current node is the last node in the 
   // linked list.
   private ListNode next;

   // Constructor to create a new ListNode with the provided Person data.
   // Precondition: A valid Person object (person) is passed as input.
   // Postcondition: A new ListNode is created with the provided Person data, and the 
   // 'next' reference is set to null, indicating it's the last node in the list.
   public ListNode(Person person) {
      this.person = person;
      this.next = null;
   }

   // Accessor (getter) method to retrieve the Person data associated with the current 
   // node in the linked list.
   // Precondition: None.
   // Postcondition: Returns the Person data of the current node.
   public Person getPerson() {
      return person;
   }

   // Mutator (setter) method to set the Person data of the current node in the linked 
   // list.
   // Precondition: A valid Person object (person) is passed as input.
   // Postcondition: The Person data of the current node is updated with the provided 
   // Person object (person).
   public void setPerson(Person person) {
      this.person = person;
   }

   // Accessor (getter) method to retrieve the reference to the next node in the linked 
   // list.
   // Precondition: None.
   // Postcondition: Returns the reference to the next node after the current node. If 
   // there's no next node (current node is the last node), it returns null.
   public ListNode getNext() {
      return next;
   }

   // Mutator (setter) method to set the reference to the next node in the linked list.
   // Precondition: A valid ListNode object (next) is passed as input.
   // Postcondition: The reference to the next node after the current node is updated 
   // with the provided ListNode object (next). If the provided ListNode object is 
   // null, it indicates that the current node is the last node in the list.
   public void setNext(ListNode next) {
      this.next = next;
   }
}