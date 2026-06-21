package Programs;

public class FindtheMissingNumfromNArray {

	public static void main(String[] args) {
		int N=5;
		
		int [] arr= {1,2,3,5};
		System.out.println(findMissingNumber(N,arr));
		
	}
	
	static int findMissingNumber(int N, int[] arr) {
		
	int sum=0;	
	for (int i = 1; i <= N; i++) {
		sum += i;
	}
	 int missSum=0;
	for (int i=0;i<arr.length;i++) {
		missSum+=arr[i];
	}

	int missing=0;
	missing=sum-missSum;
		return missing;
	}
}
