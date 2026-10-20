//Program to check if the String is a Palindrome or not
//level -> level | nitin -> nitin

package com.example.String;

import java.util.Scanner;

public class PalindromeString {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the String: ");
		String str=sc.next();
		
		String reverse="";
		
		for(int i=str.length()-1; i>=0; i--) {
			reverse=reverse + str.charAt(i);
		}
		
		//equals()-> This method doesn't ignores the cases
		//equalsIgnoreCase()-> This method ignores the cases
		if (str.equalsIgnoreCase(reverse)) {
			System.out.println("The String is a Palindrome.");
		} else {
			System.out.println("The String is a not a Palindrome.");
		}
		
		sc.close();
	}
}
