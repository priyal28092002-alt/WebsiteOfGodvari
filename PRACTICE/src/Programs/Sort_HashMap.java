package Programs;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Sort_HashMap {
	
	public static void main (String [] args) {
		HashMap<String,Integer> oldHashMap= new HashMap<>();
		oldHashMap.put("L", 20);
		oldHashMap.put("M", 10);
		oldHashMap.put("K", 5);
		oldHashMap.put("S", 3);
		
	Map<String,Integer> map=SortedMap(oldHashMap);
	for(Map.Entry<String, Integer> m:map.entrySet()) {
		System.out.println(m.getKey()+":"+m.getValue());
	}
		
	}
	
	static Map<String, Integer> SortedMap(HashMap<String, Integer> map) {
		Map<String, Integer> SortedMap = new LinkedHashMap<>();

	    // Work on a copy to avoid modifying original during iteration
	    Map<String, Integer> tempMap = new HashMap<>(map);
		while (tempMap != null && !tempMap.isEmpty()) {
			int value = Integer.MAX_VALUE;
			String Lastkey = null;
			for (String key : tempMap.keySet()) {
				if (tempMap.get(key) < value) {
					value = tempMap.get(key);
					Lastkey = key;
				}
		
			}
			SortedMap.put(Lastkey, value);
			tempMap.remove(Lastkey);
			value = Integer.MAX_VALUE;
			Lastkey = null;

		}

		return SortedMap;
	}

}
