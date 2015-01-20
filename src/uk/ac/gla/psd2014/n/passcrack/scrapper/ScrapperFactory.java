package uk.ac.gla.psd2014.n.passcrack.scrapper;

import java.io.File;
 
public class ScrapperFactory {

    public Scrapper getScrapper(String source) throws ScrapperException {

        File file = new File(source);
        
        if (file.isDirectory()) {
        	return new DirectoryScrapper(source);
        } else if (file.isFile()) {
        	return new FileScrapper(file);
        } else  {
        	throw new ScrapperException("Unrecognised source");
        }
    } 
}
