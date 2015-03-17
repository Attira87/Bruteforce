package bruteforce;

public class HashCracker extends Cracker{

	public HashCracker(String hash, String hashType, String pattern,
			String dictType, String dictPath) throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException {
		super(pattern, dictType, dictPath);
	}

}
