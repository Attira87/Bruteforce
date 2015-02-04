package bruteforce;
/**
 * This class will test passwords against encrypted zip files.
 * @author Daniel
 *
 */
public class ArchiveTester implements Tester<Object>{
	
	public ArchiveTester(String filename) {
		
	}

	/** returns true if password is correct, false otherwise
	 * 
	 * @param password
	 * @return
	 */
	public boolean testPass(Object password){
		System.out.println((String)password); //for now
		return false;
	}

}
