package Programs;

public class CheckNumberPalindromeOrNot {

	public static void main(String[] args) {

		boolean val=CheckPalindrome(1221);
		if(val) {
			System.out.println("Yes Palindrome");
		}else {
			System.out.println("No Palindrome");
		}
	
	}

	static boolean CheckPalindrome(int k) {

		if (k == 0) {
			return false;
		}
		String number = String.valueOf(k);
		StringBuilder reversenum = new StringBuilder();
		char[] chh = number.toCharArray();
		for (int i = number.toCharArray().length-1; i >= 0.; i--) {
			reversenum.append(chh[i]);
		}
		String reverse = reversenum.toString();
		if (reverse != null && !reverse.isEmpty()) {
			int reversval = Integer.valueOf(reverse);
			if (k == reversval) {
				return true;
			}
		}
		return false;
	}

}
