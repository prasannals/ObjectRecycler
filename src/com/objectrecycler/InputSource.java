package com.objectrecycler;

/**
 * To be implemented by any class that acts as an input source for the ObjectRecycler
 * class.
 * @author Prasanna Lakkur Subramanyam
 *
 * @param <T> The type of object an InputSource object returns after reading data
 * from source
 * 
 */
public interface InputSource<T> {
	
	/**
	 * Read the data from the source, whatever it may be, and fill the object T 
	 * with the required data
	 * @param index - the index of the data that has to be retrieved. //TODO 
	 * @return an object of type T with the required data inserted in it.
	 */
	T readFromSource(int index);
	
	/**
	 * Fill the provided object with the appropriate data which corresponds to 
	 * the provided index
	 * @param object - the object which has to be filled with data
	 * @param index - the index of the data to be retrieved
	 */
	void fillObjectWithInfo(int index, T object);
}


