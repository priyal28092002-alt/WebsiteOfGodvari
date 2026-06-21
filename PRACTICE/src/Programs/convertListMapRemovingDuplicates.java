package Programs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class convertListMapRemovingDuplicates {

	
	public static void main(String[]args) {
		List<String> list = Arrays.asList(
			    "Java",
			    "Spring",
			    "Java",
			    "Hibernate",
			    "Spring",
			    "Microservices",
			    "Java"
			);
		System.out.println(ReturnMapfromList(list));

		
	}
	
	static HashMap<Integer,String> ReturnMapfromList(List<String> list){
		HashMap<Integer,String> map=new HashMap<>();
		Integer i=0;
		for(String str:list) {
		if(!map.containsValue(str))
			map.put(i++, str);
		}
		
		return map;
	}
}
