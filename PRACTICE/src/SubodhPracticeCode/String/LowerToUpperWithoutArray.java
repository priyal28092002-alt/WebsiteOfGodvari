//Program to convert all the lower case characters into upper case characters.

/*Diff b/w ascii of lower and upper = 32

convert uppercase into lowercase
===> ascii of lowercase = ascii of uppercase + 32

convert lowercase into uppercase
===> ascii of uppercase = ascii of lowercase - 32 */

package com.example.String;

public class LowerToUpperWithoutArray {
	public static void main(String[] args) {
		String str = "jaVA@ proGramming123";

		String upper = "";

		for (int i = 0; i < str.length(); i++) {
			if (Character.isLowerCase(str.charAt(i))) {
				upper = upper + (char) (str.charAt(i) - 32);
			} else {
				upper = upper + str.charAt(i);
			}
		}

		System.out.println(upper);
	}
}
