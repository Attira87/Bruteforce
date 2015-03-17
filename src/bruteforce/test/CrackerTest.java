package bruteforce.test;

import static org.junit.Assert.*;
import org.junit.Test;

import bruteforce.CharsetNullException;
import bruteforce.Cracker;
import bruteforce.InvalidStartingStringLengthException;
import bruteforce.StartingSequenceNotPresentInCharsetException;

public class CrackerTest {

	Cracker cracker;
	
	@Test
	public void crackerTest() throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException{
		cracker = new Cracker("a2a3", null, null);
	}
}
