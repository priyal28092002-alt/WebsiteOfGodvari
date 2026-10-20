/*
Program to reverse the words of a sentence without changing the word position
Input: "i like to eat apple"
Output: "i ekil ot tae elppa" 
*/

package com.example.String;

public class ReverseWordsOfSentenceB {
	public static void main(String[] args) {
		String s ="i like to eat apple";
		String word="";
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i)!=' ') {
				word=word+s.charAt(i);
			}else {
				System.out.print(reverse(word)+" ");
				word="";
			}
		}
		System.out.println(reverse(word));
	}
	public static String reverse(String s) {
		String reverse="";
		for (int i = s.length()-1; i >= 0; i--) {
			reverse=reverse+s.charAt(i);
		}
		return reverse;
	}
}