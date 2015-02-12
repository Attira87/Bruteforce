package bruteforce;

import java.util.ArrayList;


public class PermuteRepeat implements Generator {
	ArrayList<String> charset;		//stores characters or character sequences
	int[] lengths;					//lengths of passwords to generate
	Integer currentLength;			//stores the index in lengths[] of current length
	int pointer; 					//index in index ArrayList of current character or character sequence to increment
	ArrayList<Integer> index;		//stores the indices of characters or character sequences in elements
	boolean lastReached;


	public PermuteRepeat(String charset, String start,  int from, int to)
			throws CharsetNullException, InvalidStartingStringLengthException, StartingStringCharactersNotPresentInCharsetException, ZeroLengthException {
		this(stringToCharArrayList(charset), stringToCharArrayList(start), fromTo(from, to));
	}

	public PermuteRepeat(String charset, String start, int ... lengths)
			throws CharsetNullException, InvalidStartingStringLengthException, StartingStringCharactersNotPresentInCharsetException {
		this(stringToCharArrayList(charset), stringToCharArrayList(start), lengths);
	}

	public PermuteRepeat(ArrayList<String> charset, ArrayList<String> start, int ... lengths)
			throws CharsetNullException, InvalidStartingStringLengthException, StartingStringCharactersNotPresentInCharsetException {
		if(charset == null) throw new CharsetNullException("Charset is empty");
		this.lengths = lengths;
		this.charset = charset;
		this.lastReached = false;
		
		if (start == null){
			currentLength = 0;
			index = new ArrayList<Integer>(lengths[currentLength]);
			zeroInitializeIndex();
		}else{
			index = new ArrayList<Integer>();
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

	private String numToStart(double n) { // TODO: turn a random number between 0 and 1 into starting password;
		return null;
	}

	/**
	 * Turns a String into an ArrayList of single character Strings or sequences
	 * of strings
	 * 
	 * @param s
	 * @return ArrayList of Strings of single characters
	 */
	private static ArrayList<String> stringToCharArrayList(String s) {
		if (s == null || s == "")
			return null;
		return stringToCharArrayList(s.toCharArray());
	}

	/**
	 * Turns characters or sequences of characters into a starting point
	 * Sequences must be enclosed in [] brackets
	 * 
	 * @param s
	 * @return
	 */
	private static ArrayList<String> stringToCharArrayList(char[] s) {
		//currently square brackets can't be in charset as they are reserved for sequences.
		//TODO: implement functionality to include square brackets too, e.g. by doubling them [[ ]]
		ArrayList<String> charArrayList = new ArrayList<String>(s.length);
		String temp = "";
		boolean sequence = false;
		for (char c : s) {
			if (c == '[') {
				sequence = true;
				continue;
			} else if (c == ']') {
				sequence = false;
				charArrayList.add(temp);
				temp = "";
				continue;
			} else{
				if(sequence) temp += c;
				else charArrayList.add(String.valueOf(c));
			}
	
		}
		return charArrayList;
	}

	private void zeroInitializeIndex() {
		index.clear();
		for (int i = 0; i < lengths[currentLength]; i++)
			index.add(0);
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
		 * brackets indicate where 'pointer' is currently pointing
		 */
		
		if(index.get(pointer).intValue()==charset.size()-1){ // if aa(d), i.e. last char reached
			do{
				index.set(pointer,0); 	//sets last char to first char from charset, example: aa(a)
				pointer--; 				//decrement pointer, example: a(a)a
				
				if(pointer==-1){ 		// last password reached, example: ()ddd
	
					  if(!nextLength()){ //max length exceeded, no more passwords
					   	lastReached = true;
					   	System.out.println("last reached");
					  	break;
					  }
	
					//recreate the index
					zeroInitializeIndex();
					break;
				}
			} while(index.get(pointer).intValue()==charset.size()-1);	//again, if last char reached at current position
	
			if(pointer!=-1){ //example: not ()ddd
				index.set(pointer,index.get(pointer)+1); 				//increment current, example: a(b)a 
				pointer = index.size()-1; 								//reset pointer to rightmost position, example: ab(a)
			}
			if(pointer==-1) //example: ()ddd
				pointer = index.size()-1; 								//reset pointer to rightmost position, example: ab(a)
			
		}else index.set(pointer, index.get(pointer)+1); 				//increment rightmost char, example: aa(b)
	}

	/**
	 * builds a string representation of current password
	 */
	@Override
	public String getCurrentPassword() {
		if (lastReached)
			return null;

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < index.size(); i++) {
			buf.append(charset.get(index.get(i)));
		}
		return buf.toString();
	}

	@Override
	public Boolean hasNext() {
		return !lastReached;
	}

	private boolean nextLength() {
		if (currentLength == lengths.length - 1) {
			return false;
		} else
			currentLength++;
		return true;
	}

	/**
	 * sets the index to the starting position
	 * 
	 * @param start
	 *            starting password value, it's length also dictates the length
	 *            of current password
	 * @throws InvalidStartingStringLengthException 
	 * @throws StartingStringCharactersNotPresentInCharsetException 
	 */
	private void setStart(ArrayList<String> start) throws InvalidStartingStringLengthException, StartingStringCharactersNotPresentInCharsetException {
		// set currentLength to the index of start's length
		for (int i = 0; i < lengths.length; i++) {
			if (lengths[i] == start.size())
				currentLength = i;
		}
		if (currentLength == null) {			//if the length of starting password doesn't match any of the lengths in lengths array
			throw new InvalidStartingStringLengthException("Starting password length and given lengths don't match");
		}
		for (String s : start) {				//get the index in elements of each element in 'start' ArrayList
			if(charset.indexOf(s) == -1){
				throw new StartingStringCharactersNotPresentInCharsetException("Starting password character/sequences not present in character set");
			}
			index.add(charset.indexOf(s));
		}
	}
}
