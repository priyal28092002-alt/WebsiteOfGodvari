package Programs;

import java.util.LinkedHashMap;
import java.util.Map;

public class FindNonrepeatingCharInString {
	
	public static void main(String [] args) {
		
		String str="ababddfflllkmkm";
		Character ch=findnonRepeatingChar(str);
		System.out.println(ch);
	}

	
	static Character findnonRepeatingChar(String str) {
		Map<Character,Integer> map= new LinkedHashMap<>();
		
		for(Character ch:str.toCharArray()) {
			map.put(ch, map.getOrDefault(ch,0)+1);
		}
		
		for(Character key:map.keySet()) {
			if(map.get(key)==1) {
				return key;
			}
		}
		
		return null;
	}
}
