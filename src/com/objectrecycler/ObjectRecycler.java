package com.objectrecycler;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Prasanna Lakkur Subramanyam
 *
 * @param <T> The type of object that the object recycler will store
 */
public abstract class ObjectRecycler<T> {
	/**
	 * An object which helps us save data of type T
	 */
	protected Savable<T> savable;
	
	/**
	 * An object which helps acts provides us with data of type T from 
	 * an input source.
	 */
	protected InputSource<T> inputSource;
	
	/**
	 * The maximum allowed size of the List of objects in memory
	 */
	protected int sizeLimit;
	
	/**
	 * List of objects of type T in memory
	 */
	protected List<T> dataInMemory; 
	
	/*
	 * A note on the different types of "index" that we'll come across
	 * 
	 * "actual index"
	 * Data is expected to be a list of items from 0 to n. This is the index that
	 * the client uses. We shall call it's index "actual index" from now on(as it
	 * represents the actual place of the data from the clients perspective)
	 * 
	 * "memory index"
	 * "memory index" refers to the index of the items in the "dataInMemory" List.
	 */
	
	
	/**
	 * represents the "actual index" of the first item in the "dataInMemory" List
	 */
	protected int firstIndexInMemoryList;
	
	/**
	 * 
	 * @param sizeLimit The maximum allowed size of the List of objects in memory
	 * @param savable An object which helps us save data of type T
	 * @param inputSource An object which helps acts provides us with data of 
	 * type T from an input source.
	 */
	protected ObjectRecycler(int sizeLimit, Savable<T> savable, 
			InputSource<T> inputSource){
		this.sizeLimit = sizeLimit;
		this.savable = savable;
		this.inputSource = inputSource;
		this.dataInMemory = new ArrayList<>();
		this.firstIndexInMemoryList = 0; 
		
		//load the first "sizeLimit" number of items into memory
		
		for(int i = 0; i < sizeLimit; i++){
			dataInMemory.add(inputSource.readFromSource(i));
		}
	}
	
	
	
	
	
	
	protected Savable<T> getSavable() {
		return savable;
	}



	protected void setSavable(Savable<T> savable) {
		this.savable = savable;
	}



	protected InputSource<T> getInputSource() {
		return inputSource;
	}



	protected void setInputSource(InputSource<T> inputSource) {
		this.inputSource = inputSource;
	}



	protected int getSizeLimit() {
		return sizeLimit;
	}



	protected void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}



	protected List<T> getDataInMemory() {
		return dataInMemory;
	}



	protected void setDataInMemory(List<T> dataInMemory) {
		this.dataInMemory = dataInMemory;
	}



	protected int getFirstIndexInMemoryList() {
		return firstIndexInMemoryList;
	}



	protected void setFirstIndexInMemoryList(int firstIndexInMemoryList) {
		this.firstIndexInMemoryList = firstIndexInMemoryList;
	}


	
	
	
	
	
	
	

	/**
	 * Loads the next item into the recycler list
	 * @return a reference to the object loaded into the list
	 */
	protected T loadNext(){
		//index is the "actual index" of the next item. We get it by adding the actual index of the first item in the list + 
		//number of items in the list(this gives us the "actual index" of the next item to be inserted in the list)
		int index = firstIndexInMemoryList + dataInMemory.size();
		//for the next entry to be loaded, the first entry in the memory has gotta go(be saved in permanent storage)
		T objRef = dataInMemory.get(0);
		dataInMemory.remove(0);
		//save the old data
		savable.dumpData(firstIndexInMemoryList, objRef);
		//populate the object with new data
		inputSource.fillObjectWithInfo(index, objRef);
		//put it back into the list at the end
		dataInMemory.add(objRef);
		firstIndexInMemoryList++;
		
		return objRef;
	}
	
	/**
	 *  
	 * @return
	 */
	protected T loadPrev(){
		//the "actual index" of the data to be retrieved
		int index = firstIndexInMemoryList - 1;
		//for the previous entry to be loaded, the last entry in the memory has gotta go(be saved in permanent storage)
		int toRemoveActualIndex = firstIndexInMemoryList + dataInMemory.size() - 1;
		T objRef = dataInMemory.get(toRemoveActualIndex - firstIndexInMemoryList);
		dataInMemory.remove(toRemoveActualIndex - firstIndexInMemoryList);
		//save the old data
		savable.dumpData(toRemoveActualIndex, objRef);
		//populate the object with new data
		savable.retrieveDataIntoObject(index, objRef);
		//put it back into the list at the end
		dataInMemory.add(0, objRef);
		firstIndexInMemoryList--;
		
		return objRef;
	}
	
	
	
	public abstract T getDataAt(int index);
	
	
}
