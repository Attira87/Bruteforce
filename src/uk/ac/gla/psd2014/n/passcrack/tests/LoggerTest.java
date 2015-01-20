package uk.ac.gla.psd2014.n.passcrack.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import uk.ac.gla.psd2014.n.passcrack.logger.Logger;

import org.junit.*;

public class LoggerTest{
	String target;
	String password = "";
	File logFailFile;
	File logSuccessFile;
	File logTryFile;
	boolean success;
	
	Logger logger = new Logger();
	
	@Before
	public void setup() throws Exception {
		logFailFile = new File("./log_cracker.txt");
		logSuccessFile = new File("./log_cracker.txt");
		logTryFile = new File("./log_cracker_detailed.txt");

		target = "./TargetTest.txt";
		password = "bo720jk10lo11";

		success = true;
	}	

	
	@Test
	public void loggFailTest() {
		logger.logFailure(target, 1L, 1);
		String line = "Could not crack";
		System.out.println(line);
		Scanner sc;
		try {
			sc = new Scanner(logFailFile);
			String fLine = sc.nextLine();
			System.out.println(fLine);
			assertTrue(fLine.contains(line));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			fail("file was not created!");
		}		
	}


	@Test
	public void loggSuccessTest() {
		logger.logSuccess(target, password, 1L, 1);
		String line = target + " cracked successfully. Password: " + password;
		System.out.println(line);
		Scanner sc;
		try {
			sc = new Scanner(logSuccessFile);
			String currentLine = sc.nextLine();
			System.out.println(currentLine);
			assertTrue(currentLine.contains(line));
		} catch (FileNotFoundException e) {
			fail("file was not created!");
		}		
	}
	

	@Test
	public void loggTryTest() {		
		logger.logTry(success, password);
		String line =  password + ": successful";
		System.out.println(line);
		Scanner sc;
		try {
			sc = new Scanner(logTryFile);
			String currentLine = sc.nextLine();
			System.out.println(currentLine);
			assertTrue(currentLine.contains(line));
		} catch (FileNotFoundException e) {
			fail("file was not created!");
		}		
	}
}
	
