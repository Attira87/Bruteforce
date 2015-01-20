package uk.ac.gla.psd2014.n.passcrack.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.ac.gla.psd2014.n.passcrack.cracker.FileCracker;

public class FileCrackerTest {

	@Test
	public void testTargetFile() {
		String filename = "./target_suscipit.txt";
		FileCracker fileCracker = new FileCracker(filename);

		try {
			boolean resultFalse = fileCracker.tryPassword("wrongPasswordHere");

			assertFalse("Matched wrong password!", resultFalse);

			boolean resultTrue = fileCracker.tryPassword("suscipit");

			assertTrue("Could not match right password!", resultTrue);
		} catch (Exception e) {
			fail("could not match password!");
		}

	}
}