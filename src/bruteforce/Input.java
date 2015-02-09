package bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input{
	BufferedReader br;
	
	public Input() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public String read(){
	
		try{
			return br.readLine();
		}catch(IOException e){
			System.out.printf("IO error: %s", e);
			System.exit(1);
		}
		return null;
	}

}
