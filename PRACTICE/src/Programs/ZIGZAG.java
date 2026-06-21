package Programs;

import java.util.Scanner;

/*Test Case 1:
	 
	Input 1 : 927
	Input 2 : 613
	Output : 932176*/
public class ZIGZAG {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter first number");
		int a = sc.nextInt();
		System.out.println("Enter second number");
		int b = sc.nextInt();
		System.out.println("value is: ");
		System.out.println(findtheZigzag(String.valueOf(a),String.valueOf(b)));
	
	}
	
	
	
	public static String findtheZigzag(String a, String b) {
		int sizea = a.length();
		int sizeb = b.length();
		StringBuilder str = new StringBuilder();
		if (sizea != sizeb) {
			return "invalid";
		}
		char[] cha = a.toCharArray();
		char[] chb = b.toCharArray();
		int j = sizeb - 1;
		for (int i = 0; i < sizea; i++, j--) {
			// for (int j = sizeb; j > 0; j--) {
			str.append(cha[i]);
			str.append(chb[j]);
			// }
		}
		String real = str.toString();
		return real;
	}
}
