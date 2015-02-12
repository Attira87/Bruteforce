package bruteforce;

public interface Generator {
	public String getNextPassword();
	public void nextPassword();
	public String getCurrentPassword();
	public Boolean hasNext();

}
