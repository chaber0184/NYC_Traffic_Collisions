/**
 * 
 */
package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author Dimitri Chaber
 *
 */
public class CollisionInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//date fields
		File test = null;
		
		CollisionsData data = new CollisionsData();

		//create scanner to read user input
		Scanner input = new Scanner(System.in);
		String user = "";
		
		Date start = null;
		Date end = null;
		String zip = null;
		
		//check to see if file location is in argument and terminate program if it is not
		try {
			//create file object from command line argument
			test =  new File(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException w) {
			System.err.println("No file in argument");
			System.exit(1);
			
		}
		//check if file exists and terminate if it does not
	    if( test.exists() == false ) {
	    	System.err.println(test.getName()+" does not exist");
	    	System.exit(1);
		
		}
	    //create scanner to read from from file
		Scanner collisionsFile = null;
		try {
			collisionsFile = new Scanner(test);
		} 
		catch (FileNotFoundException e) {
			
		}
		
		//read contents of file and add them to CollisionsData
		do {
			
			ArrayList<String> temp = splitCSVLine(collisionsFile.nextLine());
			
			try{
			if(temp.size() > 23){
				Collision tempCol = new Collision(temp);
				
				data.add(tempCol);
			}	
			

			}
			catch(Exception e) {
			
				
			}
		} 
		while(collisionsFile.hasNext());
		//close file scanner
		collisionsFile.close();
		
		
		
		
		//user input loop
		while(!user.equalsIgnoreCase("quit")){
			System.out.println("Enter a zip code(type quit to exit):");
			user = input.next();
			//validate zip code
			try{
				if(Collision.checkZip(user))
					zip = user;
			}
			catch(IllegalArgumentException a){
				System.out.println("Invalid zip");
				continue;
			}
			//validate Dates
			try{
				System.out.println("Enter a start date(MM/DD/YYYY):");
				user = input.next();
				start = new Date(user);
			
				System.out.println("Enter an end date(MM/DD/YYYY):");
				user = input.next();
				end = new Date(user);
			}
			catch(Exception e){
				System.out.println("Invalid date");
				continue;
			}
			//print out report based on user input
			System.out.println(data.getReport(zip, start, end));
			
		}
		//close user input
		input.close();
	

	}
	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author Joanna Klukowska
	 * @param textLine	a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that line
	 */
	public static ArrayList<String> splitCSVLine(String textLine){
		
		ArrayList<String> entries = new ArrayList<String>(); 
		int lineLength = textLine.length(); 
		StringBuffer nextWord = new StringBuffer(); 
		char nextChar; 
		boolean insideQuotes = false; 
		boolean insideEntry= false;
		
		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			
			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {
					
				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false; 
					insideEntry = false;
				}else {
					insideQuotes = true; 
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
				// add it to the current entry 
					nextWord.append( nextChar );
				}else { // skip all spaces between entries
					continue; 
				}
			} else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar); 
				} else { // end of entry found
					insideEntry = false;
					
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			} 
			
		}
		// add the last word ( assuming not empty ) 
		// trim the white space before adding to the list 
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}

}
