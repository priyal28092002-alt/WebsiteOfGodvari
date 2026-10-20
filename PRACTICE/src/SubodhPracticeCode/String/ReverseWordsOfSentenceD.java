/*
Program to reverse the words of a sentence without changing the word position
Input: "i like to eat apple"
Output: "i ekil ot tae elppa" 
*/

package com.example.String;

public class ReverseWordsOfSentenceD {
	public static void main(String[] args) {
		String s ="i like to eat apple";
		String rev = reverse(s);
		String[]a=rev.split(" ");
		for (int i = a.length-1; i >=0; i--) {
			System.out.print(a[i]+" ");
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