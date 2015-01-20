package uk.ac.gla.psd2014.n.passcrack.logger;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
 
public class Logger {
    private static final String FILENAME = "log_cracker.txt";
    private static final String FILENAME_DETAILED = "log_cracker_detailed.txt";
 
     
    public Logger() {
        try {
            File file = new File(FILENAME);
            File fileDetailed = new File(FILENAME_DETAILED);
 
            if (!file.delete()) {
                // TODO: implement
            }
             
            if (!fileDetailed.delete()) {
                // TODO: implement
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     
    public void logFailure(String target, long milliseconds, int tries) {
        String string = "Could not crack " + target + " (run for: " + milliseconds + " milliseconds; tried " + tries + " passwords)\n";
        appendToFile(FILENAME, string);
    }
     
 
    public void logSuccess(String target, String password, long milliseconds, int tries) {
        String string = target + " cracked successfully. Password: " + password + " (run for: " + milliseconds + " milliseconds; tried " + tries + " passwords)\n";
        appendToFile(FILENAME, string);
    }
     
 
    public void logTry(boolean success, String password) {
        String string = password + ": " + (!success ? "un" : "") + "successful\n";
        appendToFile(FILENAME_DETAILED, string);
    }
     
     
    private boolean appendToFile(String filename, String line) {
        try {
 
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
 
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(line);
            bufferWritter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
