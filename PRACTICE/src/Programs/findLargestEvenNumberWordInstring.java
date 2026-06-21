package Programs;

public class findLargestEvenNumberWordInstring {

	public static void main (String[] args) {
		
		System.out.println(LargestevenNumberString("Thank You for giving such great opportunity."));
	}
	
	public static String LargestevenNumberString(String s) {
		
		if (s == null || s.isEmpty()) {
			return "";
		}
		
	  String largestring="";
	    int highestcount=0;
		String [] str=s.split(" ");
		
		for(String sm:str) {
			char[]ch=sm.toCharArray();
			if(ch.length%2==0 && highestcount<ch.length) {
				highestcount=ch.length;
				largestring=sm;
			}
		}
		
		return largestring;
	}
}
