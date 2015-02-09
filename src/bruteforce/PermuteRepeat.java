package bruteforce;
import java.util.ArrayList;

/**
 * Generates passwords from min to max from a given charset using a permutation with repetition algorithm
 * TO DO: IMPLEMENT STARTING PASSWORD OPTION AND PLUG INTO THE INDEX ARRAYLIST
 * TO DO: RUN IN MULTIPLE THREADS
 */
public class PermuteRepeat implements Generator {
	char[] charset;
	int[] lengths; // for lengths 3,5,7 will generate only those lengths
	int min, max, indexSize, current;
	ArrayList<Integer> index;
	boolean lastReached;
	
	public PermuteRepeat(char[] c, int min, int max){
		this.charset = c;
		this.min = min;
		this.indexSize = min;
		this.max = max;
		
		this.lastReached = false;
		index = new ArrayList<Integer>(indexSize);
		zeroInitializeIndex();
		this.current = index.size() - 1;
		
	}
	
	public PermuteRepeat(char [] c, int min, int max, String start){
		
	}
	
	private void zeroInitializeIndex(){
		index.clear();
		for(int i = 0; i < indexSize; i++) index.add(0);
	}

	/**
	 * returns current password if exists or null and increments
	 */
	@Override
	public String getNextPassword() {

		String password = getCurrentPassword();
		nextPassword();
		
		return password;
	}
	/**
	 * increment to next position
	 */
	public void nextPassword(){
		/*
		 * example starting pass for charset [abcd] and length 3: aa(a)
		 * brackets indicate where 'current' is currently pointing
		 */
		
		if(index.get(current).intValue()==charset.length-1){ // if aa(d), i.e. last char reached
			do{
				index.set(current,0); //sets last char to first char from charset, example: aa(a)
				current--; //decrement pointer, example: a(a)a
				
				if(current==-1){ // last password reached, example: ()ddd
					indexSize++;
					
					if(indexSize>max){ //max length exceeded, no more passwords
						lastReached = true;
						break;
					}
	
					//recreate the index
					zeroInitializeIndex();
					break;
				}
			} while(index.get(current).intValue()==charset.length-1);	//again, if last char reached at current position
	
			if(current!=-1){ //example: not ()ddd
				index.set(current,index.get(current)+1); //increment current, example: a(b)a 
				current = index.size()-1; //reset pointer to rightmost position, example: ab(a)
			}
			if(current==-1) //example: ()ddd
				current = index.size()-1; //reset pointer to rightmost position, example: ab(a)
			
		}else index.set(current, index.get(current)+1); //increment rightmost char, example: aa(b)
	}

	/**
	 * builds the string representation of current password
	 */
	@Override
	public String getCurrentPassword() {
		if(lastReached) return null;
		
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<index.size(); i++){
			buf.append(charset[index.get(i)]);
		}
		
		return buf.toString();
	}

	@Override
	public Boolean hasNext() {
		return !lastReached;
	}
}
