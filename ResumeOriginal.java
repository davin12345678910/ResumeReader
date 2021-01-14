//Duaa Zaheer, Davin Kyi, Allison Li
//11/20/2020
//Resume Reader 


//Method description  
/*
This is a program that will be able to give you a short anaylsis of a resume in
which you will be looking at. This will give a company a quick anaylsis on which
resume in which they looked at is most highly ranked, in other words, which one has
more points when compared to the other resumes given. Also, the program can also 
give you the amount of experience you've had with a certain keyword, such as java.
This program will also allow you to see if the basic skills, such as Java are given
in the resume, and this is done by checking to see if it is contained within the 
resume given. Last and not least, it will give you all the words that are contained
within the resume you are looking at.  

*/ 

import java.util.*;
import java.io.*;

public class ResumeOriginal implements Comparable<Resume> {

   //imagine if a person say present 
   private static final String present = "present";
   
   //name of the person who wrote the resume  
   private String name;
   //this most likely will just tell you all the words it contains
   private Set<String> keyWords;
   //this will most likely come from the getExperience() method
   //but we need to somehow have a compare method for this
   //and this will likely require an algorithm
   private Map<String, Integer> experience; //<Keyword, Experience>
   //this will most likely be the amount of points calculated from the
   //algorithms class in which will be made by allison
   private int totalPoints;
   //these are the months in the years, and they are abbreviated  
   private ArrayList<String> months;
   
   //this is for the format method in which we might for the resume class, if needed  
   private String[] formats = {"%m/%d/%y", "%M %d, %y", "%d/%m/%y"};

   //these are helper fields for the methods, to help store the data 
   private Map<String, Integer> time;
   private Scanner scanner;
   //percentile of the canidate relative to other canidates  
   private double percentile;
   //this is the file of the resume  
   private File resumeData;  
   
   
   //i think we are going to pass in a file instead perhaps  
   public Resume(File file) throws FileNotFoundException {
      this.name = "";
      this.resumeData = file;
      scanner = new Scanner(file);
      //initalizing the Set of keywords in which we will be returning later 
      this.keyWords = new HashSet<String>();
      while(scanner.hasNext()) {
         String word = scanner.next();
         this.keyWords.add(word);
      }
      this.experience = new HashMap<String, Integer>();
      //the questions is when will we add in the points, maybe we want to have them after the algorithm gets the value
      
      //want this to set this to a value of 0 for now, and later find some way
      //YOU WILL WANT TO FIND A WAY HOW TO MAKE AN ALGORITHM OBJECT, AND ASK IT FOR THE TOTAL AMOUNT OF POINTS LATER  
      this.totalPoints = 0;  
      //this.totalPoints = totalPoints;
      
      this.time = new HashMap<String, Integer>();
      //first one is 11/30/2015, Nov 27, 2002 
      String [] months = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"}; 
      for (int i = 0; i < months.length; i++) {
         this.months.add(months[i]);
      }
   }
   
   //maybe we will assume that the person's name is the first two words in the resume  
   //this will return the name of the person wo wrote the resume  
   public String getName() {
      //we have to determine how to get the name from the resume  
      //THIS WILL BE THE FIRST LINE OF THE METHOD, OR THE EMPLOYER WILL PUT IN THE NAME IN HERE  
      return this.name;
   }
   
   public Set<String> getKeyWords() {
      return keyWords;
   }
   
   /*
   //returns the total amount of experience
   //which is a map of keywords with the associated amount of time
   //for each  
   public Map<String, Integer> getExperience() {
      return this.experience;
   }
   */
   
   //returns the total amount of points
   public int getTotalPoints() {
      return this.totalPoints;
   }
   
   //We will need a set and a get method for the percnetile here
   public double getPercentile() {
      return this.percentile;
   }
      
   //this will allow you to compare the points
   //you will have to set up some way to say if 
   //a negative number means something, 0 means equal
   //and a postive numbers means something 
   public int compareTo(Resume other) {
      return Integer.compare(this.totalPoints, other.totalPoints);
   }
   
   //this will check to see if all of the words are contained within your keyWords needed map  
   public boolean qualify(Set<String> neededWords) {
     return keyWords.containsAll(neededWords);
   }
   
   //this will allow you to get all of the individual keywords amount of experience
   //by giving you the total time in which has shown up with the word  
   
