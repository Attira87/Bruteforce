package bruteforce;

public class Cracker<T> {
	private String input;
	private Object target;
	
	/**
	 * 
	 * @param input target string pattern
	 * @param target 
	 */
	public <T> Cracker(String input, Object target){
		this.input = input;
		this.target = target;
	}

	
}
