package bruteforce;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;


public class Bruteforce {
	static Process pr;
	static Runtime run;
	static char [] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	static char [] numbers = "0123456789".toCharArray();
	static char [] testChar = "qwerty".toCharArray();
	static char [] letters = "abcd".toCharArray();
	static char [] testnum = "123".toCharArray();
	static String command, filename, platform, line, start;
	static String usageString = "Usage: bruteforce [-options] [arguments]"
			+ "\noptions:\n-gen\t\tgenerate passwords\n-bf\t\tbrute force attack a file\n-entropy\tcalculate entropy";
	public static void main(String[] args) throws IOException, InterruptedException{
		
		/* Below is a list of commands to run to extract and verify zip files with unzip (on Unix) and 7zip (on Unix and Windows)
		 * to extract 1.zip with pass qwerty2 with 7zip:	"7za x " + "1.zip" + " -pqwerty2 -y", x extract, -p password(no space), -y yes to all prompts
		 * to extract 1.zip with pass qwerty2 with unzip:	"unzip -oP qwerty2 1.zip", o forces overwrite, P password
		 * 
		 * 
		 * For testing passwords will only need to verify and not extract.
		 * to verify 1.zip with pass qwerty2 with 7zip:		"7za t 1.zip -pqwerty2"
		 * to verify 1.zip with pass qwerty2 with unzip:	"unzip -tq -P qwerty2 1.zip"
		 * 
		 * linux/mac - command = "unzip -tq -P PASSWORD filename";
		 * windows (7zip) - command = "7za t " + filename + " -pPASSWORD";
		 */
		
		
		if(args.length == 0)System.out.println(usageString);
		else{
			filename = args[1];
			platform = System.getProperty("os.name").toLowerCase();
			System.out.println(platform);
			
			if(args.length == 5) start = args[4];
			else start = "";
			/*
			 * 0 - option, 1 - zipfile, 2 - min pass length, 3 - max pass length
			 */
			
			switch(args[0]){
			case "-gen":	//execute gen method ;
							break;
			case "-bf":		
							PassGen myPassGen = new PassGen(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
							ArchiveTester archiveTester = new ArchiveTester(filename);
							boolean found = false;
							String password = "";
							while((password = myPassGen.nextPassword()) != null){
								found = archiveTester.testPass(password);
								if(found){
									System.out.printf("Password found: %s", password);
									break;
								}
								
							}
							System.out.println("Password not found");
							//nPr(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
							//printAll(letters, Integer.parseInt(args[2]),Integer.parseInt(args[3]), start);

							break;
			case "-bf--7zip":	command = "7za t " + filename + " -p"; 	//tests the archive without unpacking, a bit faster
								//command = "7za x " + filename + " -p";	//unpacks the archive, slower 
								//nPr(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
							printAll(letters, Integer.parseInt(args[2]),Integer.parseInt(args[3]), start);

							break;
			case "-bf--unzip":	command = "unzip -tq -P ";	//force unzip
							//nPr(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
							//printAll(letters, Integer.parseInt(args[2]),Integer.parseInt(args[3]), start);
							break;
			case "-bf--dict": //dictionary attack
							break;
			case "-entropy"://execute entropy method;
							break;
			default: 		System.out.println(usageString);
							break;
			}
		}
			
		
		
		
	}

	//From http://stackoverflow.com/questions/13157656/permutation-of-an-array-with-repetition-in-java
	//Most likely won't use
	public static void printAll(char[] c, int min, int max, String start) throws IOException{
		
		int loop; //stores the starting size of password
		if(start=="") loop = min;
		else loop = start.length();
			
		for(int i = loop; i<=max; ++i){
			printAll(c, i, start);
		}
	}
	
	public static void printAll(char[] c, int n, String start) throws IOException{
		  if(start.length() >= n){
			  System.out.println(start);
			  if(platform.contains("linux") || platform.contains("mac")){
				  //pr = run.exec(command+start + " " + filename);
			  }
			  else if(platform.contains("windows")){
					  pr = run.exec(command+start + " -y");
			  }
				
		  }else{
		    for(char x: c){ 
		      printAll(c, n, start+x);
		    }
		  }
		}
	
	
	//not using these at the moment, may use in the future for non repeated characters 
	public static void permutation(String str) throws IOException { 
	    permutation("", str); 
	}

	private static void permutation(String prefix, String str) throws IOException {
	    int n = str.length();
	   // if (n == 0) System.out.println(command + prefix + " " + "file.zip");
	    if(n==0){
	    	pr = run.exec(command + prefix + " " + "file.zip");
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));

			
			while(( line = buf.readLine()) != null ) 
			{
			  //System.out.println(line);
			  if(line.contains("No errors detected")) {System.out.println("SUCCESS!!!");}
			}
	    	}
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
	
	
	
}
