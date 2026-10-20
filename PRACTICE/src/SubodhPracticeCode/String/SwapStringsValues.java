//Program to swap two String values using 3rd variable

package com.example.String;

public class SwapStringsValues {
	public static void main(String[] args) {
		String str1 = "8th August";
		String str2 = "2024";
		
		System.out.printf("Before swapping:%n%s%n%s%n%n", str1, str2);
		
		String temp = str1;
		str1 = str2;
		str2 = temp;
		
		System.out.printf("After swapping:%n%s%n%s", str1, str2);
	}
}
