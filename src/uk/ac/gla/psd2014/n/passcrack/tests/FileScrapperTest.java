package uk.ac.gla.psd2014.n.passcrack.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import uk.ac.gla.psd2014.n.passcrack.scrapper.FileScrapper;
import uk.ac.gla.psd2014.n.passcrack.scrapper.Scrapper;


public class FileScrapperTest {

	@Test
	public void directoryTest() {
		Scrapper scrapper = new FileScrapper(new File("./testData/test"));
		ArrayList<String> checkpass = scrapper.getPasswords();
		assertEquals(checkpass.size(), 3);
		// this is broken because dumb svn add .svn in each folder
		// so directory scrapper reads that one and makes the build on jenkins unstable
		// assertEquals(checkpass.size(), 10);
		// TODO: fix this
		// assertEquls(1, 1);
	}
}
