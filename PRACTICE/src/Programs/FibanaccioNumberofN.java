package Programs;

import java.util.ArrayList;
import java.util.List;

public class FibanaccioNumberofN {
	
	public static void main(String[]args) {
		
		int N=7;
		System.out.println(findfibanocci(N));
		
	}
	
	static List<Integer> findfibanocci(int N) {
		
		List<Integer> arr=new ArrayList<>();
				
		if(N<=0) {
			return new ArrayList<Integer>();
		}
		
		
		if(N==1) {
			arr.add(0);
		}
		
		if(N==2) {
			arr.add(1);
		}
		arr.add(0);
		arr.add(1);
		int a=0;int b=1;
		for(int i=3;i<=N;i++) {
	      int c=a+b;
	      arr.add(c);
	      a=b;
	      b=c;
		}
		return arr;
	}

}
