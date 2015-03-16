package bruteforce;

import java.util.ArrayList;

public abstract class PermutationAlgorithm implements Generator{
	protected ArrayList<String> charset;		//stores characters or character sequences
	protected ArrayList<Integer> index;			//stores the indices of characters or character sequences in charset
	protected int [] lengths;					//lengths of passwords to generate
	protected Integer currentLength;			//stores the index in lengths[] of current length
	protected boolean lastReached;
	
	
	public PermutationAlgorithm(ArrayList<String> charset, int ... lengths) throws CharsetNullException{
		if(charset == null) throw new CharsetNullException("Charset is empty");
		this.charset = charset;
		this.lengths = lengths;
		lastReached = false;
	}
	
	/**
	 * Turns a String into an ArrayList of single character Strings or sequences
	 * of strings
	 * 
	 * @param s
	 * @return ArrayList of Strings of single characters
	 */
	protected static ArrayList<String> stringToCharArrayList(String s) {
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
	protected static ArrayList<String> stringToCharArrayList(char[] s) {
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
	
	/**
	 * sets the index to the starting position
	 * 
	 * @param start
	 *            starting password value, it's length also dictates the length
	 *            of current password
	 * @throws InvalidStartingStringLengthException 
	 * @throws StartingSequenceNotPresentInCharsetException 
	 */
	protected void setStart(ArrayList<String> start) throws InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException {
		// set currentLength to the index of start's length
		for (int i = 0; i < lengths.length; i++) {
			if (lengths[i] == start.size())
				currentLength = i;
		}
		if (currentLength == null) {			//if the length of starting password doesn't match any of the lengths in lengths array
			throw new InvalidStartingStringLengthException("Starting password length and given length/s don't match");
		}
		
		index = new ArrayList<Integer>(lengths[currentLength]);
		
		for (String s : start) {				//get the index in elements of each element in 'start' ArrayList
			if(charset.indexOf(s) == -1){
				throw new StartingSequenceNotPresentInCharsetException("Starting password character/sequences not present in character set");
			}
			index.add(charset.indexOf(s));
		}
	}
	

	@Override
	public Boolean hasNext() {
		return !lastReached;
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
	 * builds a string representation of current password
	 */
	@Override
	public String getCurrentPassword() {
		if (lastReached)
			return null;

		StringBuffer buf = new StringBuffer();
		
//		for (int i = 0; i < index.size(); i++) {
//			buf.append(index.get(i));
//		}
//		buf.append(" ");
		
		
		for (int i = 0; i < index.size(); i++) {
			buf.append(charset.get(index.get(i)));
		}
		return buf.toString();
	}
}
