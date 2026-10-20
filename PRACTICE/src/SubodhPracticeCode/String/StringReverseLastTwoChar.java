/*Given as String, create a new String made up of its last two letters,
reversed and separated by a space
Input: "bat"
Output: "t a" */

package com.example.String;

public class StringReverseLastTwoChar {
	public static void main(String[] args) {
		String str="bat";
		
		int length=str.length();
		String reverse=str.charAt(length-1)+""+str.charAt(length-2);
		
		/*String reverse="";
		for(int i=str.length()-1; i>=str.length()-2; i--) {
			reverse=reverse + str.charAt(i);
			reverse+=" ";
		}*/
		
		System.out.println(reverse);
	}
}
