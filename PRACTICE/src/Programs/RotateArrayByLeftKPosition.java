package Programs;

import java.util.ArrayList;
import java.util.List;

public class RotateArrayByLeftKPosition {

	
	public static void main(String [] args) {
		
		int[] arr = { 1, 2, 3, 4, 5 };
		int k = 3;

		List<Integer> list = RotatedList(k, arr);

		System.out.println(list.toString());
		
	}
	
	static List<Integer> RotatedList(int k ,int[] arr){
		 List<Integer> arr1= new ArrayList<>();
		 
		
		for (int i=k;i<arr.length;i++) {
			arr1.add(arr[i]);
		}
		for(int i=0;i<k;i++) {
			arr1.add(arr[i]);
		}
		return arr1;
			
	}
}
