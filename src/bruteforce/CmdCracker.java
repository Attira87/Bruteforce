package bruteforce;

public class CmdCracker extends Cracker{

	public CmdCracker(String cmd, String pattern, String dictType,
			String dictPath) throws CharsetNullException, InvalidStartingStringLengthException, StartingSequenceNotPresentInCharsetException {
		super(pattern, dictType, dictPath);
	}

}
