package bruteforce;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


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
			+ "\noptions:\n-gen\t\tgenerate passwords\n-bf\t\tbrute force attack a file\n-entropy\tcalculate entropy\n";
	public static void main(String[] args) throws IOException, InterruptedException{
		/*
		 * The arguments hashmap stores the args either from the command line args or interactive mode args.
		 * It streamlines the execution of the program for either mode.
		 */
		HashMap<String, String> arguments = new HashMap<String, String>();
		arguments.put("mode1", null);
		arguments.put("mode2", null);
		arguments.put("filename", null);
		arguments.put("pattern", null);
		arguments.put("hash", null);
		arguments.put("cmd", null);
		arguments.put("entropy", null);
		arguments.put("dicttype", null);
		arguments.put("dictpath", null);
		arguments.put("password", null);
		

		if(args.length == 0){System.out.println(usageString);System.exit(0);}
		if(args.length < 1){System.out.println("Invalid parameters.");System.exit(0);}
		
		switch(args[0]){
		
		case "-i":		//interactive mode
						System.out.printf("Enter: \n" +
								"-bf\t\tto crack a password with bruteforce\n" +
								"-gen\t\tto generate passwords\n" +
								"-en\t\tto calculate entropy\n" +
								"-scp\t\tto scrape source to a dictionary\n\n");
						Input in = new Input();
						String cin = in.read().trim();
						arguments.put("mode1", cin);

						switch(cin){
						case "-bf":
							System.out.printf("\nEnter target to crack:\n-zip\t\tencrypted zip file\n-hash\t\thashed password\n-cmd\t\tcommand line service/program\n\n");
							cin = in.read();
							arguments.put("mode2", cin);
							switch(cin.trim()){
							case "-zip":
								System.out.printf("Enter filename:\n");
								cin = in.read();
								arguments.put("filename", cin);
								
								System.out.printf("Enter pattern to generate passwords from:\n" +
										"a\t\tsmall letters\n" +
										"A\t\tbig letters\n" +
										"n\t\tdigits\n" +
										"s\t\tspecial characters\n" +
										"d\t\tdictionary word\n" +
										"+\t\tconcatenate patterns\n" +
										"[a-c]\t\tcharacters 'a', 'b', 'c' in this order\n" +
										"[a--c]\t\tcharacters 'a', '-', 'c' in this order\n" +
										"{a-c}\trange from a character to c character\n" +
										"{a--c}\t\tcharacter set 'a', '-', 'c' to generate permutations from\n" +
										"{[abc][ABC][qwerty]}\t\tpermuted character sets in original order\n" + //array of char[] 
										"n\t\tlength n of preceding pattern\n" +
										"n-m\t\tlength between n and m of preceding pattern\n" +
										"n,m,o\t\tlengths n, m, o of preceding pattern\n" +
										"example 1: a3,5,7n2\n" +
										"3,5 or 7 small letters followed by 2 digits\n" +
										"example 2: da+A+n+s2\n" +
										"a dictionary word followed by a 2 character pattern generated from small and big letters, numbers and special characters\n" +
										"example 3: d3-6[LOL]n2\n" +
										"a dictionary word of length 3 to 6 characters, characters 'LOL' and 2 digits\n" +
										"example 4: {a-cgk-n[12][45][87]}8[98]\n" + //characters in [] treated like single characters
										"an 8 character/sequence pattern generated from characters/sequences 'a','b','c','g','k','l','m','n','12','45','87' followed by characters 98\n");
								cin = in.read();
								arguments.put("pattern", cin);
								
								System.out.printf("\n(Optional) Enter dictionary type or leave blank:\n-word\t\talready generated wordlist\n-raw\t\traw source file\n");
								cin = in.read();
								arguments.put("dicttype",cin);
								System.out.printf("\n(Optional) Enter dictionary path or leave blank:\n");
								cin = in.read();
								arguments.put("dictpath",cin);

								break;
							case "-hash": break;
							case "-cmd": break;
							
							}
							break;
						case "-gen": 
							System.out.printf("\nEnter password generation method:\n-en\t\tentropy\n-pat\t\tpattern\n");
							cin = in.read();
							arguments.put("mode2", cin);
							switch(cin){
							case "-en": 
								System.out.printf("\nEnter entropy:\n");
								cin = in.read();
								arguments.put("entropy", cin);
								break;
							case "-pat":
								System.out.printf("\nEnter pattern:\n");
								cin = in.read();
								arguments.put("pattern", cin);
								System.out.printf("\n(Optional) Enter dictionary type or leave blank:\n-word\t\talready generated wordlist\n-raw\t\traw source file\n");
								cin = in.read();
								arguments.put("dicttype", cin);

								System.out.printf("\n(Optional) Enter dictionary path or leave blank:\n");
								cin = in.read();
								arguments.put("dictpath", cin);
								break;
							}
							break;
						case "-en": break;
						default: System.out.println("Invalid option.");break;
						}
						
						break;
		
		case "-gen":
			arguments.put("mode1", args[0]);
			arguments.put("mode2", args[1]); //entropy or pattern
			switch(args[1]){
			case "-en":
				arguments.put("entropy",args[2]);
				break;
			case "-pat":
				arguments.put("pattern",args[2]);
				if(args.length == 4){
					System.out.println("Invalid parameters.");
					System.exit(1);
				}
				if(args.length == 5) {
					arguments.put("dicttype",args[3]);
					arguments.put("dictpath",args[4]);
				}
				break;
			}
			break;
		case "-bf":
			arguments.put("mode1", args[0]);
			arguments.put("mode2", args[1]);
			
			switch(args[1]){
			case "-zip":
				arguments.put("filename", args[2]);
				arguments.put("pattern", args[3]);
				if(args.length == 5){
					System.out.println("Invalid parameters.");
					System.exit(1);
				}
				if(args.length == 6){
					arguments.put("dicttype",args[4]);
					arguments.put("dictpath",args[5]);
				}
				break;
			case "-hash":
				arguments.put("hash", args[2]);
				arguments.put("pattern", args[3]);
				if(args.length == 5){
					System.out.println("Invalid parameters.");
					System.exit(1);
				}
				if(args.length == 6){
					arguments.put("dicttype",args[4]);
					arguments.put("dictpath",args[5]);
				}
				break;
			case "-cmd":
				arguments.put("cmd",args[2]);
				arguments.put("pattern",args[3]);
				
				if(args.length == 5){
					System.out.println("Invalid parameters.");
					System.exit(1);
				}
				if(args.length == 6){
					arguments.put("dicttype",args[4]);
					arguments.put("dictpath",args[5]);
				}
				break;
			}
			
//			Generator myPassGen = new PermuteRepeat(letters,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
//			ArchiveTester archiveTester = new ArchiveTester(filename);
//			boolean found = false;
//			String password = "";
//			while((password = myPassGen.getNextPassword()) != null){
//				found = archiveTester.testPass(password);
//				if(found){
//					System.out.printf("Password found: %s", password);
//					break;
//				}
//				
//			}
//			if(!found) System.out.println("Password not found");

			break;

		case "-en":
			arguments.put("mode1", args[0]);
			arguments.put("password", args[1]);
			break;
		case "-scp":
			arguments.put("mode1", args[0]);
			arguments.put("dictpath", args[1]);
			break;
		default:
			System.out.println(usageString);
			break;
		}
		
		System.out.printf("%s",arguments.toString());

		
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
