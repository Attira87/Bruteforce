package bruteforce;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ZipCracker extends Cracker{
	ZipFile zipFile;

	public ZipCracker(String filename, String pattern, String dictType,
			String dictPath) throws ZipException {
		super(pattern, dictType, dictPath);
		zipFile = new ZipFile(filename);
	}
	
	public void Crack(){
		
		try{
			zipFile.setPassword("qwerty");
			zipFile.extractAll("out//");
		}catch(ZipException e){
			
		}
		
	}

}
