	package Programs;
	
	import java.util.HashMap;
import java.util.HashSet;
	
	public class PairsofSuminArray {
	
		public static void main(String[]args) {
			int[] arr= {1,2,3,6,1,4,7};
			int k=7;
			
			System.out.println(findpair(arr,k));
			
		}
		
		static HashMap<Integer, Integer> findpair(int[] arr, int k) {
		    HashMap<Integer, Integer> map = new HashMap<>();
		    HashSet<Integer> seen = new HashSet<>();

		    for (int num : arr) {
		        int complement = k - num;

		        if (seen.contains(complement)) {
		        	int min=Math.min(complement, num);
		        	int max=Math.max(complement, num);
		            map.put(min, max);
		        }
		        seen.add(num);
		    }
		    return map;
		}

	}
