/*
Program to reverse the words of a sentence without changing the word position
Input: "i like to eat apple"
Output: "i ekil ot tae elppa" 
*/

package com.example.String;

public class ReverseWordsOfSentenceC {
	public static void main(String[] args) {
		String s ="i like to eat apple";
		String[] a=s.split(" ");
		for (int i = 0; i < a.length; i++) {
			System.out.print(reverse(a[i])+" ");
		}
	}
	public static String reverse(String s) {
		String reverse="";
		for (int i = s.length()-1; i >= 0; i--) {
			reverse=reverse+s.charAt(i);
		}
		return reverse;
	}
}