package uk.ac.gla.psd2014.n.passcrack.cracker;


public class CrackerException extends Exception {
	private static final long serialVersionUID = 1L;

	public CrackerException(String message, Exception e) {
		super(message, e);
	}
}
