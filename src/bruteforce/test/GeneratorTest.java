package bruteforce.test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import bruteforce.CharacterSets;
import bruteforce.CharsetNullException;
import bruteforce.Generator;
import bruteforce.IntegerStartGreaterThanNoOfPermutationsException;
import bruteforce.InvalidStartingStringLengthException;
import bruteforce.Permute;
import bruteforce.PermuteRepeat;
import bruteforce.RandomPassword;
import bruteforce.SequenceOccurrenceMismatchException;
import bruteforce.StartingSequenceNotPresentInCharsetException;
import bruteforce.ZeroLengthException;

public class GeneratorTest {

	Generator permuteRepeat;
	Generator permute;
	Generator randomGenerator;

//	@Test
//	public void randomPasswordGeneratorTest() throws CharsetNullException{
//		randomGenerator = new RandomPassword(CharacterSets.lowerCase + CharacterSets.upperCase + CharacterSets.special + CharacterSets.digits, 10);
//		System.out.println(randomGenerator.getNextPassword());
//	}
	
//	@Test
//	public void setRandomStartTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, IntegerStartGreaterThanNoOfPermutationsException{
//		permuteRepeat = new PermuteRepeat(CharacterSets.lowerCase, BigInteger.valueOf(-1), 10);
//		System.out.println(permuteRepeat.getNextPassword());
//
//	}

	
	@Test
	public void setIntTest() throws CharsetNullException, IntegerStartGreaterThanNoOfPermutationsException{
		PermuteRepeat setInteger = new PermuteRepeat("abcdefghijklmnopqwer", BigInteger.valueOf(3199999), 5);
		String password = "";
		String expected = "rrrrr";
		while((password = (String) setInteger.getNextPassword()) != null){
			//System.out.println(password);
			if(password.equals(expected)) break;
			
		}
		assertEquals(expected, password);
	}
	
	@Test
	public void permSetStartTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, SequenceOccurrenceMismatchException, IntegerStartGreaterThanNoOfPermutationsException{
		permute = new Permute("abcdefg", 5039);
		String expected = "gfedcba";
		String password;
		password = (String) permute.getNextPassword();
		assertEquals(expected,password);

	}
	
	
	public void runPermute(String expected){
		String password;
		while((password = (String) permute.getNextPassword()) != null){
			//System.out.println(password);
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);
	}
	
	public void runPermRepeat(String expected){
		String password;
		while((password = (String) permuteRepeat.getNextPassword()) != null){
			//System.out.println(password);
			if(password.equals(expected)) break;
		}
		assertEquals(expected, password);
	}
	
	@Test
	public void lowerCase2_4Test() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException {
		permuteRepeat = new PermuteRepeat(CharacterSets.lowerCase, null, 2, 4);
		String expected = "jhf";
		runPermRepeat(expected);
	}
	@Test
	public void upperCase3_3Test() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		permuteRepeat = new PermuteRepeat(CharacterSets.upperCase, null, 3, 3);
		
		String expected = "TFH";

		runPermRepeat(expected);	
	}
	@Test
	public void digits3_4Test() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		permuteRepeat = new PermuteRepeat(CharacterSets.digits, null, 3, 4);
		String expected = "1385";
		runPermRepeat(expected);
	}
	
	@Test
	public void specialCharsTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		permuteRepeat = new PermuteRepeat(CharacterSets.special, null, 4, 4);
		String expected = "$%(@";
		runPermRepeat(expected);
	}
	
	@Test
	public void customCharsetTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		permuteRepeat = new PermuteRepeat("abcdef23456", null, 5, 5);
		String expected = "dcb54";
		runPermRepeat(expected);	
	}
	
	@Test
	public void startTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		permuteRepeat = new PermuteRepeat(CharacterSets.lowerCase, "start", 5, 5);
		String expected = "start";
		runPermRepeat(expected);	
	}
	
	@Test
	public void sequenceTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		permuteRepeat = new PermuteRepeat("[squirrel][cat][dog][awesome][love][10-02-2015]", null, 1, 4);
		String expected = "awesomecatdog10-02-2015";
		runPermRepeat(expected);
	}
	@Test
	public void emptyCharsetTest() throws InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException, CharsetNullException{

		String actual;
		String expected = "bruteforce.CharsetNullException: Charset is empty";
		
		try{
			permuteRepeat = new PermuteRepeat("", null, 1, 1);
			actual = "exception not thrown";
		}catch(CharsetNullException e){
			actual = e.toString();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void nullCharsetTest() throws InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		String actual;
		String expected = "bruteforce.CharsetNullException: Charset is empty";
		
		try{
			permuteRepeat = new PermuteRepeat(null, null, 1, 1);
			actual = "exception not thrown";
		}catch(CharsetNullException e){
			actual = e.toString();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void from5to1Test() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		permuteRepeat = new PermuteRepeat("abcde", "eeeee", 5, 1);
		String password;
		String expected = "aaaa";

		permuteRepeat.nextPassword();
		password = (String) permuteRepeat.getNextPassword();
		assertEquals(expected, password);	
	}
	@SuppressWarnings("deprecation")
	@Test
	public void intArrayTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException{
		permuteRepeat = new PermuteRepeat("abcde", null, 1, 3, 5);
		String password1;
		String password2;
		String expected1 = "aaa";
		String expected2 = "aaaaa";

		for(int i = 0; i<5; i++){
			permuteRepeat.nextPassword();
		}
		password1 = (String) permuteRepeat.getNextPassword(); //aaa
		
		for(int i = 0; i<124; i++){
			permuteRepeat.nextPassword();
		}
		password2 = (String) permuteRepeat.getNextPassword(); //aaaaa
	
		assertEquals(new String[]{expected1, expected2}, new String[]{password1,password2});

	}
	
	@Test
	public void twoLengthsTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException{
		//check that length changes from 3 to 5 and doesn't go to 4
		int [] lengths = {3,5};
		permuteRepeat = new PermuteRepeat("abcdefg", "ggg", lengths);
		String expected = "aaaaa";
		String password = (String) permuteRepeat.getNextPassword();
		password = (String) permuteRepeat.getNextPassword();
		
		assertEquals(expected, password);
	}
	
	
	@Test
	public void sequenceAndLowerCase() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException, ZeroLengthException, IntegerStartGreaterThanNoOfPermutationsException{
		permuteRepeat = new PermuteRepeat("[Jim][Andrew][Alfred]abcd", (String)null, 5);
		String expected = "JimdAlfredAndrewc";
		runPermRepeat(expected);
	}
	
	@Test
	public void startSequenceTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException{
		permuteRepeat = new PermuteRepeat("[spoon][fork][knife][chopsticks]", "[knife][chopsticks][fork]", 1, 3, 5);
		String password;
		String expected = "knifechopsticksfork";

		password = (String) permuteRepeat.getNextPassword();
		
		assertEquals(expected, password);
	}
	
}
