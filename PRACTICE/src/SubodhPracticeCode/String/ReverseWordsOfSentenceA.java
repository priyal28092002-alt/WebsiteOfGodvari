/*
Program to reverse the words of a sentence without changing the word position
Input: "i like to eat apple"
Output: "i ekil ot tae elppa" 
*/

package com.example.String;

public class ReverseWordsOfSentenceA {
	public static void main(String[] args) {
		String sentence = "i like to eat apple";
		String[] words = sentence.split(" ");

		for (int i = 0; i < words.length; i++) {
			words[i] = new StringBuilder(words[i]).reverse().toString();
		}

		// Print the reversed words to check
		for (String word : words) {
			System.out.print(word + " ");
		}
	}
}