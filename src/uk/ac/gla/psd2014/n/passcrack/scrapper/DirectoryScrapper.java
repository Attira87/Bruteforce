package uk.ac.gla.psd2014.n.passcrack.scrapper;
 
import java.io.File;
import java.util.ArrayList;

import uk.ac.gla.psd2014.n.passcrack.cracker.Cracker;
 
public class DirectoryScrapper implements Scrapper {
 
    private ArrayList<String> passwords;
    private String targetDir;
    // can be used to address specific file types
    private String pattern;
 
    public DirectoryScrapper(String dir) {
        this.targetDir = dir;
        passwords = new ArrayList<String>();
    }
 
    @Override
    public ArrayList<String> getPasswords() {
        File curDir = new File(this.targetDir);
        getAllFilse(curDir);
        return this.passwords;
    }
 
    private void getAllFilse(File curDir) {
        FileScrapper fileScrapper;
        File[] filesList = curDir.listFiles();
        for (File file : filesList) {
            if (file.isDirectory())
                getAllFilse(file);
            // runs only if there is a text file to read
            if (file.isFile()) {
                fileScrapper = new FileScrapper(file);
                /*
                for (String i : ftoScrap.getPasswords())
                    passwords.add(i);
                 */
                passwords.addAll(fileScrapper.getPasswords());
            }
        }
 
    }
 
}