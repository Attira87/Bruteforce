import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

/*
 * This program is designed to run on Linux, Windows or Mac.
 * It uses 7zip on Windows and unzip on Linux and Mac. The desired zip tool can also be forced with command line arguments
 */
		//-gen length : generate passwords
		//-bf : brute force attack a file

/*
 * cases for brute force:
 * 	n size charset, length L:
 * 	-find all combinations of length L only
 * 	-find all combinations of all lengths from 1 to L
 * 
 * 
 */
public class bruteforce {
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
			case "-bf":		if(platform.contains("linux") || platform.contains("mac")){
								command = "unzip -tq -P ";
								//nPr(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
								printAll(letters, Integer.parseInt(args[2]),Integer.parseInt(args[3]), start);
								
							}else if(platform.contains("windows")){
								command = "7za t " + filename + " -p";
								//nPr(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
								printAll(letters, Integer.parseInt(args[2]),Integer.parseInt(args[3]), start);
							}else System.out.println("Unsupported OS.");
							break;
			case "-bf--7zip":	command = "7za t " + filename + " -p"; 	//tests the archive without unpacking, a bit faster
								//command = "7za x " + filename + " -p";	//unpacks the archive, slower 
								//nPr(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
							printAll(letters, Integer.parseInt(args[2]),Integer.parseInt(args[3]), start);

							break;
			case "-bf--unzip":	command = "unzip -tq -P ";	//force unzip
							//nPr(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
							printAll(letters, Integer.parseInt(args[2]),Integer.parseInt(args[3]), start);
							break;
			case "-entropy"://execute entropy method;
							break;
			default: 		System.out.println(usageString);
							break;
			}
		}
			
		
		
		
	}
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
	
	public static void nPr(char [] c, int min, int max) throws IOException, InterruptedException{
		//TO DO: IMPLEMENT STARTING PASSWORD OPTION AND PLUG INTO THE INDEX ARRAYLIST
//		int maxlength = 10;
//		int minLength = 10;
		
		
		for(int i = min; i<=max; i++){
			//An ArrayList of ints is used to store the int representation of the current permutation
			//and index into the char array to retrieve the current state.
			ArrayList<Integer> index = new ArrayList<Integer>(c.length);		
			for(int j = 0; j<i;j++) index.add(0);
			
			//this function is called for each password length
			nPr(c, index);
		}
		
	
	}
	
	//for charset c and length x the nPr method needs to be called x times with lengths x-(x-1), x-(x-2)....x-(x-x)
	//e.g. for charset abcd and password length 3 nPr needs to be called 3 times, once for each length 1, 2 and 3.
	//for(int i = 1; i < x; i++) nPr (c,i); // where x is the requested password length and c is charset.
	
	
	public static void nPr(char [] c, ArrayList<Integer> z) throws IOException, InterruptedException{
		double perm = Math.pow(c.length, z.size());		// the number of permutations is defined by n^r where n is the
											//number of choices and r the number of selections
		double total = 0.0;
		
		Date d;
		
		
		run = Runtime.getRuntime();

		System.out.println(perm);
		String s="";
		
		//int a=0;
		
		//This is my first trial-and-error attempt for length 4. Kept for sentimental reasons.
//		for(int l = 0; l<c.length;l++){
//			for(int k = 0; k<c.length;k++){
//				for(int j = 0; j<c.length; j++){
//					for(int i = 0; i<c.length; i++){
//						
//						System.out.println(++a+" " + c[l] + c[k] + c[j] + c[i]);
//					}
//				
//				System.out.println();
//				}
//			}
//		}
		int currenti = z.size()-1;


		while(true){
			
			s="";
			total+=1;
			
			//System.out.println(++a+" " + z.toString() );
			for(int a=0; a<z.size();a++){

				s+=c[z.get(a)];
				//System.out.printf("%s",c[z.get(a)]);	//THIS IS WHERE PASSWORDS GET PRINTED OUT
			}
			if(total%100==0){ //show percentage and current password every 100th password			COMMENT OUT THE IF STATEMENT TO PRINT OUT EVERY SINGLE PASSWORD
				System.out.printf("%.2f%% %s \n",(total/perm)*100.0,s);		
			}
			
		/********************************************************************************************/
			if(platform.contains("linux") || platform.contains("mac")){
				pr = run.exec(command+s + " " + filename);		//THIS IS WHERE PASSWORDS ARE TRIED OUT			//COMMEND OUT THIS BLOCK TO GENERATE PASSWORDS
			}else if(platform.contains("windows")){
				pr = run.exec(command+s + " -y");
			}
																									//WITHOUT TRYING THEM OUT.
			pr.waitFor();																			//TRYING OUT EVERY PASSWORD IS MUCH MUCH SLOWER THAN
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));	//SIMPLY GENERATING THEM.
																									//MAYBE WRITE THIS BIT IN A SEPARATE THREAD?
																									//
			while(( line = buf.readLine()) != null ) 												//
			{																						//
			  //System.out.println(line);															//
			  if(line.contains("Everything is Ok") || line.contains("No errors detected")){			//
				  System.out.println("SUCCESS!!!");													//
				  System.out.println(s);															//
				  System.exit(0);																	//
			  }																						//
			}																						//
		/********************************************************************************************/

			
			//if last char reached max, reset it to start, iteratively check if prev char can be incremented,
			//increment prev char, reset pointer
			
			
			if(z.get(currenti).intValue()==c.length-1){
				//System.out.println();

				while(z.get(currenti).intValue()==c.length-1){
					z.set(currenti,0);
					currenti--;
					
					
					if(currenti==-1)
						break;
	
				}
				if(currenti==-1)
					break;
				z.set(currenti,z.get(currenti)+1);
				currenti=z.size()-1;
			}else{
				z.set(currenti, z.get(currenti)+1);

			}

		}

		
	}
	
	
	//not using these at the moment, may use in the future for not repeated characters 
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
