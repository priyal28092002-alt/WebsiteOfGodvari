//Program to remove all the vowels from the String
//Program to remove all the consonants from the String
//Program to remove all the spaces from the String
//Program to remove all the digits from the String
//Program to remove the given character from the String

package com.example.String;

import java.util.Scanner;

//Program to remove the given character from the String
public class RemoveCharactersFromString {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the String: ");
		String str=sc.nextLine();
		
		System.out.print("Enter the character to remove: ");
		char ch=sc.nextLine().charAt(0);
		
		String nstr="";
		
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i)!=ch) {
				nstr+=str.charAt(i);
			}
		}
		
		System.out.println("The String after removing the character "+ch+": "+nstr);
		sc.close();
	}
}
