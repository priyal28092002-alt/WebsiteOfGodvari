//Program to check if the the String is Pangram or not
/*A sentence that contains every letter of the alphabet, if possible with each letter only being used once.

- The quick brown fox jumps over the lazy dog.
- Sphinx of black quartz, judge my vow.
- Farmer Jack realized that big yellow quilts were expensive.*/

package com.example.String;

public class StringIsPangram {
	public static void main(String[] args) {
		String str="Java is a programming language";
		str.toLowerCase();
		
		boolean[] b = new boolean[26];
		
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i)>='a' && str.charAt(i)<='z') {
				int x=str.charAt(i)-'a'; //character - 'a' gives the index
				b[x]=true;
			}
		}
		
		int count=0;
		for(int i=0; i<b.length; i++) {
			if(b[i]==true) {
				count++;
			}
		}
		
		if(count==26) {
			System.out.println("Is a pangram");
		}else {
			System.out.println("Not a pangram");
		}
	}
}
