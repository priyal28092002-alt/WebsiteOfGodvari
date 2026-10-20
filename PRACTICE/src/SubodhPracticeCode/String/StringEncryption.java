/*You are a spy who needs to send a secret message to your partner. You decide to use a simple encryption scheme that involves the following steps:

Split the message into three-word groups, starting from the beginning. If the last group has less than three words, pad it with empty spaces.
For each group, replace the vowels in the first word with “%”, consonants in the second word with “#” and the last word must be converted to uppercase. Then concatenate the three words together.
Join the encrypted groups with a single space and send the message.
For example, if the message is “Where are you? Meet me near the clock tower.”, the encrypted message would be:

"Wh%r% a#e YOU? M%%t #e NEAR th% ##o## TOWER."

Write a function that takes a message as a string and returns the encrypted message as a string. You can assume that the message only contains letters, spaces, and punctuation marks. You can also assume that the vowels are “a”, “e”, “i”, “o”, and “u”. */

package com.example.String;

public class StringEncryption {
	public static void main(String[] args) {
		String s = "Where are you? Meet me near the clock tower";
        //String output = " ";
		String [] a = s.split(" ");
		for (int i = 0; i < a.length; i++) {
			int x = i % 3;
			if(x == 0) {
				System.out.print(vowels(a[i]) + " ");
			}
			else if (x == 1) {
				System.out.print(consonants(a[i])+ " ");
			}
			else {
				System.out.print(a[i].toUpperCase()+ " ");
			}
		}
	}
	
	private static String consonants(String s) {
		char[] ch = s.toCharArray();
		for(int i = 0; i < ch.length;i++)
		if(!(ch[i]=='a' || ch[i]=='e' || ch[i]=='i' || ch[i]=='o' || ch[i]=='u' || ch[i]=='A' || ch[i]=='E' || ch[i]=='I' || ch[i]=='O' || ch[i]=='U'))
		{
			ch[i] = '#';
		}
		String result = new String(ch);
		return result;
	}

	private static String vowels(String s) {
		char[] ch = s.toCharArray();
		for(int i = 0; i < ch.length;i++)
		if(ch[i]=='a' || ch[i]=='e' || ch[i]=='i' || ch[i]=='o' || ch[i]=='u' || ch[i]=='A' || ch[i]=='E' || ch[i]=='I' || ch[i]=='O' || ch[i]=='U')
		{
			ch[i] = '%';
		}
		String result = new String(ch);
		return result;
	}
}