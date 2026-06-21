package Programs;

import java.util.ArrayList;
import java.util.List;

public class FindLargestandsmallestNumberinarray {
	
	public static void main(String[]args) {
		int[] arr= {2,3,38,89,9,1,87};
		System.out.println(findthaeLargestAndSmallest(arr));
		
	}

	static List<Integer> findthaeLargestAndSmallest(int[] arr){
		Integer smallest=Integer.MAX_VALUE;
		Integer largest=Integer.MIN_VALUE;
		
		for(int i:arr) {
			if(i>largest) {
				largest=i;
			}
			
			if(smallest>i) {
				smallest=i;
			}
		}
		
		List<Integer>list=new ArrayList<>();
		list.add(largest);
		list.add(smallest);
		return list;
	}
}
