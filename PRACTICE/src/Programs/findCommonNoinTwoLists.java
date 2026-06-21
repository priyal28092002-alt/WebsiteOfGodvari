package Programs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class findCommonNoinTwoLists {

	
	public static void main(String [] args) {
		
		int[] str1= {1,2,3,4,6};
		
		int[] str2= {1,8,3,9,19};
		
		List<Integer> list =	getListofCommonNumbers(str1,str2);
		System.out.println(list.toString());
	}
	
	static List<Integer> getListofCommonNumbers(int[] a, int[] b){
		List<Integer> list=new  ArrayList<>();
		Set<Integer> set=new HashSet<>();
		
		for(int s:a) {
			set.add(s);
		}
		
		for(int l:b) {
			if(!set.add(l)) {
				list.add(l);
			}
		}
		
		
		return list;
	}
	
	
}
