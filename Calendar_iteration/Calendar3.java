/*
   Programmer: Ziwei Shen
   Class: CS 145
   Date: August. 14th, 2023
   Subject: Assignment 3 Calendar
   Purpose: This program is to create a simple text-based calendar application. It allows the 
            user to interactively navigate and view calendars for different months, highlight 
            specific days, add events to specific dates, and print the calendar content to a file.
*/


import java.util.*;
import java.io.*;

public class Calendar3 {
   public static void main(String[] args)  throws FileNotFoundException{

      Scanner input = new Scanner(System.in);
      Calendar calendar = Calendar.getInstance();
      
      // There are 2 phases:
      //  - Before calling drawCalender: The month to be printed, it will be figured out by 
      //    Command Parser logic
      //  - After calling drawCalender: Then month most recently printed, will be start point 
      //    of next loop
      int month = 0;
      // There are 2 phases:
      //  - Before calling drawCalender: The day to be printed, it will be figured out by 
      //    Command Parser logic
      //  - After calling drawCalender: Then month most recently printed, will be start 
      //    point of next loop
      int day = 0;
      // The day user defined, which determines if an asterisk (*) should be added on a 
      // specific date
      boolean highlight;
      // Set to true if it's a legal command to draw a calendar. The legal command includes:
      //  - Input is e or t
      //  - Input is p or n, and e/t command has been called before
      boolean shouldDraw;

      String[][] eventArray = new String[12][31];
      eventArray = readFile(eventArray);

      // Determine whether to continue executing the loop or exit the program based on the 
      // user's input.
      //  - "q" to quit the program
      //  - "n" to display the next month
      //  - "p" to display the previous month
      //  - "e" for the user to enter a date and display the calendar for that month
      //  - "t" to get today's date and display a calendar for this month
      //  - "ev" to get a event planning action from user's input
      //  - "fp" to print a file of a given month

      // The variable of shouldContinue is used for determining whether to continue the following 
      // loop, the condition includes:
      //  - if true, then continuing the loop and displaying Menu
      //  - if false, then quiting the program and not displaying Menu anymore
      boolean shouldContinue = true;
      while (shouldContinue) {
         displayMenu();
         String userInput = input.nextLine().toLowerCase();
         highlight = false;
         shouldDraw = false;

         // If input is "q", finishing the program 
         if (userInput.equals("q")) {
            shouldContinue = false;
            System.out.println("Program finished.");
         // If input is "e", allow users to input a data they select and display it
         } else if (userInput.equals("e")) {
            // Prompt user to input a date
            String date = getInput();
            
            day = dayFromDate(date);
            month = monthFromDate(date);   
            highlight = true;     
            shouldDraw = true;          
                                      
         // Logic for displaying today's calendar
         } else if (userInput.equals("t")) {
            Date currentDate = new Date();
            
            calendar.setTime(currentDate);
            
            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DATE);

            System.out.println("today: " + month + "/" + day);
            highlight = true;    
            shouldDraw = true;   
   
         // Logic for displaying previous month
         } else if (userInput.equals("p")) {
            if(month == 0) {
               System.out.println("Please select e or t firstly");
            } else {
               month -= 1;
               if (month == 0) {
                  month = 12;
               }
  
               System.out.println("previoius month " + month);
               highlight = false;  
               shouldDraw = true;  
            }
         
         // Logic for displaying next month
         } else if (userInput.equals("n")) {
            if (month == 0) {
               System.out.println("Please select e or t firstly");
            } else {
               month = (month % 12) + 1;            
               System.out.println("next month " + month);
               highlight = false;   
               shouldDraw = true;  
            }
            
         // Logic for getting a event planning action from user's input
         } else if (userInput.equals("ev")) {
            eventArray = eventPlanning(eventArray);
            if (eventArray != null) {
               System.out.println("Successfully created a new event");
            }

         // Logic for print a file of a given month
         } else if (userInput.equals("fp")) {
            printFile(eventArray);
            System.out.println("Successfully printed the file");
  
         // If user input is not belong to character we defined, promp users to enter the right one
         } else {
             System.out.println("Invalid command. Please enter e, t, p, n, ev, fp, or q.");
         }

         // Check if shouldDraw is true to determine if the calendar should be drawn
         if (shouldDraw == true) {
            // drawCalendar(month, day, highlight);
            try {
               drawCalendar(month, day, highlight, eventArray);
            } catch (FileNotFoundException e) {
               // Handle the exception (e.g., print an error message)
               e.printStackTrace();
            }
         }
         
      }
   }

   /*
     Displays the menu options for the calendar program.    
   */
   public static void displayMenu() 
   {
      System.out.println("Please type a command");
      System.out.println("\t\"e\" to enter a date and display the corresponding calendar");
      System.out.println("\t\"t\" to get today's date and display today's calendar");
      System.out.println("\t\"n\" to display the next month");
      System.out.println("\t\"p\" to display the previous month");
      System.out.println("\t\"ev\" to get a event planning action");
      System.out.println("\t\"fp\" to print a file of a given month");
      System.out.println("\t\"q\" to quit the program");
  }
  
   
   /*
      Prompts the user to enter a date and validates the input. 
      Return
         - date : the valid date entered by the user (in MM/DD format).
   */
   public static String getInput ()
   {
      Scanner userDate = new Scanner(System.in);
      
      // Get user input for a date
      System.out.print("Which date would you like to look at (MM/DD)? ");
      String date = userDate.nextLine();
      
      // Store the extracted month from user input
      int inputM = monthFromDate(date);
      // Store the extracted day from user input
      int inputD = dayFromDate(date);

      // Determine whether the user input, including the value of month and day, is valid. 
      // The valid input includes:
      //  - the value of month is from 1 to 12
      //  - the value of day is belong to the actual number of day of a month in Calendar
      // If the user input is invalid, prompt the user to enter a valid date
      boolean isValid = false;
      while(!isValid) {
         if ((inputM >= 1 && inputM <=12) && (inputD <= getLength(inputM) && inputD >=1)) {
            isValid = true;
         } else {
            System.out.print("Please enter a valid date (MM/DD). ");
            date = userDate.nextLine();
            inputM = monthFromDate(date);
            inputD = dayFromDate(date);
         }
      }
      return date;   
   }
   
   /*
      Extracts the month from a given date string in "mm/dd" format.
      Parameters
         - date: The date string from which to extract the month.
      Return
         - When valid is true, the method will return the parsed integer value of monthString.
         - When valid is false, the method will return -1 to indicate an error or invalid input.
   */
   public static int monthFromDate(String date) 
   {
      if(date.contains("/") && !date.startsWith("/")) {
         // Find the index of the slash in the date string
         int index = date.indexOf("/");
         String monthString = date.substring(0, index);
         
         // Check if the dayString consists of valid digit characters
         boolean valid = true;
         // a count used for iterating through each character of the month string
         int i = 0;
         while(i < monthString.length() && valid) {
            // Check if each character is a digit
            valid = Character.isDigit(monthString.charAt(i));
            i++;
         }
         if(valid) {
            return Integer.parseInt(monthString);
         }
      }
      return -1;
   }
         
   /* 
      Extracts the day value from the given date string.
      Parameters
         - date: A string representing a date in the format "MM/DD".
      Return
         - The integer value of the extracted day if the date is valid and meets the requirements,
           otherwise -1 to indicate an error or invalid input.
    */
   public static int dayFromDate(String date) 
   {
      if(date.contains("/") && !date.startsWith("/")) {
         // Find the index of the slash in the date string
         int index = date.indexOf("/");
         String dayString = date.substring(index + 1);
         
         // Check if the dayString consists of valid digit characters
         boolean valid = true;
         // a count used for iterating through each character of the date string
         int i = 0;
         while(i < dayString.length() && valid) {
            valid = Character.isDigit(dayString.charAt(i));
            i++;
         }
         if(valid) {
            return Integer.parseInt(dayString);
         }
      }
      return -1;
   }

   /*
      Returns the number of days in a given month.
      Parameters
         - month: An integer representing the month (1-12).
      Return
         - The number of days in the specified month.
    */
   public static int getLength(int month)
   {
      // the number of day in a month
      int length;
      
      if(month == 2){
         length = 28;
      } else if ((month <=7 && month % 2 == 0) || (month > 7 && month % 2 == 1)) {
         length = 30;
      } else {
         length = 31;
      }
      return length;
   }    

   
   /*
      According to the specific value of month and day, displaying the corresponding calendar of 
      the month and highlight the given day if necessary.         
      Parameters
         - month: the variable of month means the month to be printed
         - day: the variable of the day means the day to be highlighted
         - highlight: the variable of the highligh means that determine if it is necessary to 
           highlight a day
         - eventArray
      Return
         - calendarContent
   */
   public static String drawCalendar(int month, int day, boolean highlight, String[][] eventArray) 
                                     throws FileNotFoundException {
      // Determine the day of the week for the first day of a given month
      // Set 2023 as default number of year
      int year = 2023;
      // The first day of the given parameter month , e.g: 1 for Sunday, 2 for Monday etc.
      int firstDayOfWeek = getFirstDayOfWeek(year, month);  
      // Number of block counts I have found.
      int blockCount = 0;
      String calendarContent = "";
      
      // Display the ASCII Header 
      calendarContent += drawHeader();

      // Display the month at the top of the calendar
      int width = 100;
      String centeredText = String.format("%" + width + "s", month);
      calendarContent += centeredText;
      calendarContent += "\n";

      // Display week name
      calendarContent += 
      "=================================================================================================================================================================================================================================\n";
      calendarContent += 
      "|              Sun              |              Mon              |              Tue              |              Wed              |              Thu              |              Fri              |              Sat              |\n";
      calendarContent += 
      "=================================================================================================================================================================================================================================\n";


      // The variable of row means the current row of a calendar
      for (int row = 0; row < 5; row++) {
         // The variable of col means the current column of a calendar
         for (int col = 0; col < 7; col++) {
            // I found a new block, add 1
            blockCount++;

            // Determine if we need to print an empty block
            if (blockCount < firstDayOfWeek || blockCount > (getLength(month) + firstDayOfWeek -1))
               {
               calendarContent += "|                               ";
               
            } else {
               // Print the value of each day of the given month
               // Only if the day equals to the user selected day and the variable of highlight 
               // equals to true, adding an esterisk; 
               int printDay = blockCount - firstDayOfWeek + 1;
               String eventDescription = eventArray[month-1][printDay-1];
               
               if (highlight == true && day == printDay) {
                  calendarContent += String.format("|%-29d* ", printDay);
               } else if (eventDescription == null){
                  calendarContent += String.format("|%-30d ", printDay);
               } else {
                  String space = " ";
                  int totalWidth = 30;
                  int remainingSpaces = totalWidth - String.valueOf(printDay).length()- 
                                        eventDescription.length();              
                  String formattedString = "|" + printDay + " " + eventDescription;
                  
                  // Add the remaining spaces to the formatted string
                  for (int i = 0; i < remainingSpaces; i++) {
                     formattedString += space;
                  }
                  // System.out.printf("|%-2d %s ", printDay, eventDescription);
                  calendarContent += formattedString;
               }  
            } 
         }
         calendarContent += "|\n";

         for (int i = 0; i < 7; i++) {
            calendarContent += "|                               ";
         }
         calendarContent += "|\n";
         
         for (int i = 0; i < 7; i++) {
            calendarContent += "|                               ";
         }
         calendarContent += "|\n";
         calendarContent += "=================================================================================================================================================================================================================================\n";
      }
      System.out.print(calendarContent);
      return calendarContent;
   }
   
   /*
      This method will read a event file, then store it and display events when user draw a 
      calendar.
      Parameters
          String[][] eventArray
      Return
          String[][] eventArray    
   */
   public static String[][] readFile(String[][] eventArray)  throws FileNotFoundException 
   {
      //  read event file and show on canlendar
      File eventFile = new File ("calendarEvents.txt");
      Scanner readFile = new Scanner (eventFile);
      
      // String[][] eventArray = new String[12][31];

      while (readFile.hasNextLine()){
         String line = readFile.nextLine();
         
         String[] lineElements = new String[2];
         lineElements = line.split(" ");
         
         String date = lineElements[0];
         String event = lineElements[1];
            
         String[] dateParts = date.split("/");
         
         int eventMonth = Integer.parseInt(dateParts[0]) - 1 ;
         int eventDay = Integer.parseInt(dateParts[1]) - 1 ;
         
        // Locate the specific position in the 2-dimensional array where the event should be stored
         eventArray[eventMonth][eventDay] = event;
      }
      return eventArray;
   }
   
  
   /*
      The event planning action should prompt the user for an event. The event should be entered 
      in the form of �MM/DD event_title�. After parsing the event, should be stored in a global 
      array that will contain all events planned for that year.Once the event is parsed and stored, 
      if the month calendar is drawn that contains scheduled events, those events should be presented 
      in the calendar. If there is an event in a day, the title of the event should be placed within 
      the blank space within the square of the day.

      Parameters
          String[][] eventArray

      Return
          String[][] eventArray 
   */    
   public static String[][] eventPlanning(String[][] eventArray)  throws FileNotFoundException  
   {
      eventArray = readFile(eventArray);
      Scanner userInput = new Scanner(System.in);
      String line;
      String[] lineElements;
      String date;
      String event;
      int eventMonth;
      int eventDay;

      boolean isValidInput = false;
      while (!isValidInput) {
         System.out.print("Please enter an event (MM/DD event_title): ");
         line = userInput.nextLine();
         lineElements = line.split(" ");

         if (lineElements.length != 2) {
             System.out.println(
               "Invalid input. Please enter the event in the format 'MM/DD event_title'.");
         } else {
             date = lineElements[0];
             event = lineElements[1];

             String[] dateParts = date.split("/");

             eventMonth = Integer.parseInt(dateParts[0]) - 1;
             eventDay = Integer.parseInt(dateParts[1]) - 1;

             if (isValidDate(eventMonth, eventDay)) {
                 eventArray[eventMonth][eventDay] = event;
                 isValidInput = true;
             } else {
                 System.out.println("Invalid date. Please enter a valid date (MM/DD).");
             }
         }
      }
      return eventArray; 
   }
   
   public static boolean isValidDate(int month, int day) {
      if (month >= 0 && month < 12 && day >= 0 && day < getLength(month + 1)) {
         return true;
      }
      return false;
   }
     
   /*
     the user will be prompted to enter a month to print. After the month to print has been obtained, 
     the program should proceed to ask the user for the name of a file to which it will print the calendar.

      Parameters
         String[][] eventArray
    */  
   public static void printFile (String[][] eventArray)  throws FileNotFoundException 
   {
      Scanner userInput = new Scanner(System.in);
      System.out.print("Which month you want to print(MM)? ");
      String printMonth = userInput.nextLine();
      
      System.out.print("Output file name: ");
      String outputFileName = userInput.nextLine();
      
      drawCalendarAndPrint(Integer.parseInt(printMonth), 0, false, eventArray, outputFileName);
   }

   /*
    * Draws the calendar for a given month, highlighting a specific day if necessary,
      and prints it a specified output file.
      Parameters
         - month: The month to be printed.
         - day: The day to be highlighted (0 if no highlight is needed).
         - highlight: Determines whether to highlight the specified day.
         - eventArray: The array containing event information for each day.
         - outputFileName: The name of the output file to which the calendar will be printed.
    */ 
   public static void drawCalendarAndPrint(int month, int day, 
                  boolean highlight, String[][] eventArray, String outputFileName) 
                  throws FileNotFoundException
   {
      String calendarContent = drawCalendar(month, day, highlight, eventArray);
      
      // Write the calendar content to the file
      PrintStream output = new PrintStream(new FileOutputStream(outputFileName));
         output.print(calendarContent);
   }

   /*
    * Returns the first day of the week for a given month and year.
      Parameters
         - year: An integer representing the year.
         - month: An integer representing the month (1-12).
      Return
         An integer representing the first day of the week for the specified month and year.
         The value is in the range 1-7, where 1 represents Sunday, 2 represents Monday, and so on.
   */
   public static int getFirstDayOfWeek(int year, int month)
   {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 2023);
      calendar.set(Calendar.MONTH, month - 1);
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      
      // The first day of the given month
      int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
      return dayOfWeek;
   }
   
   /*
    * Draw the header of a tree at the begining of a calendar.
      Return
         headerContent  
    */
   public static String drawHeader()
   {
      int height = 7;
      int trunkWidth = height / 3;
      int trunkHeight = height / 2;
      String headerContent = "";
      
      // Print the tree crown
      for (int i = 1; i <= height; i++) {
         for (int j = 1; j <= height - i; j++) {
            headerContent += " ";
         }
         
         for (int k = 1; k <= i * 2 - 1; k++) {
            headerContent +="*";
         }
         
         headerContent += "\n";
      }
      
      // Print the tree trunk
      for (int i = 1; i <= trunkHeight; i++) {
         for (int j = 1; j <= height - trunkWidth; j++) {
            headerContent +=" ";
         }
         
         for (int k = 1; k <= trunkWidth * 2; k++) {
            headerContent += "*";
         }
         
         headerContent += "\n";
      }
      return headerContent;
   }       
} // the end of the class 
