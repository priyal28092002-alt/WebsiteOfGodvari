/*Program to print the String along with the count of alphabetic characters to the left concatenated 
with alphabetic characters and the sum of the digits present in the String to the right
Input: "MU1M2B3A4I"
Output: 6MUMBAI10 */

package com.example.String;

public class DigitInStringSum {
	public static void main(String[] args) {
		String str="MU1M2B3A4I";
		String nstr="";
		
        //int numChar=0;
		int sumDigit=0;
		
		for(int i=0; i<str.length(); i++) {
			char ch=str.charAt(i);
			if(Character.isAlphabetic(ch)) {
				nstr+=ch;
				//numChar++;
			}else if(Character.isDigit(ch)) {
				sumDigit+=(Character.getNumericValue(ch));
				//sumDigit+=(str.charAt(i)-'0');
			}
		}
		
		//System.out.println(numChar+nstr+sumDigit);
		System.out.println(nstr.length()+nstr+sumDigit);
	}
}
