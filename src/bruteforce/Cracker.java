package bruteforce;

public class Cracker<T> {
	private String input;
	private Object target;
	
	/**
	 * 
	 * @param input target string pattern
	 * @param target eg zip
	 */
	//depending on input will decide whether to instantiate PermuteRepeat or Permute or dictionary
	public <T> Cracker(String input, Object target){
		this.input = input;		
		this.target = target;
		
	}

	public void crack(){
		
	}
}
