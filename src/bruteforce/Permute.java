package bruteforce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Permute extends PermutationAlgorithm implements Generator {

	public Permute(String charset, int nth) throws CharsetNullException, IntegerStartGreaterThanNoOfPermutationsException{
		this(stringToCharArrayList(charset), nth);
	}
	
	public Permute(String charset) throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, SequenceOccurrenceMismatchException{
		this(stringToCharArrayList(charset), null);
	}
	public Permute(String charset, String start) throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, SequenceOccurrenceMismatchException{
		this(stringToCharArrayList(charset), stringToCharArrayList(start));
	}
	
	public Permute(ArrayList<String> charset, ArrayList<String> start) throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, SequenceOccurrenceMismatchException{
		super(charset, charset.size());
		
		if (start == null){
			currentLength = 0;
			index = new ArrayList<Integer>(this.lengths[currentLength]);
			initializeIndex();
		}else{
			//index = new ArrayList<Integer>();
			//each char in start must 1. exist in charset (checked in setStart) and 2. not be repeated, unless also repeated in charset
			//check for repeated chars in 'start', if any exist throw exception
			Map<String, Integer> charsetMap = new HashMap<String, Integer>();
			Map<String, Integer> startMap = new HashMap<String, Integer>();
			
			for(String s: charset){
				if(charsetMap.containsKey(s)){
					charsetMap.put(s, charsetMap.get(s)+1);
				}
				else charsetMap.put(s, 1);
			}
			
			for(String s: start){
				if(startMap.containsKey(s)){
					startMap.put(s, startMap.get(s)+1);
				}
				else startMap.put(s, 1);
				
				if(!charsetMap.containsKey(s)) throw new StartingSequenceNotPresentInCharsetException("Starting sequence not present in character set");
				if(startMap.get(s) > charsetMap.get(s)){
					throw new SequenceOccurrenceMismatchException("Occurence of sequence in starting string greater than in given charset");
				
				}
			}
			setStart(start);
		}

	}
	
	public Permute(ArrayList<String> charset, int start) throws CharsetNullException, IntegerStartGreaterThanNoOfPermutationsException{
		super(charset, charset.size());
		currentLength = 0;
		index = new ArrayList<Integer>(this.lengths[currentLength]);
		if(start == -1){
			setRandomStart();
		}else{
			setStart(start);
		}

	}
	
	private void setRandomStart() throws IntegerStartGreaterThanNoOfPermutationsException {

		int random = (int) (Math.random() * (double)factorial());
		System.out.println(random);
		setStart(random);
	}
	
	private int factorial(){
		int factorial = 1;
		for(int i = 2; i <= lengths[currentLength]; i++){
			factorial *= i;
		}
		return factorial;
	}
	
	
	//http://stackoverflow.com/questions/7918806/finding-n-th-permutation-without-computing-others/7919887#7919887
	private void setStart(int n) throws IntegerStartGreaterThanNoOfPermutationsException{ //given an integer 0 < n < factorial(charset.size)
		
		if(n >= factorial()){
			throw new IntegerStartGreaterThanNoOfPermutationsException("The starting integer is greater than the number of permutations");
		}
		
		int k = 0;
		int fact[] = new int[lengths[0]];
		int perm[] = new int[lengths[0]];
		
		fact[k] = 1;
		while(++k < lengths[0]){
			fact[k] = fact [k-1] * k;
		}
		
		for(k = 0; k < lengths[0]; ++k){
			perm[k] = n / fact [lengths[0] - 1 - k];
			n = n % fact[lengths[0] - 1 - k];
		}
		
		for(k = lengths[0] - 1; k > 0; --k){
			for(int j = k - 1; j >= 0; --j){
				if(perm[j] <= perm[k]){
					perm[k]++;
				}
			}
		}
		
		for(k = 0; k < lengths[0]; ++k){
			index.add(perm[k]);
		}
		
		//System.out.println(index.toString());
	}
	
	private void initializeIndex(){
		for(int i = 0; i < lengths[0]; i++){
			index.add(i);
			//System.out.printf("%s",charset.charAt(i));
		}
	}
	
	//http://lookupnotes.blogspot.co.uk/2012/04/iterative-and-recursive-algorithms-to.html
	public void nextPassword(){
	  int i = lengths[0] - 1;
	  while (index.get(i-1) >= index.get(i)){
		  i = i-1;
		  if(i-1<0){
			  lastReached = true;
			  return;
		  }
	  }
	    

	  int j = lengths[0];
	  while (index.get(j-1) <= index.get(i-1))
		  j = j-1;

	  swap(i-1, j-1);    // swap values at positions (i-1) and (j-1)
	 
	  i++; j = lengths[0];
	  while (i < j){
	    swap(i-1, j-1);
	    i++;
	    j--;
	  }
	}
	
	
	private void swap(int i, int j){
		int t = index.get(i);
		index.set(i, index.get(j));
		index.set(j, t);
	
	}
	
	
	

}
