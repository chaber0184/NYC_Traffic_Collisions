/**
 * 
 */
package project5;

import java.util.ArrayList;

/**
 * @author Dimitri Chaber
 * Class representing NYPD collision reports
 */
public class Collision implements Comparable<Collision> {
	//date fields
	private Date d;
	private String  zip, key;
	private int perInjured, perKilled, pedInjured, pedKilled, cycInjured, cycKilled, motoInjured, motoKilled;

	/**
	 * Collision constructor
	 * @param entries ArrayList of strings
	 * @throws IllegalArgumentException 
	 * @throws NumberFormatException
	 */
	public Collision(ArrayList<String> entries) throws IllegalArgumentException, NumberFormatException {
		if(entries.size() < 24)
			throw new IllegalArgumentException("invalid entry");
			d = new Date(entries.get(0));
			//check if zip is valid
			if(checkZip(entries.get(3)))
				zip = entries.get(3);
			checkKey(entries.get(23));
			perInjured = Integer.parseUnsignedInt(entries.get(10));
			perKilled = Integer.parseUnsignedInt(entries.get(11));
			pedInjured = Integer.parseUnsignedInt(entries.get(12));
			pedKilled = Integer.parseUnsignedInt(entries.get(13));
			cycInjured = Integer.parseUnsignedInt(entries.get(14));
			cycKilled = Integer.parseUnsignedInt(entries.get(15));
			motoInjured = Integer.parseUnsignedInt(entries.get(16));
			motoKilled = Integer.parseUnsignedInt(entries.get(17));
			
		
		
		
			
	}

	/**
	 * gets number of cyclist injured
	 * @return the cycInjured
	 */
	public int getCyclistsInjured() {
		return cycInjured;
	}

	/**
	 * gets number of cyclist killed
	 * @return the cycKilled
	 */
	public int getCyclistsKilled() {
		return cycKilled;
	}

	/**
	 * checks if key is valid
	 * @param k key to check
	 * @return true if valid
	 */
	public boolean checkKey(String k) {
		key = k;
		return true;
	}

	/**
	 * checks if zip code is correct format
	 * @param z zip to check
	 * @return true if valid
	 */
	public static  boolean checkZip(String z) {
		char[] array = z.toCharArray();
		if(array.length != 5){
			throw new IllegalArgumentException("Invalid zip code Must be 5 digits");
		}
		else for(int i = 0; i > array.length;i++ ){
				if(array[i] > 9 || array[i] < 0)
					throw new IllegalArgumentException("Invalid zip code Must be made of digits");
		}
		

		return true;
	}

	/**
	 * gets zip code
	 * 
	 * @return zip
	 */

	public String getZip() {
		return zip;

	}

	/**
	 * gets date
	 * 
	 * @return date
	 */
	public Date getDate() {
		return d;
	}

	/**
	 * gets key
	 * 
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * get number of people injured
	 * 
	 * @return the perInjured
	 */
	public int getPersonsInjured() {
		return perInjured;
	}

	/**
	 * get number of people killed
	 * 
	 * @return the perKilled
	 */
	public int getPersonsKilled() {
		return perKilled;
	}

	/**
	 * get number of pedestrians injured
	 * 
	 * @return the pedInjured
	 */
	public int getPedestriansInjured() {
		return pedInjured;
	}

	/**
	 * get number of pedestrians killed
	 * 
	 * @return the pedKilled
	 */
	public int getPedestriansKilled() {
		return pedKilled;
	}

	/**
	 * get number of motorists injured
	 * 
	 * @return the motoInjured
	 */
	public int getMotoristsInjured() {
		return motoInjured;
	}

	/**
	 * get number of motorists killed
	 * 
	 * @return the motoKilled
	 */
	public int getMotoristsKilled() {
		return motoKilled;
	}

	/**
	 * Compares collision objects based on zips, dates and keys
	 * 
	 * @param data
	 *            collision to compare
	 * @return result of comparison
	 */
	public int compareTo(Collision data) {
		int temp = this.zip.compareTo(data.getZip());
		if (temp != 0)
			return (temp);
		temp = d.compareTo(data.d);
		if (temp != 0)
			return temp;
		else
			return key.compareTo(data.key);
	}

	/**
	 * checks if equal based on zips, dates and keys
	 * 
	 * @param o
	 *            collision to check for equalityy
	 * @return true if equal
	 */
	public boolean equals(Object o) {
		Collision temp = (Collision) o;
		if (this.compareTo(temp) == 0)
			return true;
		else
			return false;

	}
	
	public String toString() {
		String s = zip + "  " + d + "  " + key;
		return s;
	}

}
