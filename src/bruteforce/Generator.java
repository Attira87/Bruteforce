package bruteforce;

public interface Generator {
	public String getNextPassword();
	public String getCurrentPassword();
	public Boolean hasNext();

}
