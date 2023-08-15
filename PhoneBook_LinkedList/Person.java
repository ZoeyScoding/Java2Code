/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: July. 18th, 2023
   Subject: Assignment 2 Phone Book
   Class Description: This class represents a data model for storing information 
                      about an individual. It serves as a blueprint to create objects
                      that hold attributes such as first name, last name, phone number,
                      address, and city for a person. 
*/

public class Person {
   // Stores the first name of the person.
   private String firstName;
   // Stores the last name of the person.
   private String lastName;
   // Stores the phone number of the person.
   private String phoneNumber;
   // Stores the address of the person.
   private String address;
   // Stores the city of the person.
   private String city;

   // Constructor to initialize the person's attributes.
   // Precondition:
   //    - All input parameters (firstName, lastName, phoneNumber, address, and city) 
   //      are non-null.
   // Postcondition:
   //    - Creates a new Person object with the provided attribute values.
   public Person(
      String firstName, String lastName, String phoneNumber, String address, String city) 
      {
      this.firstName = firstName;
      this.lastName = lastName;
      this.phoneNumber = phoneNumber;
      this.address = address;
      this.city = city;
   }

   // Getter method for retrieving the first name.
   // Precondition: None
   // Postcondition:
   //    - Returns the first name of the person.
   public String getFirstName() {
      return firstName;
   }

   // Setter method for setting the first name.
   // Precondition:
   //    - The input parameter (firstName) is non-null.
   // Postcondition:
   //    - Sets the first name of the person to the provided value.
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   // Getter method for retrieving the last name.
   // Precondition: None
   // Postcondition:
   //    - Returns the last name of the person.
   public String getLastName() {
      return lastName;
   }

   // Setter method for setting the last name.
   // Precondition:
   //    - The input parameter (lastName) is non-null.
   // Postcondition:
   //    - Sets the last name of the person to the provided value.
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   // Getter method for retrieving the phone number.
   // Precondition: None
   // Postcondition:
   //    - Returns the phone number of the person.
   public String getPhoneNumber() {
      return phoneNumber;
   }

   // Setter method for setting the phone number.
   // Precondition:
   //    - The input parameter (phoneNumber) is non-null.
   // Postcondition:
   //    - Sets the phone number of the person to the provided value.
   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   // Getter method for retrieving the address.
   // Precondition: None
   // Postcondition:
   //    - Returns the address of the person.
   public String getAddress() {
      return address;
   }

   // Setter method for setting the address.
   // Precondition:
   //    - The input parameter (address) is non-null.
   // Postcondition:
   //    - Sets the address of the person to the provided value.
   public void setAddress(String address) {
      this.address = address;
   }
   
   // Getter method for retrieving the city.
   // Precondition: None
   // Postcondition:
   //    - Returns the city of the person.
   public String getCity() {
      return city;
   }

   // Setter method for setting the city.
   // Precondition:
   //    - The input parameter (city) is non-null.
   // Postcondition:
   //    - Sets the city of the person to the provided value.
   public void setCity(String city) {
      this.city = city;
   }

   // Returns a string representation of the person's information.
   // Precondition: None
   // Postcondition:
   //    - Returns a string containing the first name, last name, phone number, 
   //      address, and city of the person.
   public String toString() {
      return firstName + " " + lastName + " - " + 
             phoneNumber + " - " + address + " - " + city;
   }
}