   //THIS IS THE MAIN THING WE NEED TO GET TO WORK, SO FAR THE BASE STRUCTURE GIVEN SO FAR
   //SHOULD WORK, WE MIGHT NEED TO DEBUG IF THIS DOSEN'T WORK,
   public Map<String, Integer> getExperience() throws FileNotFoundException {
      scanner = new Scanner(this.resumeData);
      
      //this will work if we are assuming that the first 2 words are going to be the 
      //name of the person in which we are talking about  
      this.name = "";
      String firstName = scanner.next();
      String lastName = scanner.next();
      this.name = firstName + " " + lastName;
      
      while(scanner.hasNextLine()) {
         int totalMonths = 0;
         String line = scanner.nextLine();
         Scanner newScan = new Scanner(line);
         //we will probably only want to put the keyword once into the map
         //and then from that one line, give that one keyword a time 
         //this will allow me to only change the times on the keywords found on
         //the one line 
         Set<String> foundWords = new HashSet<String>();  
         while (newScan.hasNext()) {
            //this is a starter for the reading in of the time, but
            //this is going to be trickier than i thought  
            String word = newScan.next();
            foundWords.add(word);
            if (months.contains(word.substring(0, 3))) {
               String startMonth = newScan.next();
               int startYear = newScan.nextInt();
               //we will think about what to do with a to or a - (always a -, or can it be a to/from, and etc.)  
               newScan.next(); //throws away the dash in the text 
               String endMonth = newScan.next().substring(0, 3);
               int endYear = newScan.nextInt();
               totalMonths += (endYear - startYear) * 12;
               //approx 30 days for each month, we can figure this out later as well
               totalMonths += months.indexOf(endMonth) - months.indexOf(startMonth);
            } 
        }
        for (String keyWord : foundWords) {
           //for each of the words that were in that one line
           //we will add the additional time
           //but, we do not want to add the time to every word found,
           //unless if it was on the line  
           experience.put(keyWord, time.get(keyWord) + totalMonths);
        }
      }
      return experience;
   }
   
   // we want to figure out, are we going to be returning how many sets...
   public Map<String, Set<String>> containsWanted(Map<String, Set<String>> keyWord) {
      Map<String, Set<String>> foundKeyWords = new HashMap<String, Set<String>>();
      for (String type : keyWord.keySet()) {
         Set<String> possibleKeyWords = keyWord.get(type);
         for (String possibleKeyWord : possibleKeyWords) {
            if (keyWords.contains(possibleKeyWord)) {
               if (!foundKeyWords.containsKey(type)) {
                  foundKeyWords.put(type, new HashSet<String>());
               }
               foundKeyWords.get(type).add(possibleKeyWord);
            }
         } 
      }
      return foundKeyWords;     
   }
}
   
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
   //I AM TRYING TO MAKE A METHOD THAT YOU CAN USE FOR MULTIPLE TIME FORMATS  
   
   //this is if you have different formats 
   //this will allow you to get all of the individual keywords amount of experience
   //by giving you the total time in which has shown up with the word  
   
   /*
   public Map<String, Integer> interpretTimeUniversal(File file) throws FileNotFoundException {
      scanner = new Scanner(file);
      while(scanner.hasNextLine()) {
         int totalMonths = 0;
         String line = scanner.nextLine();
         //we will probably only want to put the keyword once into the map
         //and then from that one line, give that one keyword a time 
         
         //this will allow me to only change the times on the keywords found on
         //the one line 
         Set<String> foundWords = new HashSet<String>();  
         for (int i = 0; i < formats.length; i++) {
            Scanner newScan = new Scanner(line);
            String format = formats[i];  
            while (newScan.hasNext()) {
               //this is a starter for the reading in of the time, but
               //this is going to be trickier than i thought  
               String word = newScan.next();
               //Month - jan - dec
               //days 1 - 31 <--- we are going to throw this in out algorithm (we might want to capture
               //some information on this)  
               //years just some random years  
               
               //if integer / 1000 < 10 && > 0, that is going to be a year, but if interger / 1000 < 0, it is a day 
               if (months.contains(word.substring(0, 3))) {
                  String startMonth = newScan.next();
                  int startYear = newScan.nextInt();
                  //we will think about what to do with a to or a -
                  newScan.next(); //throws away the dash in the text 
                  String endMonth = newScan.next().substring(0, 3);
                  int endYear = newScan.nextInt();
                  totalMonths += (endYear - startYear) * 12;
                  //approx 30 days for each month, we can figure this out later as well
                  totalMonths += months.indexOf(endMonth) - months.indexOf(startMonth);
               } 
           }
        }
        for (String keyWord : foundWords) {
           //for each of the words that were in that one line
           //we will add the additional time
           //but, we do not want to add the time to every word found,
           //unless if it was on the line  
           experience.put(keyWord, time.get(keyWord) + totalMonths);
        }
      }
      return experience;
   }
   */
/////////PossibleMETHODS///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//don't have a file passed in, do something else
   //TRY TO MAKE A METHOD WHERE YOU DON'T PASS IN A FILE    
   
   /*
   //this will allow you to get all the keywords in the document 
   public Set<String> getkeyWords(File file) throws FileNotFoundException {
      scanner = new Scanner(file);
      while(scanner.hasNext()) {
         String word = scanner.next();
         keywords.add(word);
      }
      return keywords;
   }
   */
   
   /* 
   //this method allows you to see if all the required keywords 
   //are contained within the resume in which you are looking at  
   public boolean containsAll(Set<String> contains) {
      for (String word : contains) {
         if (!keyWords.contains(word)) {
            System.out.println("not qualified!");
            return false;
         } 
      }
      System.out.println("is qualified!");
      return true;
   } 
   */
   
   /*
   //this is the qualify method, and this will let us know if a person is a worthy canidate in the 
   //beginning or not  
   public boolean qualify(Set<String> neededWords) {
      int totalPossibleWords = 0; 
      int foundWords = 0;
      for (String word : neededWords) {
         totalPossibleWords++;
         if (keyWords.contains(word)) {
            foundWords++;
         }
      }
      double percentFound = foundWords * 1.00 / totalPossibleWords;
      if (percentFound < 50.0) {
         return false;
      } else {
         return true;
      }
   }
   */
   

