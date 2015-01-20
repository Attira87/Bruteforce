package uk.ac.gla.psd2014.n.passcrack.scrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileScrapper implements Scrapper {
	private File file;

	public FileScrapper(File input) {
		this.file = input;
	}

	@Override
	public ArrayList<String> getPasswords() {
		ArrayList<String> output = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String[] words;
			String line;
			while ((line = br.readLine()) != null) {
				words = line.split("\\s+");
				for (int i = 0; i < words.length; i++) {
					output.add(words[i]);
				}
			}
			br.close();
			return output;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return output;
	}
}