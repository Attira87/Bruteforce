package bruteforce;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * This class will test passwords against encrypted zip files.
 * @author Daniel
 *
 */
public class ArchiveTester implements Tester<Object>{
	ZipFile target;
	public ArchiveTester(String filename) throws ZipException {
		target = new ZipFile(filename);
	}

	/** returns true if password is correct, false otherwise
	 * 
	 * @param password
	 * @return
	 */
	public boolean testPass(Object password){
		//System.out.println((String)password); //for now
		
		return false;
	}

}
