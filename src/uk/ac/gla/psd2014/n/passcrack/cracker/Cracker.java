package uk.ac.gla.psd2014.n.passcrack.cracker;

public interface Cracker {
	public boolean tryPassword(String password) throws CrackerException;
}
