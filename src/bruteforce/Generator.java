package bruteforce;

public interface Generator {
	public Object getNextPassword();
	public void nextPassword();
	public Object getCurrentPassword();
	public Boolean hasNext();
	public void reset();

}
