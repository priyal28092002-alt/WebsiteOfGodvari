package Programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DuplicatesInArray {
	
	public static void main(String[] args) {
		
		int [] arr= {1,2,2,0,1,9};
	List<Integer> a=duplicates(arr);
	System.out.println(a.toString());
	List<Integer> b=duplicatesHasMap(arr);
	System.out.println(b.toString());
		
	}
	static List<Integer> duplicates(int [] arr) {
		List<Integer> array = new ArrayList<Integer>();
		HashSet<Integer> set= new  HashSet<>();
		
		//Using Hash Set
		for(Integer a:arr) {
			if(!set.add(a)) {
				array.add(a);
			}
		}
		
		return array;
	}

	//Using HashMap
	static List<Integer> duplicatesHasMap(int [] arr) {
		List<Integer> array = new ArrayList<Integer>();
		HashMap<Integer,Integer> map= new  HashMap<>();
		

		for(Integer a:arr) {
			map.put(a, map.getOrDefault(a, 0)+1);
		}
		
		for(int m:map.keySet()) {
			if(map.get(m)>1) {
				array.add(m);	
			}
		}
		
		return array;
	}
}
