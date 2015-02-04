package bruteforce.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bruteforce.Generator;
import bruteforce.PermuteRepeat;

public class GeneratorTest {

	Generator letters;
	Generator numbers;
	
	@Before
	public void setUp() throws Exception {
		letters = new PermuteRepeat("abcd".toCharArray(), 4, 4);
		numbers = new PermuteRepeat("1234".toCharArray(), 4, 4);
	}

	@Test
	public void lettersTest() {
		String actual = letters.getNextPassword();
		String expected = "aaaa";
		assertEquals(expected, actual);
	}
	@Test
	public void numbersTest(){
		String actual = numbers.getNextPassword();
		String expected = "1111";
		assertEquals(expected, actual);
	}

}
