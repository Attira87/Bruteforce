package bruteforce.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bruteforce.PassGen;


public class PassGenTest {

	PassGen myPassGen;
	
	
	@Before
	public void setUp() throws Exception {
		myPassGen = new PassGen("abcd".toCharArray(), 4, 4);
		
	}

	@Test
	public void test() {
		String actual = myPassGen.nextPassword();
		String expected = "aaaa";
		assertEquals(expected, actual);
		
		actual = myPassGen.nextPassword();
		expected = "aaab";
		assertEquals(expected, actual);
	}
	
	@After
	public void tearDown() throws Exception {
	}



}
