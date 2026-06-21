package Programs;

import java.util.HashMap;
import java.util.Map;

public class FrequencyofCharacters {
	
	public static void main(String [] args) {
		
		String str="aahgheghakiei";
		
		Map<Character,Integer> map =frequencyofCharacters(str);
		
		for(Map.Entry<Character, Integer> m:map.entrySet()) {
			System.out.println(m.getKey()+":" + m.getValue());
			
		}
	}

	
	static Map<Character,Integer> frequencyofCharacters(String str){
		HashMap <Character,Integer> map= new HashMap<>();
		
		for(Character ch:str.toCharArray()) {
			map.put(ch,map.getOrDefault(ch, 0)+1);
		}
		return map;
		
	}
}
