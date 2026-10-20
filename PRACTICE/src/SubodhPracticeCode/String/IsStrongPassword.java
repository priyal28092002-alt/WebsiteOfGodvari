//Program to check if the password(String) is strong or not
/*1)At least 1 Upper case character
 *  At least 1 lower case character
 *  At least 1 numeric digit
 *  At least 1 special character
 */

package com.example.String;

public class IsStrongPassword {
	public static void main(String[] args) {
		String str="Programming@24";
		
		if(str.length()>=8) {
			boolean isUpper=false, isLower=false, isDigit=false, isSpecial=false;
			
			for(int i=0; i<str.length(); i++) {
				
				char ch=str.charAt(i);
				if(Character.isUpperCase(ch)) {
					isUpper=true;
				}else if(Character.isLowerCase(ch)) {
					isLower=true;
				}else if(Character.isDigit(ch)) {
					isDigit=true;
				}else {
					isSpecial=true;
				}
			}
			
			if(isUpper && isLower && isDigit && isSpecial) {
				System.out.println("Strong Password");
			}else {
				System.out.println("Weak Password");
			}
		}else {
			System.out.println("Weak Password");
		}
	}
}
