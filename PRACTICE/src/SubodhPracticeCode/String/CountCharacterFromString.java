//Program to count all the vowels present in the String
//Program to count all the consonants present in the String
//Program to count all the numeric values present in the String

package com.example.String;

import java.util.Scanner;

//Program to count all the vowels present in the String
public class CountCharacterFromString {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the String: ");
		String str=sc.nextLine();
		
		int count=0;
		
		for(int i=0; i<str.length(); i++) {
			char x=str.charAt(i);
			if(x=='a' || x=='e' || x=='i' || x=='o' || x=='u') {
				count++;
			}
		}
		
		System.out.println(count);
		sc.close();
	}
}
