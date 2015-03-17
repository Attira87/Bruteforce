package bruteforce;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Stack;


public class PermuteRepeat extends PermutationAlgorithm implements Generator {
	int pointer; 	//index in index ArrayList of current character or character sequence to increment

	public PermuteRepeat(ArrayList<Object> charset, int ... lengths) throws CharsetNullException{
		super(charset, lengths);
		
		currentLength = 0;
		index = new ArrayList<Integer>(this.lengths[currentLength]);
		initializeIndex();
		this.pointer = index.size() - 1;
	}
	
	public PermuteRepeat(String charset, BigInteger start, int length) throws CharsetNullException, IntegerStartGreaterThanNoOfPermutationsException{ // set starting point with an integer, 0 < start < charset.length ^ length
		this(stringToCharArrayList(charset), start, length);
	}
	@SuppressWarnings("unchecked")
	public PermuteRepeat(ArrayList<String> charset, BigInteger start, int length) throws CharsetNullException, IntegerStartGreaterThanNoOfPermutationsException{
		super(charset,length);
		currentLength = 0;
		index = new ArrayList<Integer>(this.lengths[currentLength]);
//		if(start == null){
//			
//		}
		if(start.equals(BigInteger.valueOf(-1))){
			//System.out.println(start);
			setRandomStart();
		}else{
			setStart(start);
		}
		
		this.pointer = index.size() - 1;
	}
	
	//TODO: String stop parameter that indicates where to stop
	public PermuteRepeat(String charset, String start,  int from, int to)
			throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException {
		this(stringToCharArrayList(charset), stringToCharArrayList(start), fromTo(from, to));
	}

	public PermuteRepeat(String charset, String start, int ... lengths)
			throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException {
		this(stringToCharArrayList(charset), stringToCharArrayList(start), lengths);
	}

	public PermuteRepeat(ArrayList<String> charset, ArrayList<String> start, int ... lengths)
			throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException {
		super(charset, lengths);
		this.lengths = lengths;
		
		if (start == null){
			currentLength = 0;
			index = new ArrayList<Integer>(this.lengths[currentLength]);
			initializeIndex();
		}else{
			setStart(start);
		}
		this.pointer = index.size() - 1;
	}

	private static int[] fromTo(int from, int to) throws ZeroLengthException {
		if(from == 0 || to == 0){
			throw new ZeroLengthException("Starting or ending length is zero");
			//TODO: test for this
		}
		
		int diff = to - from;
		if(diff < 0) diff = Math.abs(--diff);
		else if(diff >= 0) diff++; 
		int[] lengths = new int[diff];
		
		if(from <= to){
			for (int j = 0, i = from; i <= to; ++i, ++j) {
				lengths[j] = i;
			}
		}else if(from > to){
			for (int j = 0, i = from; i >= to; --i, ++j) {
				lengths[j] = i;
			}
		}
		return lengths;
	}
	
	private void setRandomStart() throws IntegerStartGreaterThanNoOfPermutationsException {

		long r = (long) (Math.random() * (long)Math.pow(charset.size(), lengths[currentLength]));
		
		BigInteger random = BigInteger.valueOf(r);
		//System.out.println(random);
		setStart(random);
	}
	
	private void setStart(BigInteger start) throws IntegerStartGreaterThanNoOfPermutationsException{ //given a number between 0 and the number of permutations, set starting string
		//BigInteger pow = new BigInteger((long) Math.pow(charset.size(), lengths[currentLength]));
		BigInteger pow = BigInteger.valueOf((long) Math.pow(charset.size(), lengths[currentLength]));
		//System.out.println(pow);
		if(start.compareTo(pow) >= 0){
			throw new IntegerStartGreaterThanNoOfPermutationsException("The starting integer is greater than the number of permutations");
		}
		convertBase(start, charset.size());
	}
	
	//base conversion algorithm
	//http://www.cut-the-knot.org/recurrence/conversion.shtml
	
	/** Calculates what indices to put in index ArrayList for a given password number 
	 * 
	 * @param start 0 < n < charset.size ^ length
	 * @param base size of charset
	 */
	private void convertBase(BigInteger start, int base){
		Stack<Integer> stack = new Stack<Integer>();
		while(start.compareTo(BigInteger.valueOf(base)) >= 0){
			stack.push((start.mod(BigInteger.valueOf(base)).intValue()));
			start = start.divide(BigInteger.valueOf(base));
		}

		int zerosToPrepend = lengths[0] - (stack.size() + 1);
		for(int i = 0; i < zerosToPrepend; i++) index.add(0);
		
		index.add(start.intValue());
		
		while(!stack.empty()){
			index.add(stack.pop());
		}
	}


	private void initializeIndex() {
		index.clear();
		for (int i = 0; i < lengths[currentLength]; i++)
			index.add(0);
	}

	public void reset(){
		currentLength = 0;
		initializeIndex();
		pointer = index.size() - 1;
		lastReached = false;
		
		
		
	}

	/**
	 * increment to next position
	 */
	public void nextPassword(){
		/*
		 * example starting pass for charset [abcd] and length 3: aa(a)
		 * brackets indicate where 'pointer' is currently pointing
		 */
		
		if(((Integer) index.get(pointer)).intValue()==charset.size()-1){ // if aa(d), i.e. last char reached
			do{
				index.set(pointer,0); 	//sets last char to first char from charset, example: aa(a)
				pointer--; 				//decrement pointer, example: a(a)a
				
				if(pointer==-1){ 		// last password reached, example: ()ddd
	
					  if(!nextLength()){ //max length exceeded, no more passwords
					   	lastReached = true;
					   	//System.out.println("last reached");
					  	break;
					  }
	
					//recreate the index
					initializeIndex();
					break;
				}
			} while(((Integer) index.get(pointer)).intValue()==charset.size()-1);	//again, if last char reached at current position
	
			if(pointer!=-1){ //example: not ()ddd
				index.set(pointer,(Integer)index.get(pointer)+1); 				//increment current, example: a(b)a 
				pointer = index.size()-1; 								//reset pointer to rightmost position, example: ab(a)
			}
			if(pointer==-1) //example: ()ddd
				pointer = index.size()-1; 								//reset pointer to rightmost position, example: ab(a)
			
		}else index.set(pointer, (Integer)index.get(pointer)+1); 				//increment rightmost char, example: aa(b)
	}

	private boolean nextLength() {
		if (currentLength == lengths.length - 1) {
			return false;
		} else
			currentLength++;
		return true;
	}


}
