package Programs;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class RemoveDuplicateCharactersfromString {

	
	public static void main(String[] args) {
		
		System.out.println(Orginal("aahdeuehahsheweiey"));
	}
	
	static String Orginal(String str) {
		
		HashSet<Character> ch= new LinkedHashSet<>();
		
		for(Character chh:str.toCharArray()) {
			
			ch.add(chh);
		}
		
		String mm=ch.toString();
		return mm;
	}
	
}
