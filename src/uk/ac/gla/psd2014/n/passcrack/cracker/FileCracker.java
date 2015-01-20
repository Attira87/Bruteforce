package uk.ac.gla.psd2014.n.passcrack.cracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileCracker implements Cracker {
	
	String inputFile;
	
	public FileCracker(String fileName) {
		inputFile = fileName;
	}
	

	@Override
	public boolean tryPassword(String password) throws CrackerException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {
			String currentLine;
			if ((currentLine = bufferedReader.readLine()) != null) {
				return (currentLine.equals(hash(password)));
			}
		} catch (IOException e) {
			throw new CrackerException("Could not read target file", e);
		}
		
		return false;
	}
	
	
	private String hash(String message) {
		String hashVal = null;
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte [] hash = messageDigest.digest(message.getBytes("US-ASCII"));
			
			//convert byte array to hex string
			StringBuilder stringBuilder = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				stringBuilder.append(String.format("%02x", b&0xff));
			}
			
			hashVal = stringBuilder.toString();		
		} catch (UnsupportedEncodingException ex) {
			//TODO : implement exception
		} catch (NoSuchAlgorithmException ex) {
			//TODO : implement exception
		}
		
		return hashVal; 
	}
}
