package com.objectrecycler;

/**
 * An ObjectRecycler that loads objects in and out of memory one at a time till 
 * the required object is obtained
 * @author Prasanna Lakkur Subramanyam
 *
 * @param <T> The type of object that the object recycler will store
 */
public class OneStepObjectRecycler<T> extends ObjectRecycler<T>{

	public OneStepObjectRecycler(int sizeLimit, Savable<T> savable, 
			InputSource<T> inputSource) {
		super(sizeLimit, savable, inputSource);
	}

	
	/**
	 * 
	 * @param index the index of the data in the list
	 * @return An object of type T which represents the data at the given index
	 */
	@Override
	public T getDataAt(int index){
		if(index < 0)
			throw new IllegalArgumentException("Index cannot be negative");
		
		if(firstIndexInMemoryList == -1)
			throw new IllegalArgumentException("There is no entry in the List yet");
		
		if(index >= firstIndexInMemoryList){
		//it's either in the list of items that is in memory or something totally new that we've got to fetch
			
			if(index < firstIndexInMemoryList + dataInMemory.size() ){
			//the data already exists in the memory
				return dataInMemory.get(index - firstIndexInMemoryList);
			
			}else {
			//index already greater than firstIndexInMemoryList, it's not less than firstIndexInMemoryList + dataInMemory.size(), hence,
			//it must be greater than firstIndexInMemoryList + dataInMemory.size(). 
				//load next element into memory until the element at "index" is loaded into the memory
				while( (firstIndexInMemoryList + dataInMemory.size() - 1) != index){
					loadNext();
				}
				
				return dataInMemory.get(dataInMemory.size() - 1);
			}
			
			
		} else {
		//the index of the item to be fetched is > 0 and  < firstIndexInMemoryList. Therefore, it must be in the persistent storage
			while(firstIndexInMemoryList != index){
				loadPrev();
			}
			
			return dataInMemory.get(0);
		}
		
	}
	
	@Override
	public String toString() {
		return dataInMemory.toString() + "\nFirstIndex is " + firstIndexInMemoryList + "\n";
	}

}
