//Program to count the occurrence of a substring in a String
//Eg: 'hi' in the string "ahiaaahiaahiahiaahi"

package com.example.String;

public class CountSubStringOccuranceInString {
	public static void main(String[] args) {
		String s="ahiaaahiaahiahiaahi";
		System.out.println(countHi(s));
		
	}
	public static int countHi(String s) {
		if(s.contains("hi")) {
			return 1+countHi(s.substring(s.indexOf("hi")+2));
		}
		else
		{
			return 0;
		}
	}
}
