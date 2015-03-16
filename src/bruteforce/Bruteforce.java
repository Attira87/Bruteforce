package bruteforce;
import java.io.IOException;
import java.util.HashMap;

import net.lingala.zip4j.exception.ZipException;


public class Bruteforce {

	//static String command, filename, platform, line, start;
	static String usageString = "Usage: bruteforce [-options] [arguments]" +
	"\noptions:\n" +
	"-i\t\tinteractive mode\n-bf\t\tcrack a password with bruteforce\n" +
	"-gen\t\tgenerate passwords\n" +
	"-en\t\tcalculate entropy\n" +
	"-scp\t\tscrape source to a dictionary\n\n";
	
	public static void main(String ... args) throws IOException, InterruptedException, ZipException{
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
		arguments.put("hashtype", null);
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
							case "-hash":
								System.out.printf("Enter hash:\n");
								cin = in.read();
								arguments.put("hash", cin);
								
								System.out.printf("Enter hash type:\n");
								cin = in.read();
								arguments.put("hashtype", cin);
								
								System.out.printf("Enter pattern:\n");
								cin = in.read();
								arguments.put("pattern", cin);
								break;
							case "-cmd": 
								System.out.printf("Enter command line to execute, use $ as a placeholder for password:\n");
								cin = in.read();
								arguments.put("cmd", cin);
								
								System.out.printf("Enter pattern:\n");
								cin = in.read();
								arguments.put("pattern", cin);
								
								
								System.out.printf("\n(Optional) Enter dictionary type or leave blank:\n-word\t\talready generated wordlist\n-raw\t\traw source file\n");
								cin = in.read();
								arguments.put("dicttype",cin);
								System.out.printf("\n(Optional) Enter dictionary path or leave blank:\n");
								cin = in.read();
								arguments.put("dictpath",cin);
								break;
							
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
						case "-en":
							System.out.printf("Enter entropy generation method:\n\n-pass\tuser entered password\n-pat\tpattern\n\n");
							cin = in.read();
							arguments.put("mode2", cin);
							
							switch(cin){
							case "-pass":
								System.out.printf("\nEnter password:\n");
								cin = in.read();
								arguments.put("password", cin);
								break;
							case "-pat":
								System.out.printf("\nEnter pattern:\n");
								cin = in.read();
								arguments.put("pattern", cin);
								break;
							
							}
							break;
						case "-scp":
							System.out.printf("\nEnter dictionary type:\n-word\t\talready generated wordlist\n-raw\t\traw source file\n");
							cin = in.read();
							arguments.put("dicttype", cin);

							System.out.printf("\nEnter dictionary path:\n");
							cin = in.read();
							arguments.put("dictpath", cin);
							break;
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
				arguments.put("hashtype", args[3]);
				arguments.put("pattern", args[4]);
				if(args.length == 6){
					System.out.println("Invalid parameters.");
					System.exit(1);
				}
				if(args.length == 7){
					arguments.put("dicttype",args[5]);
					arguments.put("dictpath",args[6]);
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

			break;

		case "-en":
			arguments.put("mode1", args[0]);
			
			switch(args[1]){
			case "-pass":
				arguments.put("mode2", args[1]);
				arguments.put("password", args[2]);
				break;
			case "-pat":
				arguments.put("mode2", args[1]);
				arguments.put("pattern", args[2]);
				break;
			}
			break;
		case "-scp":
			arguments.put("mode1", args[0]);
			arguments.put("dictpath", args[1]);
			break;
		default:
			System.out.println(usageString);
			break;
		}
		
		//TODO: Check that all the arguments are collected, esp. dictionary; add starting password option, add thread number option
		
		switch(arguments.get("mode1")){
			case "-bf": 
				switch(arguments.get("mode2")){
				case "-zip": {
					ZipCracker cracker = new ZipCracker(arguments.get("filename"), arguments.get("pattern"), arguments.get("dicttype"), arguments.get("dictpath"));
					break;}
				case "-hash":{ 
					HashCracker cracker = new HashCracker(arguments.get("hash"),arguments.get("hashtype"), arguments.get("pattern"), arguments.get("dicttype"), arguments.get("dictpath"));
					break;}
				case "-cmd":{
					CmdCracker cracker = new CmdCracker(arguments.get("cmd"), arguments.get("pattern"), arguments.get("dicttype"), arguments.get("dictpath"));
					break;}
				}
				break;
			case "-gen": break;
			case "-en": break;
			case "-scp": break;
			
		}
		
		System.out.printf("%s",arguments.toString());

	}
	
	
	
}
