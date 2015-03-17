package bruteforce;

import java.util.ArrayList;

public class RandomPassword extends PermutationAlgorithm implements Generator{
	private String password;

	public RandomPassword(String charset, int length) throws CharsetNullException{
		this(stringToCharArrayList(charset), length);
	}
	
	
	public RandomPassword(ArrayList<String> charset, int length) throws CharsetNullException {
		super(charset,length);
		password = "";
	}


	@Override
	public String getNextPassword() {
		nextPassword();
		return getCurrentPassword();
	}

	@Override
	public void nextPassword() {
		password = "";
		for(int i = 0; i < lengths[0]; i++){
			password += charset.get((int) (Math.random() * charset.size()));
		}
		
	}

	@Override
	public String getCurrentPassword() {
		return password;
	}

	@Override
	public Boolean hasNext() {
		return true;
	}
	
	public void setLength(int length){
		lengths[0] = length;
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
