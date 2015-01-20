package uk.ac.gla.psd2014.n.passcrack.cracker;

import java.io.File;


public class CrackerFactory {
	
	/**
	 * Returns the instance of cracker depending on the selected source
	 * 
	 * @param target Target to crack
	 * @return Returns instance of Cracker
	 * @throws CrackerException Throws exception if the target is not recognized
	 */
	public Cracker getCracker(String target) throws CrackerException {
		
		try {
			File file = new File(target);
		} catch (Exception e) {
			throw new CrackerException("Target file does not exist", e);
		}
		
		FileCracker newCracker = new FileCracker(target);
		return newCracker;
	}
}
