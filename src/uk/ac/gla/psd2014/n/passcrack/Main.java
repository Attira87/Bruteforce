package uk.ac.gla.psd2014.n.passcrack;
 
import java.util.ArrayList;

import uk.ac.gla.psd2014.n.passcrack.cracker.Cracker;
import uk.ac.gla.psd2014.n.passcrack.cracker.CrackerException;
import uk.ac.gla.psd2014.n.passcrack.cracker.CrackerFactory;
import uk.ac.gla.psd2014.n.passcrack.logger.Logger;
import uk.ac.gla.psd2014.n.passcrack.scrapper.Scrapper;
import uk.ac.gla.psd2014.n.passcrack.scrapper.ScrapperException;
import uk.ac.gla.psd2014.n.passcrack.scrapper.ScrapperFactory;
 
public class Main {
    private boolean detailedLog;
    private Cracker cracker;
    private Scrapper scrapper;
    private String target;
     
    public static void main(String[] args) {
        try {
            Main main = new Main(args);
            main.crack();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
            System.exit(-1);
        } catch (CrackerException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (ScrapperException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
     
     
    /**
     * Cracks the password
     */
    public void crack() {
        long start = System.currentTimeMillis();
        int tries = 0;
        Logger logger = new Logger();
         
        ArrayList<String> dictionary = scrapper.getPasswords();      
         
        for (String password : dictionary) {
            boolean wasSuccessful = false;
            tries++;
            try {
                wasSuccessful = cracker.tryPassword(password);
            } catch (CrackerException e) {
                logger.logFailure(target, (System.currentTimeMillis() - start), tries);
                System.out.println("Fatal error:" + e.getMessage());
                System.out.println("Terminating");
                return;
            }
             
            if (detailedLog) {
                logger.logTry(wasSuccessful, password);
            }
             
            if (wasSuccessful) {
                logger.logSuccess(target, password, (System.currentTimeMillis() - start), tries);
                return;
            }
        }
         
        logger.logFailure(target, (System.currentTimeMillis() - start), tries);
    }
 
     
    /**
     * Creates the appropriate fields depending on the CLI input
     *
     * @param args CLI arguments
     * @throws IllegalArgumentException When the format of CLI arguments is wrong
     * @throws CrackerException When the cracker could not be created
     * @throws ScrapperException When the scrapper could not be created
     */
    public Main(String[] args) throws IllegalArgumentException, CrackerException, ScrapperException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Not enough arguments");
        }
         
        if (args.length > 3) {
            throw new IllegalArgumentException("Too many arguments");
        }
         
        detailedLog = false;
        if (args.length == 3) {
            String flag = args[2];
            if (flag.equals("-d")) {
                detailedLog = true;
            } else {
                throw new IllegalArgumentException("Could not understand flag: " + flag);  
            }
        }
         
        String dictionarySource = args[0];
        target = args[1];
         
        ScrapperFactory scrapperFactory = new ScrapperFactory();
        scrapper = scrapperFactory.getScrapper(dictionarySource);
         
        CrackerFactory crackerFactory = new CrackerFactory();
        cracker = crackerFactory.getCracker(target);
    }
}
