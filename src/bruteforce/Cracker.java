package bruteforce;

import java.util.ArrayList;


public class Cracker{
	String pattern;
	//Pattern pattern;
	String dictType;
	String dictPath;
	ArrayList<Generator> generators;
	
	boolean seq, perm, concat, lengthStart;
	String charset, sequence;
	ArrayList<Integer> digits, lengths;

	public <T> Cracker(String pattern, String dictType, String dictPath) throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException{
		//this.pattern = new Pattern(pattern);
		this.pattern = pattern;
		this.dictType = dictType;
		this.dictPath = dictPath;
		generators = new ArrayList<Generator>();
		parsePattern();

		int size = generators.size();
		int pointer = size - 1;
		System.out.println(size);
		String s = "";
		
		/*This is really really bad, after spending hours on this, I just gave up. At least it works. */
		while(true){
			for(int i = 0; i < size - 1; i++){
				s += generators.get(i).getCurrentPassword();
			}
			s += generators.get(pointer).getNextPassword();
			
			System.out.println(s);
			s = "";
			
			while(!generators.get(pointer).hasNext()){
				generators.get(pointer).reset();
				pointer = pointer - 1;

				System.out.println(pointer);
				generators.get(pointer).nextPassword();
				if(pointer == 0) break;

			}
			if(!generators.get(0).hasNext()){
				break;
			}
			pointer = size - 1;
		}

	}
		
		
		
	
	private void parsePattern() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException{
		
		seq = false;
		perm = false;
		concat = false;
		lengthStart = false;
		
		charset = "";
		sequence = "";
		
		digits = new ArrayList<Integer>(3);
		lengths = new ArrayList<Integer>();
		
		for(char c: pattern.toCharArray()){
			//if(!Character.isDigit(c))
			
			if(c == '{'){ perm = true; /*continue;*/}
			else if(c == '['){ seq = true; /*continue;*/}

			else if(c == '+'){ concat = true; continue;} // <---remember this
			
			if(!seq && !perm){
				if(c == ',') { //if comma, concatenate multi-digit lengths
//					String s = "";
//					for(Integer i: digits){
//						s += i;
//					}
					lengths.add(concatLengths(digits)); 
					digits = new ArrayList<Integer>(3);
					lengthStart = false;
					continue;
					
				}
				if(c == 'a'){ if(lengthStart && !concat){prepGenerator();} charset += CharacterSets.lowerCase; continue;}
				else if(c == 'A'){ if(lengthStart && !concat){prepGenerator();} charset += CharacterSets.upperCase; continue;}
				else if(c == 'n'){ if(lengthStart && !concat){prepGenerator();} charset += CharacterSets.digits; continue;}
				else if(c == 's'){ if(lengthStart && !concat){prepGenerator();} charset += CharacterSets.special; continue;}
				//else if(c == 'd') //dictionary word
				else if(Character.isDigit(c)){ digits.add(Character.getNumericValue(c)); lengthStart = true;}

				//lengths.add(Character.getNumericValue(c));
			}else if(seq && !perm){
				if(c == ']'){
					seq = false;
					//charset += c;
					//continue;
				}
				//charset += c;
				//System.out.println("worked");
			}
			else if(perm && !seq){
				if(!(c == '{' || c == '}')){
					charset += c;					
				}else{
					
				}
				
			}
			else if(perm && seq){
				
			}
			
			if(c == ']'){ seq = false; /*continue;*/}
			else if(c == '}'){ perm = false; /*continue;*/}
			
		}
		System.out.println("charset: " + charset + " lengths: " + lengths.toString() + " digits: " + digits.toString());
		if(lengthStart) prepGenerator();
		
	}
	
	private Integer concatLengths(ArrayList<Integer> digits){
		String s = "";
		for(Integer i: digits){
			s += i;
		}
		//System.out.println(Integer.parseInt(s));
		
		return Integer.parseInt(s);
	}
	
	private void prepGenerator() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException{
		lengths.add(concatLengths(digits));
		makeGenerator(charset, lengths);
		charset="";
		lengthStart=false;
		concat = false;
		lengths = new ArrayList<Integer>();
		digits = new ArrayList<Integer>(3);
	}
	
	private void makeGenerator(String charset, ArrayList<Integer> lengths) throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException{
		int intLengths [] = new int [lengths.size()];
		for(int i = 0; i < lengths.size(); i++){
			intLengths[i] = lengths.get(i).intValue();
		}
		generators.add(new PermuteRepeat(charset, (String)null, intLengths));
	}

	
}
