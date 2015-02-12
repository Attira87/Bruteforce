package bruteforce.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bruteforce.CharacterSets;
import bruteforce.CharsetNullException;
import bruteforce.Generator;
import bruteforce.InvalidStartingStringLengthException;
import bruteforce.PermuteRepeat;
import bruteforce.StartingStringCharactersNotPresentInCharsetException;
import bruteforce.ZeroLengthException;

public class GeneratorTest {

	Generator lowerCase, upperCase,digits,
	specialChars,customCharset,start,sequence,
	sequenceAndLowerCase, emptyCharset, nullCharset,
	invalidLength, from5to1, intArray, startSequence;
	
	@Before
	public void setUp() throws Exception {
		lowerCase = new PermuteRepeat(CharacterSets.lowerCase, null, 2, 4);
		upperCase = new PermuteRepeat(CharacterSets.upperCase, null, 3, 3);
		digits = new PermuteRepeat(CharacterSets.digits, null, 3, 4);
		specialChars = new PermuteRepeat(CharacterSets.special, null, 4, 4);
		customCharset = new PermuteRepeat("abcdef23456", null, 5, 5);
		start = new PermuteRepeat(CharacterSets.lowerCase, "start", 5, 5);
		sequence = new PermuteRepeat("[squirrel][cat][dog][awesome][love][10-02-2015]", null, 1, 4);
		sequenceAndLowerCase = new PermuteRepeat("[Jim][Andrew][Alfred]abcd", null, 4,5); // do this
		from5to1 = new PermuteRepeat("abcde", "eeeee", 5, 1);
		intArray = new PermuteRepeat("abcde", null, 1, 3, 5);
		startSequence = new PermuteRepeat("[spoon][fork][knife][chopsticks]", "[knife][chopsticks][fork]", 1, 3, 5);
	}

	@Test
	public void lowerCase2_4Test() {
		String password;
		String expected = "jhf";

		while((password = lowerCase.getNextPassword()) != null){
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);
	}
	@Test
	public void upperCase3_3Test(){
		String password;
		String expected = "TFH";

		while((password = upperCase.getNextPassword()) != null){
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);		
	}
	@Test
	public void digits3_4Test(){
		String password;
		String expected = "1385";

		while((password = digits.getNextPassword()) != null){
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);	
	}
	
	@Test
	public void specialCharsTest(){
		String password;
		String expected = "$%(@";

		while((password = specialChars.getNextPassword()) != null){
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);	
	}
	
	@Test
	public void customCharsetTest(){
		String password;
		String expected = "dcb54";

		while((password = customCharset.getNextPassword()) != null){
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);	
	}
	
	@Test
	public void startTest(){
		String password;
		String expected = "start";

		password = start.getNextPassword();
		
		assertEquals(expected, password);	
	}
	
	@Test
	public void sequenceTest(){
		String password;
		String expected = "awesomecatdog10-02-2015";

		while((password = sequence.getNextPassword()) != null){
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);	
	}
	@Test
	public void emptyCharsetTest() throws InvalidStartingStringLengthException, StartingStringCharactersNotPresentInCharsetException, ZeroLengthException{
		String actual;
		String expected = "bruteforce.CharsetNullException: Charset is empty";
		
		try{
			 emptyCharset = new PermuteRepeat("", null, 1, 1);
			 actual = "exception not thrown";
		}catch(CharsetNullException e){
			actual = e.toString();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void nullCharsetTest() throws InvalidStartingStringLengthException, StartingStringCharactersNotPresentInCharsetException, ZeroLengthException{
		String actual;
		String expected = "bruteforce.CharsetNullException: Charset is empty";
		
		try{
			 nullCharset = new PermuteRepeat(null, null, 1, 1);
			 actual = "exception not thrown";
		}catch(CharsetNullException e){
			actual = e.toString();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void from5to1Test(){
		String password;
		String expected = "aaaa";

		from5to1.nextPassword();
		password = from5to1.getNextPassword();
		assertEquals(expected, password);	
	}
	@SuppressWarnings("deprecation")
	@Test
	public void intArrayTest(){
		String password1;
		String password2;
		String expected1 = "aaa";
		String expected2 = "aaaaa";

		for(int i = 0; i<5; i++){
			intArray.nextPassword();
		}
		password1 = intArray.getNextPassword(); //aaa
		
		for(int i = 0; i<124; i++){
			intArray.nextPassword();
		}
		password2 = intArray.getNextPassword(); //aaaaa
	
		assertEquals(new String[]{expected1, expected2}, new String[]{password1,password2});

	}
	
	
	@Test
	public void sequenceAndLowerCase(){
		String password;
		String expected = "JimdAlfredAndrewc";

		while((password = sequenceAndLowerCase.getNextPassword()) != null){
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);	
	}
	
	@Test
	public void startSequenceTest(){
		String password;
		String expected = "knifechopsticksfork";

		password = startSequence.getNextPassword();
		
		assertEquals(expected, password);
	}
	
}
