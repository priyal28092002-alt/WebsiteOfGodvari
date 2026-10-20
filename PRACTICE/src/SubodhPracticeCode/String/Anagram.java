//Program to check if the Strings are Anagrams or not
//To be an Anagram the length of the String must be same 
//and characters in the second String should be same as that of the first String

package com.example.String;

import java.util.Arrays;

public class Anagram {
	public static void main(String[] args) {
		String str1="cat";
		String str2="act";
		
		if(str1.length()==str2.length()) {
			char[] c1=str1.toCharArray();
			char[] c2=str2.toCharArray();
			
			Arrays.sort(c1);
			Arrays.sort(c2);
			
			if(Arrays.equals(c1, c2)) {
				System.out.println("Its an Anagram");
			}else {
				System.out.println("Its not an Anagram");
			}
		}else {
			System.out.println("Since, the ength of the Strings are not same, its not an Anagram.");
		}
	}
}
