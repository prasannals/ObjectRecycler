package com.objectrecycler;

/**
 * To be implemented by a class which helps in persisting the data from ObjectRecycler
 * @author Prasanna Lakkur Subramanyam
 *
 * @param <T>
 */
public interface Savable<T> {
	
	/**
	 * Save the data from the parameter provided
	 * @param object the object whose data has to be saved
	 * @param index the index of the data being saved(helps while retrieving the data using the same index)
	 * @return true if successfully saved, false otherwise
	 */
	boolean dumpData(int index, T object);
	
	/**
	 * Retrieve the data at the specified index and return an object with the data 
	 * inserted into it
	 * @param index the index of the data to be retrieved
	 * @return an object of class T which contains the required data
	 */
	T retrieveData(int index);
	
	/**
	 * Retrieve the data at the specified index and fills the object provided with 
	 * the required data
	 * @param index
	 * @param object
	 */
	void retrieveDataIntoObject(int index, T object);
}
