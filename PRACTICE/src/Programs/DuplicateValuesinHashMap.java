package Programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;import javafx.collections.MapChangeListener;

public class DuplicateValuesinHashMap {

	public static void main(String[] args) {
	    HashMap<String, Integer> map = new HashMap<>();
	    map.put("A", 10);
	    map.put("B", 20);
	    map.put("C", 10);
	    map.put("D", 30);
	    map.put("E", 20);

	    System.out.println(duplicateValues(map));
	}

	
	static List<Integer> duplicateValues(HashMap<String,Integer> map){
		List<Integer> duplicatelist = new ArrayList<>();
		Set<Integer> set=new HashSet<>();
		for(String str:map.keySet()) {
			
			if(!set.add(map.get(str))) {
				duplicatelist.add(map.get(str));
			}
		}
		
		return duplicatelist;
	}
}
