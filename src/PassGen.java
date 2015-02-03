import java.io.IOException;
import java.util.ArrayList;


/*
 * Doesn't work yet
 */
public class PassGen implements Password{
	char[] charset;
	int min, max;
	
	
	public PassGen(char[] c, int min, int max){
		this.charset = c;
		this.min = min;
		this.max = max;
	}
	
	
	
	@Override
	public String nextPassword() throws IOException, InterruptedException {

		for(int i = min; i<=max; i++){
			//An ArrayList of ints is used to store the int representation of the current permutation
			//and index into the char array to retrieve the current state.
			ArrayList<Integer> index = new ArrayList<Integer>(charset.length);		
			for(int j = 0; j<i;j++) index.add(0);
			
			//this function is called for each password length
			nPr(charset, index);
		}
		
		return null;
	}
	
	public static void nPr(char [] c, int min, int max) throws IOException, InterruptedException{
		//TO DO: IMPLEMENT STARTING PASSWORD OPTION AND PLUG INTO THE INDEX ARRAYLIST
		
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


		String s="";
		

		
		int currenti = z.size()-1;


		while(true){
			
			s="";

			
			for(int a=0; a<z.size();a++){

				s+=c[z.get(a)];
				//System.out.printf("%s",c[z.get(a)]);	//THIS IS WHERE PASSWORDS GET PRINTED OUT
			}

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

}
