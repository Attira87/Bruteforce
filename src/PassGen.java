import java.util.ArrayList;

/**
 * Generates passwords from min to max from a given charset
 * TO DO: IMPLEMENT STARTING PASSWORD OPTION AND PLUG INTO THE INDEX ARRAYLIST
 * TO DO: RUN IN MULTIPLE THREADS
 */
public class PassGen implements Password {
	char[] charset;
	int min, max, indexSize, current;
	ArrayList<Integer> index;
	boolean lastReached;
	
	public PassGen(char[] c, int min, int max){
		this.charset = c;
		this.min = min;
		this.indexSize = min;
		this.max = max;
		
		this.lastReached = false;
		index = new ArrayList<Integer>(indexSize);
		for(int i = 0; i < indexSize; i++) index.add(0);
		
		this.current = index.size() - 1;
		
	}

	@Override
	public String nextPassword() {

		if(lastReached) return null;
		
		String s = "";
		
		//example starting pass for charset abcd and length 3: aa(a)
		//brackets indicate where 'current' is currently pointing
		
		//builds the string representation of current password
		for(int i=0; i<index.size(); i++){
			s += charset[index.get(i)];
		}
		//at this point s contains current password
		
		//increment to next position
		if(index.get(current).intValue()==charset.length-1){ // if aa(d), i.e. last char reached
			while(index.get(current).intValue()==charset.length-1){	//again, if last char reached at current position
				index.set(current,0); //sets last char to first char from charset, example: aa(a)
				current--; //decrement pointer, example: a(a)a
				
				if(current==-1){ // last password reached
					indexSize++;
					
					if(indexSize>max){ //no more passwords
						lastReached = true;
						break;
					}

					//recreate the index
					index.clear();
					for(int i = 0; i < indexSize; i++) index.add(0);
					break;
				}
			}

			if(current!=-1){ //example: not ()ddd
				index.set(current,index.get(current)+1); //increment current, example: a(b)a 
				current = index.size()-1; //reset pointer to rightmost position, example: ab(a)
			}
			if(current==-1) //example: ()ddd
				current = index.size()-1; //reset pointer to rightmost position, example: ab(a)
			
		}else index.set(current, index.get(current)+1); //increment rightmost char, example: aa(b)

		return s;
	}
}
