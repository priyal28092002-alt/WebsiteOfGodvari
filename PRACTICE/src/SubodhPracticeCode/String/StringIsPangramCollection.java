//Program to check if the the String is Pangram or not using Collection
/*A sentence that contains every letter of the alphabet, if possible with each letter only being used once.

- The quick brown fox jumps over the lazy dog.
- Sphinx of black quartz, judge my vow.
- Farmer Jack realized that big yellow quilts were expensive.*/

package com.example.String;

import java.util.HashSet;
import java.util.Set;

public class StringIsPangramCollection {
	public static void main(String[] args) {
		String s = "Sphinx of black quartz, judge my vow.";
		
		Set<Character> set = new HashSet<>();
		
		for(int i = 0 ;i<s.length();i++)
		{
			if (Character.isAlphabetic(s.charAt(i))) {
				set.add(s.charAt(i));
			}
		}
		
		if (set.size() == 26) {
			System.out.println("Pangram");
		} else {
			System.out.println("Not a Pangram");
		}
	}
}
